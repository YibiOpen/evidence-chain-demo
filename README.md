# 基于 FISCO BCOS实现的电子存证平台
由杭州亦笔科技有限公司开发的针对基于 FISCO BCOS 的区块链电子存证平台案例。

## 1.背景概述

​		随着人们的生活和工作越来越互联网化，产生了大量的线上交互业务的电子数据，但电子数据具有易变性、易改无痕性等特性。作为平台出现问题后最基本的维权资料，这些易变易改的电子数据往往都存管于平台自身的服务器中，若没有第三方或者权威机构的介入，如何证明这些数据有没有被篡改过？平台的在线合同基本由系统生成，其是否具有法律效力，是否能在诉讼中被法院采纳？故交易双方与平台方都需要保存这些电子数据，并使用技术确保这些电子数据的客观性、完整性与真实性，而不是被篡改、被删改、被伪造的，才能够在纠纷产生时作为可以置信的电子数据证据呈上法庭。并且随着互联网应用的飞速发展，大量这些业务平台需要能够将电子数据实时存储，并能够同步到第三方中立的电子证据存管机构，然后政府、司法机构将这些第三方电子证据存管机构使用标准与规范监管起来，并能够随时根据需要，查询与提取相应的电子证据，另外还可能需要公证处与司法鉴定，来进一步确认电子证据的真实性与完整性。这些就要求建立一个电子证据管理平台系统，能够包括所有这些管理功能，并形式一整套标准与规范。

## 2.解决方案

​		全数据生命周期电子数据存管与证明解决方案理念的提出，从电子数据的特征、到人民法院审理案件对证据的要求、再到电子数据的存管，提供了一整套完整的解决方案。电子数据易变、易改无痕、不易固化呈现和归档的特征，与人民法院审理案件对证据的要求：相对固定的、没有被篡改的、能在法庭上直观呈现并能卷宗归档是相悖的。而该解决方案从数据的生成和创建、传输和存储、数据取得三个方面解决：数据生成和创建时就同步实施“实时完整性备份”，在第一时间（最短的时间）解决了证据固化和保存；最高级别加密传输保护、公安部完整性鉴别、分布式云存储隔离和安全防护保障，保证数据实时同步备份过程及存储过程中没有被篡改，从而保证证据的真实性；引入司法机构参与存证的监察管理，并以电子证书的形式对此电子数据内容进行直观呈现和形式固定，解决证据取得、法庭质证呈现及归档问题。

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

#### 4.3.1前提条件

| 序号 | 软件               |
| ---- | ------------------ |
| 1    | MySQL5.6或以上版本 |
| 2    | Java8或以上版本    |

#### 4.3.2发布包部署

- 获取发布包

```
wget -c https://github.com/YibiOpen/evidence-chain-demo/releases/download/1.0.1/evidence-chain-demo-1.0.1.tar.gz
```

- 执行sql脚本

```
在mysql中执行sql脚本，脚本文件在源代码资源db目录下，其中evi_ddl.sql为建表语句、evi_dml.sql为初始化语句
```

- 修改conf目录下application.properties配置文件

```
可以根据实际情况修改应用端口
server.port=

修改数据库配置
spring.datasource.username
spring.datasource.password
spring.datasource.url

修改WeBASE Front配置
webase-front.contract.deploy.url
webase-front.trans.handle.url
webase-front.trans.query.url

修改WeBASE Node配置
webase.node.mgr.url
webase.node.mgr.appKey
webase.node.mgr.appSecret
webase.node.mgr.groupId
webase.node.mgr.linkUrl

修改文件存储目录
store.dir
```

- 执行start.sh脚本
- 区块链电子存证平台部署完成后，浏览器地址栏输入：http://ip:port/evidence/index.html，初始用户名和密码分别为：eviAdmin/ABC123

#### 4.3.3源码部署

- 获取源代码

```
git clone https://github.com/YibiOpen/evidence-chain-demo.git
```

- 执行mvn打包命令

```
mvn clean package -DskipTests
```

- 执行sql脚本

```
在mysql中执行sql脚本，脚本文件在源代码资源db目录下，其中evi_ddl.sql为建表语句、evi_dml.sql为初始化语句
```

- 修改application.properties配置文件

```
可以根据实际情况修改应用端口
server.port=

修改数据库配置
spring.datasource.username
spring.datasource.password
spring.datasource.url

修改WeBASE Front配置
webase-front.contract.deploy.url
webase-front.trans.handle.url
webase-front.trans.query.url

修改WeBASE Node配置
webase.node.mgr.url
webase.node.mgr.appKey
webase.node.mgr.appSecret
webase.node.mgr.groupId
webase.node.mgr.linkUrl

修改文件存储目录
store.dir
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

要素管理页面用于展示已经存证的电子数据，且可以进行查看和核验。

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

