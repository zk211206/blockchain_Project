# 源代码相关说明

## asset-app项目的目录结构：

```
|-- build.gradle // gradle配置文件
|-- gradle
|   |-- wrapper
|       |-- gradle-wrapper.jar // 用于下载Gradle的相关代码实现
|       |-- gradle-wrapper.properties // wrapper所使用的配置信息，比如gradle的版本等信息
|-- gradlew // Linux或者Unix下用于执行wrapper命令的Shell脚本
|-- gradlew.bat // Windows下用于执行wrapper命令的批处理脚本
|-- src
|   |-- main
|   |   |-- java
|   |         |-- org
|   |             |-- fisco
|   |                   |-- bcos
|   |                         |-- asset
|   |                               |-- client // 放置客户端调用类
|   |                                      |-- ProjectClient.java
|   |                               |-- contract // 放置Java合约类
|   |                                      |-- Project.java
|   |-- test 
|       |-- resources // 存放代码资源文件
|           |-- applicationContext.xml // 项目配置文件
|           |-- contract.properties // 存储部署合约地址的文件
|           |-- log4j.properties // 日志配置文件
|           |-- contract //存放solidity约文件
|                   |-- Project.sol
|                   |-- Table.sol
|
|-- tool
    |-- asset_run.sh // 项目运行脚本
```

主类是ProjectClient.java，合约是Project.sol，合约生成的java代码是Project.java



## 区块链节点证书配置

拷贝区块链节点对应的SDK证书：

```
$ cp fisco/nodes/127.0.0.1/sdk/* asset-app/src/test/resources/
```



## 编译

```
# 切换到项目目录
$ cd ~/asset-app
# 编译项目
$ ./gradlew build
```



## 运行

具体指令：

```
# 部署合约
$ bash asset_run.sh deploy
# 创建公司
$ bash asset_run.sh createCompany "name" "address" "category" totalMoney
# 创建账单
$ bash asset_run.sh createReceipt "nameA" "nameB" money "status"
# 转让账款
$ bash asset_run.sh transferReceipt "nameA" "nameB" money
# 向银行融资
$ bash asset_run.sh borrowFromBank "name" money
# 应收账款结算
$ bash asset_run.sh endReceipt "nameA" "nameB"
# 获取总金额
$ bash asset_run.sh totalMoney "name"
# 获取借款金额
$ bash asset_run.sh fromReceipt "name"
# 获取被借款金额
$ bash asset_run.sh toReceipt "name"
```

