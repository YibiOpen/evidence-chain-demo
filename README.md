# 基于 FISCO BCOS实现的电子存证平台
由杭州亦笔科技有限公司开发的针对基于 FISCO BCOS 的区块链电子存证平台案例。

## 1.背景概述

​		存证功能是区块链技术的很好的着力点，通过区块链技术可以有效的防止存证系统中的数据出现纂改，保障第三方存证服务的中立性，实现存证服务平台的可信性，存证服务平台同网络仲裁、互联网法院等司法机构接口已经打通，当客户与银行产生借贷纠纷时，可以通过电子存证系统方便快捷的处理。金融线上业务的“用户信息、电子合同信息”等都以电子数据形式存在，考虑到电子证据易改无痕，诉讼举证时法院往往对于电子证据是否被篡改过存疑，通过区块链电子数据存证系统实现电子证据固化，依据区块链技术实现数据防篡改特性，司法机构（仲裁/法院）作为联盟链中的节点，保证司法机构对电子证据的认可。

## 2.解决方案

​		采用区块链可信连接等方式，引入金融机构、存证机构、司法机构组成数据保全联盟链，实现客户每一次线上操作，联盟链全程广播，各节点实时记录，并利用区块链技术的实时监控广播情况、上链情况、留痕情况、证据保全状态、防抵赖等，建立新型的电子数据证据保存系统。

## 3.系统架构

![](https://github.com/maochaowu/evidenceImage/blob/main/%E7%B3%BB%E7%BB%9F%E6%9E%B6%E6%9E%84%E8%AE%BE%E8%AE%A1.png?raw=true)

### 3.1业务流程

#### 3.1.1应用注册及合约部署流程

![](https://github.com/maochaowu/evidenceImage/blob/main/%E5%BA%94%E7%94%A8%E6%B3%A8%E5%86%8C%E5%8F%8A%E5%90%88%E7%BA%A6%E9%83%A8%E7%BD%B2.png?raw=true)

- 系统部署后，通过管理员账号登录，登录成功后系统会判断是否已部署好电子存证合约，没部署则先调用WeBASE开放接口进行应用注册；
- 应用注册好，调用WeBASE开放接口新增私钥用户；
- 私钥用户新增完成后，调用WeBASE Front 模块中的合约部署接口；
- 合约部署完成后，调用WeBASE开放接口进行合约原文保存；
- 合约原文保存后，调用WeBASE开放接口进行合约地址保存。

#### 3.1.2存证数据上链

![](https://github.com/maochaowu/evidenceImage/blob/main/%E5%AD%98%E8%AF%81%E6%95%B0%E6%8D%AE%E4%B8%8A%E9%93%BE.png?raw=true)

- 电子数据存证后，会通过哈希算法对存证数据（原文or附件）进行摘要计算，计算完后针对哈希进行签名；
- 调用WeBASE Front的交易处理接口，进行存证合约的调用，实现存证数据哈希上链；
- 其他机构进行签名并调用WeBASE Front的交易处理接口，实现存证数据哈希签名上链。

#### 3.1.3存证数据核验

![](https://github.com/maochaowu/evidenceImage/blob/main/%E5%AD%98%E8%AF%81%E6%95%B0%E6%8D%AE%E6%A0%B8%E9%AA%8C.png?raw=true)

- 针对要进行数据核验的数据，首先获取到其上链地址；
- 根据合约地址调用WeBASE Front已编码查询交易发送接口，查询出链上存储的上链信息；
- 比对存证系统中的数据和链上获取的到数据是否一致。

## 4.系统运行

### 4.1准备工作

#### 4.1.1联盟链搭建

搭建单群组FISCO BCOS联盟链，可以参考下述地址进行创建

```
https://fisco-bcos-documentation.readthedocs.io/zh_CN/latest/docs/installation.html#fisco-bcos
```

#### 4.1.2WeBASE组件搭建

一键部署在同机快速搭建WeBASE管理台环境，方便用户快速体验WeBASE管理平台，可以参考下述地址进行创建

```
https://webasedoc.readthedocs.io/zh_CN/latest/docs/WeBASE/install.html
```

### 4.2WeBASE创建应用

#### 4.2.1新建应用

![](https://github.com/maochaowu/evidenceImage/blob/main/%E5%BA%94%E7%94%A8%E6%B7%BB%E5%8A%A0.png?raw=true)

- WeBASE管理台部署好后，登录进入管理平台，在应用管理菜单中，点击新建应用，选择自定义应用；
- 输入区块链电子存证平台应用相关信息后，点击确定按钮，完成应用添加。

#### 4.2.2查看注册信息

注册完成后，在应用管理菜单下的，应用列表中可以看到创建的区块链电子存证平台应用，点击注册信息，可以获取应用注册信息，如下图的appKey和appSecret。

![](https://github.com/maochaowu/evidenceImage/blob/main/%E5%AD%98%E8%AF%81%E5%BA%94%E7%94%A8%E6%B3%A8%E5%86%8C%E4%BF%A1%E6%81%AF.png?raw=true)

### 4.3 区块链电子存证平台部署

区块链电子存证平台是基于SpringBoot 2.1.0.RELEASE实现（前端页面通过后端直接加载）

支持**发布包部署**和**源码部署**

#### 4.3.1前提条件

| 序号 | 软件               |
| ---- | ------------------ |
| 1    | MySQL5.6或以上版本 |
| 2    | Java8或以上版本    |

#### 4.3.2发布包部署

- 获取发布包，并解压

```
wget -c https://github.com/YibiOpen/evidence-chain-demo/releases/download/1.0.1/evidence-chain-demo-1.0.1.tar.gz
tar -xvf evidence-chain-demo-1.0.1.tar.gz
cd evidence-chain-demo/
```

- 初始化数据库，执行sql脚本

在mysql中执行sql脚本，脚本文件在源代码资源db目录下，其中`evi_ddl.sql`为建表语句、`evi_dml.sql`为初始化语句

以mysql用户root，密码为123456，创建数据库为`evi_chain`为例
```Bash
# 创建DB
mysql -uroot -p123456 -e "create database evi_chain;"
# 执行脚本
cd db/
# 建表
mysql -uroot -p123456 -D evi_chain -e "source ./evi_ddl.sql"
# 插入默认数据
mysql -uroot -p123456 -D evi_chain -e "source ./evi_dml.sql"
```

- 修改conf目录下application.properties配置文件

```Bash
# 可以根据实际情况修改应用端口
server.port=9081

### 修改数据库配置
# mysql用户
spring.datasource.username=dbUsername
# mysql密码
spring.datasource.password=dbPassword
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/evi_chain?autoCommit=false&useUnicode=true&characterEncoding=utf-8\
  &serverTimezone=Asia/Shanghai

### 修改webase-front服务IP、端口与接口
# webase前置服务1.2. 合约部署接口（结合WeBASE-Sign）
webase-front.contract.deploy.url=http://127.0.0.1:5002/WeBASE-Front/contract/deployWithSign
# webase前置服务5.1. 交易处理接口（结合WeBASE-Sign）
webase-front.trans.handle.url=http://127.0.0.1:5002/WeBASE-Front/trans/handleWithSign
# webase前置服务5.4. 已编码查询交易发送
webase-front.trans.query.url=http://127.0.0.1:5002/WeBASE-Front/trans/query-transaction

# 修改在webase管理台中应用管理生成的配置
# webase-node-manager后台IP与端口
webase.node.mgr.url=http://127.0.0.1:5001
# webase管控台新建应用生成的url地址
webase.node.mgr.appKey=zhf3mNPA
# webase管控台新建应用生成的url地址
webase.node.mgr.appSecret=bn5BRGsNczszJmFT3urrUg6aZmSVf2KS
# 加密传输
webase.node.mgr.transferEncrypt=true
# 默认群组
webase.node.mgr.groupId=1

# webase应用外链地址，即本项目的前端访问URL，本项目则是http://ip:port/evidence/index.html
webase.node.mgr.linkUrl=http://127.0.0.2:9081/evidence/index.html

# 存证附件存储目录
store.dir=/data/evidence-chain-demo-master/evi_store
```

- 启动

回到上一级目录，执行start.sh脚本
```Bash
cd ../
bash start.sh
```

- 查看服务日志

```Bash
tail -f logs/log/evi_chain.log        
```

- 区块链电子存证平台部署完成后，浏览器地址栏输入：http://ip:port/evidence/index.html，初始用户名和密码分别为：eviAdmin/ABC123

#### 4.3.3源码部署

- 获取源代码

```
git clone https://github.com/YibiOpen/evidence-chain-demo.git
```

- 执行sql脚本


在mysql中执行sql脚本，脚本文件在源代码资源db目录下，其中`evi_ddl.sql`为建表语句、`evi_dml.sql`为初始化语句

以mysql用户root，密码为123456，创建数据库为`evi_chain`为例
```Bash
# 创建DB
mysql -uroot -p123456 -e "create database evi_chain;"
# 执行脚本
cd db/
# 建表
mysql -uroot -p123456 -D evi_chain -e "source ./evi_ddl.sql"
# 插入默认数据
mysql -uroot -p123456 -D evi_chain -e "source ./evi_dml.sql"
```

- 修改conf目录下application.properties配置文件

```Bash
# 可以根据实际情况修改应用端口
server.port=9081

### 修改数据库配置
# mysql用户
spring.datasource.username=dbUsername
# mysql密码
spring.datasource.password=dbPassword
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/evi_chain?autoCommit=false&useUnicode=true&characterEncoding=utf-8\
  &serverTimezone=Asia/Shanghai

### 修改webase-front服务IP、端口与接口
# webase前置服务1.2. 合约部署接口（结合WeBASE-Sign）
webase-front.contract.deploy.url=http://127.0.0.1:5002/WeBASE-Front/contract/deployWithSign
# webase前置服务5.1. 交易处理接口（结合WeBASE-Sign）
webase-front.trans.handle.url=http://127.0.0.1:5002/WeBASE-Front/trans/handleWithSign
# webase前置服务5.4. 已编码查询交易发送
webase-front.trans.query.url=http://127.0.0.1:5002/WeBASE-Front/trans/query-transaction

# 修改在webase管理台中应用管理生成的配置
# webase-node-manager后台IP与端口
webase.node.mgr.url=http://127.0.0.1:5001
# webase管控台新建应用生成的url地址
webase.node.mgr.appKey=zhf3mNPA
# webase管控台新建应用生成的url地址
webase.node.mgr.appSecret=bn5BRGsNczszJmFT3urrUg6aZmSVf2KS
# 加密传输
webase.node.mgr.transferEncrypt=true
# 默认群组
webase.node.mgr.groupId=1

# webase应用外链地址，即本项目的前端访问URL，本项目则是http://ip:port/evidence/index.html
webase.node.mgr.linkUrl=http://127.0.0.2:9081/evidence/index.html

# 存证附件存储目录
store.dir=/data/evidence-chain-demo-master/evi_store
```

- 在`pom.xml`所在目录，打包jar

```Bash
# maven 编译
mvn clean package -Dmaven.test.skip=true
```
打包完成后会得到`target`目录

- 运行程序
```Bash
cd target/
nohup java -jar evidence-chain-demo-1.0.1-SNAPSHOT.jar > nohup.out 2>&1 &
```

- 查看日志
```
tail -f logs/log/evi_chain.log
```

### 4.4.区块链电子存证平台演示

区块链电子存证平台部署完成后，浏览器地址栏输入：http://ip:port/evidence/index.html

初始用户名和密码分别为：eviAdmin/ABC123

#### 4.4.1首页

如下图所示，首页会显示已存证数量和已上链数量，点击登录按钮，跳转到登录页面。

![](https://github.com/maochaowu/evidenceImage/blob/main/%E5%8C%BA%E5%9D%97%E9%93%BE%E7%94%B5%E5%AD%90%E5%AD%98%E8%AF%81%E5%B9%B3%E5%8F%B0%E9%A6%96%E9%A1%B5.png?raw=true)

#### 4.4.2登录

登录页面，输入账户名和密码。

![](https://github.com/maochaowu/evidenceImage/blob/main/%E5%8C%BA%E5%9D%97%E9%93%BE%E7%94%B5%E5%AD%90%E5%AD%98%E8%AF%81%E5%B9%B3%E5%8F%B0%E7%99%BB%E5%BD%95.png?raw=true)

#### 4.4.3合约部署

首次登录，未部署合约时，需要先进行存证合约部署。

![](https://github.com/maochaowu/evidenceImage/blob/main/%E5%88%9D%E5%A7%8B%E5%8C%96%E5%AD%98%E8%AF%81%E5%90%88%E7%BA%A6%E9%83%A8%E7%BD%B2.png?raw=true)

部署完成后，可以在WeBASE中看到对应的合约文件内容

![](https://github.com/maochaowu/evidenceImage/blob/main/%E5%B7%B2%E9%83%A8%E7%BD%B2%E5%AD%98%E8%AF%81%E5%90%88%E7%BA%A6.png?raw=true)

部署完成后，可以在WeBASE中看到对应的合约列表

![](https://github.com/maochaowu/evidenceImage/blob/main/%E5%AD%98%E8%AF%81%E5%90%88%E7%BA%A6%E5%88%97%E8%A1%A8.png?raw=true)

#### 4.4.4产品管理

产品管理页面用于展示已经维护好的存证产品，且可以进行新建和修改，一个产品代表了一个实际业务。

![](https://github.com/maochaowu/evidenceImage/blob/main/%E4%BA%A7%E5%93%81%E7%AE%A1%E7%90%86.png?raw=true)

#### 4.4.5节点管理

产品管理页面用于展示已经维护好的存证节点，且可以进行新建和修改，一个节点代表了一个实际业务下的某个环节或步骤。

![](https://github.com/maochaowu/evidenceImage/blob/main/%E8%8A%82%E7%82%B9%E7%AE%A1%E7%90%86.png?raw=true)

#### 4.4.6要素管理

要素管理页面用于展示已经维护好的存证要素，且可以进行新建和修改，要素即实际业务下某个环节或步骤在操作过程中会生成的电子数据细项。

![](https://github.com/maochaowu/evidenceImage/blob/main/%E8%A6%81%E7%B4%A0%E7%AE%A1%E7%90%86.png?raw=true)

#### 4.4.7模拟存证

如下图所示，用于模拟电子数据存证，选择产品、保全点后会动态展示其存证要素，输入存证要素值，可以选择上传存证附件，最后点击提交按钮，完成存证模拟，存证存证后页面跳转到存证管理。

![](https://github.com/maochaowu/evidenceImage/blob/main/%E6%A8%A1%E6%8B%9F%E5%AD%98%E8%AF%81.png?raw=true)

WeBASE中可以看到调用合约后的交易回执信息

![](https://github.com/maochaowu/evidenceImage/blob/main/%E4%BA%A4%E6%98%93%E5%9B%9E%E6%89%A7.png?raw=true)

#### 4.4.8存证管理

存证管理页面用于展示已经存证的电子数据，且可以进行查看和核验。

![](https://github.com/maochaowu/evidenceImage/blob/main/%E5%AD%98%E8%AF%81%E7%AE%A1%E7%90%86.png?raw=true)

#### 4.4.9存证数据查看

存证数据查看会展示出具体的存证要素内容和所有的存证附件。

![](https://github.com/maochaowu/evidenceImage/blob/main/%E6%9F%A5%E7%9C%8B%E5%AD%98%E8%AF%81%E8%AF%A6%E6%83%85.png?raw=true)

#### 4.4.10存证数据核验

存证数据核验会进行链上数据获取并核对存证数据库中的数据内容。

![](https://github.com/maochaowu/evidenceImage/blob/main/%E6%A0%B8%E9%AA%8C%E5%AD%98%E8%AF%81%E8%AF%81%E6%8D%AE.png?raw=true)

WeBASE中对应的交易信息

![](https://github.com/maochaowu/evidenceImage/blob/main/%E4%BA%A4%E6%98%93%E4%BF%A1%E6%81%AF.png?raw=true)

#### 4.4.11用户和合约

如下图展示了个人中心用户信息及存证合约信息

![](https://github.com/maochaowu/evidenceImage/blob/main/%E4%B8%AA%E4%BA%BA%E4%B8%AD%E5%BF%83.png?raw=true)

#### 4.5调用WeBASE组件的接口说明

针对区块链电子合同平台业务运行过程中所调用WeBASE组件的接口列举如下：

- 应用注册

```
https://webasedoc.readthedocs.io/zh_CN/latest/docs/WeBASE-Node-Manager/appintegration.html#id7
```

- 新增私钥用户

```
https://webasedoc.readthedocs.io/zh_CN/latest/docs/WeBASE-Node-Manager/appintegration.html#id53
```

- 合约部署接口（结合WeBASE-Sign）

```
https://webasedoc.readthedocs.io/zh_CN/latest/docs/WeBASE-Front/interface.html#webase-sign
```

- 合约原文保存接口

```
https://webasedoc.readthedocs.io/zh_CN/latest/docs/WeBASE-Node-Manager/appintegration.html#id80
```

- 合约地址保存接口

```
https://webasedoc.readthedocs.io/zh_CN/latest/docs/WeBASE-Node-Manager/appintegration.html#id84
```

- 交易处理接口（结合WeBASE-Sign）

```
https://webasedoc.readthedocs.io/zh_CN/latest/docs/WeBASE-Front/interface.html#id375
```

- 已编码查询交易发送

```
https://webasedoc.readthedocs.io/zh_CN/latest/docs/WeBASE-Front/interface.html#id393
```

## 5.总体说明

系统中目前只是简单的实现了数据存证的上下链，针对多方签名暂时还未进行开发。

