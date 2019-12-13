package org.fisco.bcos.asset.contract;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.channel.event.filter.EventLogPushWithDecodeCallback;
import org.fisco.bcos.web3j.abi.EventEncoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Event;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.tx.TransactionManager;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.fisco.bcos.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version none.
 */
@SuppressWarnings("unchecked")
public class Project extends Contract {
    public static String BINARY = "608060405234801561001057600080fd5b50611b05806100206000396000f30060806040526004361061008e576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806324694ed1146100935780632d6099531461019257806333912c9d14610241578063417c448e146102be57806346c4883b1461033b5780634c6daeee146103b8578063874060511461042b578063e3c6b61f146104e4575b600080fd5b34801561009f57600080fd5b50610190600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192905050506105e3565b005b34801561019e57600080fd5b5061023f600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929050505061069c565b005b34801561024d57600080fd5b506102a8600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610bc1565b6040518082815260200191505060405180910390f35b3480156102ca57600080fd5b50610325600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610dcf565b6040518082815260200191505060405180910390f35b34801561034757600080fd5b506103a2600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610fc3565b6040518082815260200191505060405180910390f35b3480156103c457600080fd5b50610429600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001909291905050506111b7565b005b34801561043757600080fd5b506104e2600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192908035906020019092919050505061146e565b005b3480156104f057600080fd5b506105e1600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192908035906020019092919050505061197b565b005b60016080604051908101604052808681526020018581526020018481526020018381525090806001815401808255809150509060018203906000526020600020906004020160009091929091909150600082015181600001908051906020019061064e929190611a34565b50602082015181600101908051906020019061066b929190611a34565b50604082015181600201556060820151816003019080519060200190610692929190611a34565b5050505050505050565b600080600091505b600180549050821015610bbb57836040518082805190602001908083835b6020831015156106e757805182526020820191506020810190506020830392506106c2565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390206000191660018381548110151561072757fe5b906000526020600020906004020160000160405180828054600181600116156101000203166002900480156107935780601f10610771576101008083540402835291820191610793565b820191906000526020600020905b81548152906001019060200180831161077f575b50509150506040518091039020600019161480156108a15750826040518082805190602001908083835b6020831015156107e257805182526020820191506020810190506020830392506107bd565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390206000191660018381548110151561082257fe5b9060005260206000209060040201600101604051808280546001816001161561010002031660029004801561088e5780601f1061086c57610100808354040283529182019161088e565b820191906000526020600020905b81548152906001019060200180831161087a575b5050915050604051809103902060001916145b15610bae57600090505b600080549050811015610b8757836040518082805190602001908083835b6020831015156108ee57805182526020820191506020810190506020830392506108c9565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390206000191660008281548110151561092e57fe5b9060005260206000209060040201600001604051808280546001816001161561010002031660029004801561099a5780601f1061097857610100808354040283529182019161099a565b820191906000526020600020905b815481529060010190602001808311610986575b50509150506040518091039020600019161415610a19576001828154811015156109c057fe5b9060005260206000209060040201600201546000828154811015156109e157fe5b90600052602060002090600402016003015403600082815481101515610a0357fe5b9060005260206000209060040201600301819055505b826040518082805190602001908083835b602083101515610a4f5780518252602082019150602081019050602083039250610a2a565b6001836020036101000a038019825116818451168082178552505050505050905001915050604051809103902060001916600082815481101515610a8f57fe5b90600052602060002090600402016000016040518082805460018160011615610100020316600290048015610afb5780601f10610ad9576101008083540402835291820191610afb565b820191906000526020600020905b815481529060010190602001808311610ae7575b50509150506040518091039020600019161415610b7a57600182815481101515610b2157fe5b906000526020600020906004020160020154600082815481101515610b4257fe5b90600052602060002090600402016003015401600082815481101515610b6457fe5b9060005260206000209060040201600301819055505b80806001019150506108ab565b6000600183815481101515610b9857fe5b9060005260206000209060040201600201819055505b81806001019250506106a4565b50505050565b600080600090505b600080549050811015610dc857826040518082805190602001908083835b602083101515610c0c5780518252602082019150602081019050602083039250610be7565b6001836020036101000a038019825116818451168082178552505050505050905001915050604051809103902060001916600082815481101515610c4c57fe5b90600052602060002090600402016000016040518082805460018160011615610100020316600290048015610cb85780601f10610c96576101008083540402835291820191610cb8565b820191906000526020600020905b815481529060010190602001808311610ca4575b50509150506040518091039020600019161415610dbb577f29c34b2457eac21231749c61c7af7a2e5f2125a1ce7697f750843de391477aba600082815481101515610cff57fe5b906000526020600020906004020160030154846040518083815260200180602001828103825283818151815260200191508051906020019080838360005b83811015610d58578082015181840152602081019050610d3d565b50505050905090810190601f168015610d855780820380516001836020036101000a031916815260200191505b50935050505060405180910390a1600081815481101515610da257fe5b9060005260206000209060040201600301549150610dc9565b8080600101915050610bc9565b5b50919050565b6000806000809050600091505b600180549050821015610f1557836040518082805190602001908083835b602083101515610e1f5780518252602082019150602081019050602083039250610dfa565b6001836020036101000a038019825116818451168082178552505050505050905001915050604051809103902060001916600183815481101515610e5f57fe5b90600052602060002090600402016000016040518082805460018160011615610100020316600290048015610ecb5780601f10610ea9576101008083540402835291820191610ecb565b820191906000526020600020905b815481529060010190602001808311610eb7575b50509150506040518091039020600019161415610f0857600182815481101515610ef157fe5b906000526020600020906004020160020154810190505b8180600101925050610ddc565b7f0f4415facf6ca509c900aec79588d29af57760db72cb618650b3f0d468f3505281856040518083815260200180602001828103825283818151815260200191508051906020019080838360005b83811015610f7e578082015181840152602081019050610f63565b50505050905090810190601f168015610fab5780820380516001836020036101000a031916815260200191505b50935050505060405180910390a18092505050919050565b6000806000809050600091505b60018054905082101561110957836040518082805190602001908083835b6020831015156110135780518252602082019150602081019050602083039250610fee565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390206000191660018381548110151561105357fe5b906000526020600020906004020160010160405180828054600181600116156101000203166002900480156110bf5780601f1061109d5761010080835404028352918201916110bf565b820191906000526020600020905b8154815290600101906020018083116110ab575b505091505060405180910390206000191614156110fc576001828154811015156110e557fe5b906000526020600020906004020160020154810190505b8180600101925050610fd0565b7f0ffbeff4cdd33a27794ccaf9273186a904ddbb26a50fc1808911432ee1d5a1d681856040518083815260200180602001828103825283818151815260200191508051906020019080838360005b83811015611172578082015181840152602081019050611157565b50505050905090810190601f16801561119f5780820380516001836020036101000a031916815260200191505b50935050505060405180910390a18092505050919050565b6000806000809150600092505b6001805490508310156112fd57846040518082805190602001908083835b60208310151561120757805182526020820191506020810190506020830392506111e2565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390206000191660018481548110151561124757fe5b906000526020600020906004020160010160405180828054600181600116156101000203166002900480156112b35780601f106112915761010080835404028352918201916112b3565b820191906000526020600020905b81548152906001019060200180831161129f575b505091505060405180910390206000191614156112f0576001838154811015156112d957fe5b906000526020600020906004020160020154820191505b82806001019350506111c4565b838210151561146757600090505b60008054905081101561146657846040518082805190602001908083835b60208310151561134e5780518252602082019150602081019050602083039250611329565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390206000191660008281548110151561138e57fe5b906000526020600020906004020160000160405180828054600181600116156101000203166002900480156113fa5780601f106113d85761010080835404028352918201916113fa565b820191906000526020600020905b8154815290600101906020018083116113e6575b50509150506040518091039020600019161415611459578360008281548110151561142157fe5b9060005260206000209060040201600301540160008281548110151561144357fe5b9060005260206000209060040201600301819055505b808060010191505061130b565b5b5050505050565b60008060008060009250600093505b6001805490508410156115b657866040518082805190602001908083835b6020831015156114c0578051825260208201915060208101905060208303925061149b565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390206000191660018581548110151561150057fe5b9060005260206000209060040201600101604051808280546001816001161561010002031660029004801561156c5780601f1061154a57610100808354040283529182019161156c565b820191906000526020600020905b815481529060010190602001808311611558575b505091505060405180910390206000191614156115a95760018481548110151561159257fe5b906000526020600020906004020160020154830192505b838060010194505061147d565b848310151561197257600091505b60018054905082101561197157866040518082805190602001908083835b60208310151561160757805182526020820191506020810190506020830392506115e2565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390206000191660018381548110151561164757fe5b906000526020600020906004020160010160405180828054600181600116156101000203166002900480156116b35780601f106116915761010080835404028352918201916116b3565b820191906000526020600020905b81548152906001019060200180831161169f575b50509150506040518091039020600019161415611964576001828154811015156116d957fe5b90600052602060002090600402016002015490508481101561181e578085039450600060018381548110151561170b57fe5b90600052602060002090600402016002018190555061181960018381548110151561173257fe5b90600052602060002090600402016000018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156117d75780601f106117ac576101008083540402835291602001916117d7565b820191906000526020600020905b8154815290600101906020018083116117ba57829003601f168201915b505050505087836040805190810160405280600781526020017f62656c69657665000000000000000000000000000000000000000000000000008152506105e3565b611963565b8460018381548110151561182e57fe5b9060005260206000209060040201600201540360018381548110151561185057fe5b90600052602060002090600402016002018190555061195e60018381548110151561187757fe5b90600052602060002090600402016000018054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561191c5780601f106118f15761010080835404028352916020019161191c565b820191906000526020600020905b8154815290600101906020018083116118ff57829003601f168201915b505050505087876040805190810160405280600781526020017f62656c69657665000000000000000000000000000000000000000000000000008152506105e3565b611971565b5b81806001019250506115c4565b5b50505050505050565b6000608060405190810160405280868152602001858152602001848152602001838152509080600181540180825580915050906001820390600052602060002090600402016000909192909190915060008201518160000190805190602001906119e6929190611a34565b506020820151816001019080519060200190611a03929190611a34565b506040820151816002019080519060200190611a20929190611a34565b506060820151816003015550505050505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10611a7557805160ff1916838001178555611aa3565b82800160010185558215611aa3579182015b82811115611aa2578251825591602001919060010190611a87565b5b509050611ab09190611ab4565b5090565b611ad691905b80821115611ad2576000816000905550600101611aba565b5090565b905600a165627a7a72305820de4110fd7051220e2a68a040c8f5a5ec0436bed50f2fd9f47ddcc10571e062500029";

    public static final String ABI = "[{\"constant\":false,\"inputs\":[{\"name\":\"f\",\"type\":\"string\"},{\"name\":\"t\",\"type\":\"string\"},{\"name\":\"m\",\"type\":\"uint256\"},{\"name\":\"s\",\"type\":\"string\"}],\"name\":\"createReceipt\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"f\",\"type\":\"string\"},{\"name\":\"t\",\"type\":\"string\"}],\"name\":\"endReceipt\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"f\",\"type\":\"string\"}],\"name\":\"totalMoney\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"f\",\"type\":\"string\"}],\"name\":\"fromReceipt\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"f\",\"type\":\"string\"}],\"name\":\"toReceipt\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"creator\",\"type\":\"string\"},{\"name\":\"m\",\"type\":\"uint256\"}],\"name\":\"borrowFromBank\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"creator\",\"type\":\"string\"},{\"name\":\"t\",\"type\":\"string\"},{\"name\":\"m\",\"type\":\"uint256\"}],\"name\":\"transferReceipt\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"n\",\"type\":\"string\"},{\"name\":\"a\",\"type\":\"string\"},{\"name\":\"c\",\"type\":\"string\"},{\"name\":\"t\",\"type\":\"uint256\"}],\"name\":\"createCompany\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"ret\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"f\",\"type\":\"string\"}],\"name\":\"TotalMoneyEvent\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"ret\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"f\",\"type\":\"string\"}],\"name\":\"FromReceiptEvent\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"ret\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"f\",\"type\":\"string\"}],\"name\":\"ToReceiptEvent\",\"type\":\"event\"}]";

    public static final String FUNC_CREATERECEIPT = "createReceipt";

    public static final String FUNC_ENDRECEIPT = "endReceipt";

    public static final String FUNC_TOTALMONEY = "totalMoney";

    public static final String FUNC_FROMRECEIPT = "fromReceipt";

    public static final String FUNC_TORECEIPT = "toReceipt";

    public static final String FUNC_BORROWFROMBANK = "borrowFromBank";

    public static final String FUNC_TRANSFERRECEIPT = "transferReceipt";

    public static final String FUNC_CREATECOMPANY = "createCompany";

    public static final Event TOTALMONEYEVENT_EVENT = new Event("TotalMoneyEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event FROMRECEIPTEVENT_EVENT = new Event("FromReceiptEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event TORECEIPTEVENT_EVENT = new Event("ToReceiptEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected Project(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Project(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Project(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Project(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> createReceipt(String f, String t, BigInteger m, String s) {
        final Function function = new Function(
                FUNC_CREATERECEIPT, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(f), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(t), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(m), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(s)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void createReceipt(String f, String t, BigInteger m, String s, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_CREATERECEIPT, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(f), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(t), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(m), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(s)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String createReceiptSeq(String f, String t, BigInteger m, String s) {
        final Function function = new Function(
                FUNC_CREATERECEIPT, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(f), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(t), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(m), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(s)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> endReceipt(String f, String t) {
        final Function function = new Function(
                FUNC_ENDRECEIPT, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(f), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(t)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void endReceipt(String f, String t, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_ENDRECEIPT, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(f), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(t)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String endReceiptSeq(String f, String t) {
        final Function function = new Function(
                FUNC_ENDRECEIPT, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(f), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(t)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> totalMoney(String f) {
        final Function function = new Function(
                FUNC_TOTALMONEY, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(f)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void totalMoney(String f, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_TOTALMONEY, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(f)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String totalMoneySeq(String f) {
        final Function function = new Function(
                FUNC_TOTALMONEY, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(f)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> fromReceipt(String f) {
        final Function function = new Function(
                FUNC_FROMRECEIPT, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(f)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void fromReceipt(String f, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_FROMRECEIPT, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(f)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String fromReceiptSeq(String f) {
        final Function function = new Function(
                FUNC_FROMRECEIPT, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(f)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> toReceipt(String f) {
        final Function function = new Function(
                FUNC_TORECEIPT, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(f)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void toReceipt(String f, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_TORECEIPT, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(f)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String toReceiptSeq(String f) {
        final Function function = new Function(
                FUNC_TORECEIPT, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(f)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> borrowFromBank(String creator, BigInteger m) {
        final Function function = new Function(
                FUNC_BORROWFROMBANK, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(creator), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(m)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void borrowFromBank(String creator, BigInteger m, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_BORROWFROMBANK, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(creator), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(m)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String borrowFromBankSeq(String creator, BigInteger m) {
        final Function function = new Function(
                FUNC_BORROWFROMBANK, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(creator), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(m)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> transferReceipt(String creator, String t, BigInteger m) {
        final Function function = new Function(
                FUNC_TRANSFERRECEIPT, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(creator), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(t), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(m)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void transferReceipt(String creator, String t, BigInteger m, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_TRANSFERRECEIPT, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(creator), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(t), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(m)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String transferReceiptSeq(String creator, String t, BigInteger m) {
        final Function function = new Function(
                FUNC_TRANSFERRECEIPT, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(creator), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(t), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(m)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> createCompany(String n, String a, String c, BigInteger t) {
        final Function function = new Function(
                FUNC_CREATECOMPANY, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(n), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(a), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(c), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(t)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void createCompany(String n, String a, String c, BigInteger t, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_CREATECOMPANY, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(n), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(a), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(c), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(t)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String createCompanySeq(String n, String a, String c, BigInteger t) {
        final Function function = new Function(
                FUNC_CREATECOMPANY, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(n), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(a), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(c), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(t)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public List<TotalMoneyEventEventResponse> getTotalMoneyEventEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TOTALMONEYEVENT_EVENT, transactionReceipt);
        ArrayList<TotalMoneyEventEventResponse> responses = new ArrayList<TotalMoneyEventEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TotalMoneyEventEventResponse typedResponse = new TotalMoneyEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.ret = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.f = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void registerTotalMoneyEventEventLogFilter(String fromBlock, String toBlock, List<String> otherTopcs, EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(TOTALMONEYEVENT_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,fromBlock,toBlock,otherTopcs,callback);
    }

    public void registerTotalMoneyEventEventLogFilter(EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(TOTALMONEYEVENT_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,callback);
    }

    public List<FromReceiptEventEventResponse> getFromReceiptEventEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(FROMRECEIPTEVENT_EVENT, transactionReceipt);
        ArrayList<FromReceiptEventEventResponse> responses = new ArrayList<FromReceiptEventEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FromReceiptEventEventResponse typedResponse = new FromReceiptEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.ret = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.f = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void registerFromReceiptEventEventLogFilter(String fromBlock, String toBlock, List<String> otherTopcs, EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(FROMRECEIPTEVENT_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,fromBlock,toBlock,otherTopcs,callback);
    }

    public void registerFromReceiptEventEventLogFilter(EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(FROMRECEIPTEVENT_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,callback);
    }

    public List<ToReceiptEventEventResponse> getToReceiptEventEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TORECEIPTEVENT_EVENT, transactionReceipt);
        ArrayList<ToReceiptEventEventResponse> responses = new ArrayList<ToReceiptEventEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ToReceiptEventEventResponse typedResponse = new ToReceiptEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.ret = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.f = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void registerToReceiptEventEventLogFilter(String fromBlock, String toBlock, List<String> otherTopcs, EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(TORECEIPTEVENT_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,fromBlock,toBlock,otherTopcs,callback);
    }

    public void registerToReceiptEventEventLogFilter(EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(TORECEIPTEVENT_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,callback);
    }

    @Deprecated
    public static Project load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Project(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Project load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Project(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Project load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Project(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Project load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Project(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Project> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Project.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Project> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Project.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Project> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Project.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Project> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Project.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class TotalMoneyEventEventResponse {
        public Log log;

        public BigInteger ret;

        public String f;
    }

    public static class FromReceiptEventEventResponse {
        public Log log;

        public BigInteger ret;

        public String f;
    }

    public static class ToReceiptEventEventResponse {
        public Log log;

        public BigInteger ret;

        public String f;
    }
}
