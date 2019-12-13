package org.fisco.bcos.asset.client;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.fisco.bcos.asset.contract.Project;
import org.fisco.bcos.asset.contract.Project.FromReceiptEventEventResponse;
import org.fisco.bcos.asset.contract.Project.ToReceiptEventEventResponse;
import org.fisco.bcos.asset.contract.Project.TotalMoneyEventEventResponse;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.Keys;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple2;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class ProjectClient {

	static Logger logger = LoggerFactory.getLogger(ProjectClient.class);

	private Web3j web3j;

	private Credentials credentials;

	public Web3j getWeb3j() {
		return web3j;
	}

	public void setWeb3j(Web3j web3j) {
		this.web3j = web3j;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public void recordProjectAddr(String address) throws FileNotFoundException, IOException {
		Properties prop = new Properties();
		prop.setProperty("address", address);
		final Resource contractResource = new ClassPathResource("contract.properties");
		FileOutputStream fileOutputStream = new FileOutputStream(contractResource.getFile());
		prop.store(fileOutputStream, "contract address");
	}

	public String loadProjectAddr() throws Exception {
		// load Project contact address from contract.properties
		Properties prop = new Properties();
		final Resource contractResource = new ClassPathResource("contract.properties");
		prop.load(contractResource.getInputStream());

		String contractAddress = prop.getProperty("address");
		if (contractAddress == null || contractAddress.trim().equals("")) {
			throw new Exception(" load Project contract address failed, please deploy it first. ");
		}
		logger.info(" load Project address from contract.properties, address is {}", contractAddress);
		return contractAddress;
	}

	public void initialize() throws Exception {

		// init the Service
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Service service = context.getBean(Service.class);
		service.run();

		ChannelEthereumService channelEthereumService = new ChannelEthereumService();
		channelEthereumService.setChannelService(service);
		Web3j web3j = Web3j.build(channelEthereumService, 1);

		// init Credentials
		Credentials credentials = Credentials.create(Keys.createEcKeyPair());

		setCredentials(credentials);
		setWeb3j(web3j);

		logger.debug(" web3j is " + web3j + " ,credentials is " + credentials);
	}

	private static BigInteger gasPrice = new BigInteger("30000000");
	private static BigInteger gasLimit = new BigInteger("30000000");

	public void deployProjectAndRecordAddr() {

		try {
			Project project = Project.deploy(web3j, credentials, new StaticGasProvider(gasPrice, gasLimit)).send();
			System.out.println(" deploy Project success, contract address is " + project.getContractAddress());

			recordProjectAddr(project.getContractAddress());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println(" deploy Project contract failed, error message is  " + e.getMessage());
		}
	}

        public void myCreateReceipt(String f, String t, BigInteger m, String s) {
                try {
			String contractAddress = loadProjectAddr();

			Project project = Project.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
			TransactionReceipt receipt = project.createReceipt(f,t,m,s).send();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.error(" create receipt exception, error message is {}", e.getMessage());

			System.out.printf(" create receipt failed, error message is %s\n", e.getMessage());
		}
        }

        public void myEndReceipt(String f, String t) {
                try {
			String contractAddress = loadProjectAddr();

			Project project = Project.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
			TransactionReceipt receipt = project.endReceipt(f,t).send();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.error(" end receipt exception, error message is {}", e.getMessage());

			System.out.printf(" end receipt failed, error message is %s\n", e.getMessage());
		}
        }

        public void myTotalMoney(String f) {
                try {
			String contractAddress = loadProjectAddr();

			Project project = Project.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
			TransactionReceipt receipt = project.totalMoney(f).send();
			List<TotalMoneyEventEventResponse> response = project.getTotalMoneyEventEvents(receipt);
			if (!response.isEmpty()) {
                                System.out.printf(" The company %s 's total money is %s \n", f,
							response.get(0).ret.toString());
			} else {
				System.out.println(" event log not found, maybe transaction not exec. ");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.error(" get total money exception, error message is {}", e.getMessage());

			System.out.printf(" get total money failed, error message is %s\n", e.getMessage());
		}
        }

        public void myFromReceipt(String f) {
                try {
			String contractAddress = loadProjectAddr();

			Project project = Project.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
			TransactionReceipt receipt = project.fromReceipt(f).send();
			List<FromReceiptEventEventResponse> response = project.getFromReceiptEventEvents(receipt);
			if (!response.isEmpty()) {
                                System.out.printf(" The company %s 's from receipt is %s \n", f,
							response.get(0).ret.toString());
			} else {
				System.out.println(" event log not found, maybe transaction not exec. ");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.error(" get from receipt exception, error message is {}", e.getMessage());

			System.out.printf(" get from receipt failed, error message is %s\n", e.getMessage());
		}
        }

        public void myToReceipt(String f) {
                try {
			String contractAddress = loadProjectAddr();

			Project project = Project.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
			TransactionReceipt receipt = project.toReceipt(f).send();
			List<ToReceiptEventEventResponse> response = project.getToReceiptEventEvents(receipt);
			if (!response.isEmpty()) {
                                System.out.printf(" The company %s 's to receipt is %s \n", f,
							response.get(0).ret.toString());
			} else {
				System.out.println(" event log not found, maybe transaction not exec. ");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.error(" get to receipt exception, error message is {}", e.getMessage());

			System.out.printf(" get to receipt failed, error message is %s\n", e.getMessage());
		}
        }

        public void myBorrowFromBank(String creator, BigInteger m) {
                try {
			String contractAddress = loadProjectAddr();

			Project project = Project.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
			TransactionReceipt receipt = project.borrowFromBank(creator,m).send();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.error(" borrow from bank exception, error message is {}", e.getMessage());

			System.out.printf("borrow from bank failed, error message is %s\n", e.getMessage());
		}
        }

        public void myTransferReceipt(String creator, String t, BigInteger m) {
                try {
			String contractAddress = loadProjectAddr();

			Project project = Project.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
			TransactionReceipt receipt = project.transferReceipt(creator,t,m).send();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.error(" transfer receipt exception, error message is {}", e.getMessage());

			System.out.printf("transfer receipt failed, error message is %s\n", e.getMessage());
		}
        }

        public void myCreateCompany(String n, String a, String c, BigInteger t) {
                try {
			String contractAddress = loadProjectAddr();

			Project project = Project.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
			TransactionReceipt receipt = project.createCompany(n,a,c,t).send();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.error(" create company exception, error message is {}", e.getMessage());

			System.out.printf(" create company failed, error message is %s\n", e.getMessage());
		}
        }

	public static void Usage() {
		System.out.println("Your input is wrong!");
		System.exit(0);
	}

	public static void main(String[] args) throws Exception {

		if (args.length < 1) {
			Usage();
		}

		ProjectClient client = new ProjectClient();
		client.initialize();

		switch (args[0]) {
		case "deploy":
			client.deployProjectAndRecordAddr();
			break;
		case "createCompany":
			if (args.length < 5) {
				Usage();
			}
			client.myCreateCompany(args[1],args[2],args[3],new BigInteger(args[4]));
			break;
		case "createReceipt":
			if (args.length < 5) {
				Usage();
			}
			client.myCreateReceipt(args[1],args[2],new BigInteger(args[3]),args[4]);
			break;
		case "transferReceipt":
			if (args.length < 4) {
				Usage();
			}
			client.myTransferReceipt(args[1], args[2], new BigInteger(args[3]));
			break;
		case "borrowFromBank":
			if (args.length < 3) {
				Usage();
			}
			client.myBorrowFromBank(args[1], new BigInteger(args[2]));
			break;
		case "endReceipt":
			if (args.length < 3) {
				Usage();
			}
			client.myEndReceipt(args[1], args[2]);
			break;
		case "totalMoney":
			if (args.length < 2) {
				Usage();
			}
			client.myTotalMoney(args[1]);
			break;
		case "fromReceipt":
			if (args.length < 2) {
				Usage();
			}
			client.myFromReceipt(args[1]);
			break;
		case "toReceipt":
			if (args.length < 2) {
				Usage();
			}
			client.myToReceipt(args[1]);
			break;
		default: {
			Usage();
		}
		}

		System.exit(0);
	}
}
