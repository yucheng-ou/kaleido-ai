<div align="center">


<img src="images/logo.png" width="120" alt="Kaleido Logo" />

# 🎨 Kaleido AI 智能衣柜

> 基于微服务架构的AI智能衣柜，让每一次穿搭都更懂你

[![JDK](https://img.shields.io/badge/JDK-21-blue)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025.0.0-green)](https://spring.io/projects/spring-cloud)
[![Spring AI](https://img.shields.io/badge/Spring%20AI-1.1.2-orange)](https://spring.io/projects/spring-ai)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
![Stars](https://img.shields.io/badge/Stars-49-red)
[![中文文档](https://img.shields.io/badge/文档-中文-blueviolet)](README.md)

[✨ 核心特性](#-核心特性) • [🏗️ 架构设计](#️-架构设计) • [🚀 快速开始](#-快速开始) • [📖 文档](#-文档)

</div>

---

## 📌 项目简介

Kaleido AI 智能衣柜是一个采用 **微服务架构** 的AI智能穿搭管理系统，基于 **DDD领域驱动设计 + CQRS + 整洁架构** 理念构建。

### 核心功能

- 👑 **后台管理系统**
   - 完善的用户权限管理（RBAC）
   - AI Agent 创建与管理
   - 工作流编排引擎
   - 数据可视化与运营监控

- 📱 **App客户端系统**
   - **服装管理**：创建服装、管理衣橱、批量导入
   - **智能搭配**：AI创建搭配方案、风格推荐
   - **个性化配置**：用户信息管理、偏好设置
   - **激励体系**：金币机制、用户行为返利、积分兑换
   - **智能助手**：AI对话咨询、实时穿搭建议

<div align="center">
  <img src="images/%E4%B8%9A%E5%8A%A1%E5%8A%9F%E8%83%BD.jpg" width="800" alt="业务功能"/>
</div>

> 💡 **核心价值**：将传统的衣柜管理升级为智能化、个性化的穿搭体验

---

## ✨ 核心特性

### 🎯 技术亮点

| 特性 | 说明 |
|------|------|
| **DDD + CQRS** | 领域驱动设计，读写分离，领域模型独立 |
| **整洁架构** | 四层架构（接口层、应用层、领域层、基础设施层），依赖倒置 |
| **Spring AI集成** | 无缝对接LLM、向量数据库、RAG能力 |
| **微服务架构** | 11个业务服务 + 23个通用模块，独立部署、弹性扩展 |

### 🛠️ 技术栈
<div align="center">
  <img src="images/%E6%8A%80%E6%9C%AF%E6%A0%88.jpg" width="800" alt="业务功能"/>
</div>

#### 核心框架
- **Java 21**（LTS版本，虚拟线程支持）
- **Spring Boot 3.5.6** + **Spring Cloud 2025.0.0**
- **Spring AI 1.1.2**（AI集成框架）
- **Spring Cloud Alibaba 2025.0.0.0**

#### 微服务生态
- **Nacos 2.3**：服务注册 + 配置中心
- **Sentinel 1.8.7**：流量控制 + 降级熔断
- **Dubbo 3.3.0**：高性能RPC框架
- **Seata 1.8.0**：分布式事务（AT模式）
- **XXL-Job 3.3.2**：分布式任务调度

#### 数据存储
- **MySQL 8.4.0**：关系型数据，ShardingSphere分库分表
- **MongoDB 7.0+**：文档型数据，灵活Schema
- **Milvus 2.4+**：向量数据库，AI特征存储和相似度搜索
- **Redis 6+**：缓存，JetCache多级缓存（Caffeine + Redis）
- **MinIO 8.6.0**：对象存储，图片、文件管理

#### 中间件
- **RabbitMQ 3.13+**：消息队列，异步解耦
- **Redisson 3.52.0**：Redis客户端，分布式锁
- **JetCache 2.7.8**：多级缓存框架

#### 工具库
- **Sa-Token 1.44.0**：轻量级权限认证
- **MyBatis-Plus 3.5.9**：ORM框架
- **MapStruct 1.6.3**：类型安全的对象映射
- **Smart-Doc 2.7.7**：零侵入API文档生成
- **Dynamic-TP 1.2.2-x**：动态线程池监控

#### 完整技术清单

<details>
<summary>📋 点击展开完整技术版本清单</summary>

| 技术 | 版本 | 说明 |
|------|------|------|
| Java | 21 | 开发语言 |
| Spring Boot | 3.5.6 | 应用框架 |
| Spring Cloud | 2025.0.0 | 微服务框架 |
| Spring Cloud Alibaba | 2025.0.0.0 | 阿里云微服务组件 |
| Spring AI | 1.1.2 | AI集成框架 |
| MyBatis-Plus | 3.5.9 | ORM框架 |
| MySQL | 8.4.0 | 关系型数据库 |
| MongoDB | 7.0+ | 文档数据库，用于非结构化数据存储 |
| Milvus | 2.4+ | 向量数据库，用于AI特征存储和相似度搜索 |
| MinIO | 8.6.0 | 对象存储 |
| shardingsphere-jdbc | 5.5.2 | 分库分表 |
| Redis | 6+ | 缓存 |
| Redisson | 3.52.0 | Redis客户端 |
| JetCache | 2.7.8 | 多级缓存框架 |
| Caffeine | 3.2.3 | 本地缓存 |
| Sa-Token | 1.44.0 | 权限认证框架 |
| Sentinel | 1.8.7 | 流量控制和服务降级 |
| Dubbo | 3.3.0 | RPC框架 |
| Nacos | 2.3.0 | 服务注册与配置中心 |
| Seata | 1.8.0 | 分布式事务解决方案 |
| XXL-Job | 3.3.2 | 分布式任务调度 |
| RabbitMQ | 3.13+ | 消息中间件，用于异步通信和解耦系统 |
| Spring AMQP | 3.1.8 | RabbitMQ集成框架 |
| Smart-doc | 2.7.7 | API文档生成 |
| MapStruct | 1.6.3 | 对象映射工具 |
| Lombok | 1.18.36 | 代码简化工具 |
| Hutool | 5.8.38 | Java工具库 |
| PageHelper | 2.1.1 | MyBatis分页插件 |
| Dynamic-tp | 1.2.2-x | 动态线程池 |

</details>

---

## 🏗️ 架构设计

### 系统架构
<div align="center">
  <img src="images/%E7%B3%BB%E7%BB%9F%E6%9E%B6%E6%9E%84.jpg" width="800" alt="系统架构"/>
</div>

### 业务架构
<div align="center">
  <img src="images/%E4%B8%9A%E5%8A%A1%E6%9E%B6%E6%9E%84.jpg" width="800" alt="业务架构"/>
</div>

### 代码分层设计
<div align="center">
  <img src="images/%E5%88%86%E5%B1%82%E6%9E%B6%E6%9E%84.jpg" width="800" alt="代码分层设计"/>
</div>
<div align="center">
  <img src="images/%E7%9B%AE%E5%BD%95%E7%BB%93%E6%9E%84.png" width="800" alt="目录结构"/>
</div>

---

## 📁 项目结构

```
kaleido-server/
├── kaleido-common/                    # 公共模块
│   ├── kaleido-aop/                   # AOP切面
│   ├── kaleido-api/                   # API接口定义
│   ├── kaleido-base/                  # 基础工具类
│   ├── kaleido-cache/                 # 缓存模块
│   ├── kaleido-distribute/            # 分布式相关
│   ├── kaleido-doc/                   # 文档生成
│   ├── kaleido-ds/                    # 数据源
│   ├── kaleido-dynamic-tp/            # 动态线程池
│   ├── kaleido-file/                  # 文件服务
│   ├── kaleido-job/                   # 任务调度
│   ├── kaleido-limiter/               # 限流器
│   ├── kaleido-lock/                  # 分布式锁
│   ├── kaleido-mq/                    # 消息队列
│   ├── kaleido-nacos/                 # 配置中心
│   ├── kaleido-rpc/                   # RPC相关
│   ├── kaleido-sa-token/              # 权限认证
│   ├── kaleido-seata/                 # 分布式事务
│   ├── kaleido-sentinel/              # 限流降级
│   ├── kaleido-sms/                   # 短信服务
│   └── kaleido-web/                   # Web相关配置
│
├── kaleido-gateway/                   # API网关
├── kaleido-auth/                      # 认证授权服务
├── kaleido-biz/                       # 业务服务集合
│   ├── kaleido-user/                  # 用户服务
│   ├── kaleido-wardrobe/              # 衣柜服务
│   ├── kaleido-ai/                    # AI服务
│   ├── kaleido-recommend/             # 推荐服务
│   ├── kaleido-tag/                   # 标签服务
│   ├── kaleido-coin/                  # 金币服务
│   └── kaleido-mcp/                   # MCP服务
├── kaleido-notice/                    # 通知服务
├── kaleido-admin/                     # 管理后台服务
├── doc/                               # 项目文档
├── scripts/                           # 脚本文件
├── .gitignore                         # Git忽略配置
├── pom.xml                            # 父POM文件
└── README.md                          # 项目说明文档
```

---

## 🚀 快速开始

### 1. 前置准备

在开始之前，请确保您的开发环境已安装以下软件：

- **JDK 21+**: 项目基于 Java 21 开发 (参考 pom.xml)。
- **Maven**: 用于项目构建和依赖管理。
- **Docker & Docker Compose**: 用于运行数据库、中间件等基础环境。

### 2. 克隆项目

请根据您的网络环境选择代码仓库：

- **Gitee (国内推荐)**
  ```bash
  git clone https://gitee.com/ou-yucheng/kaleido-ai.git
  ```

- **GitHub**
  ```bash
  git clone https://github.com/yucheng-ou/kaleido-ai.git
  ```

### 3. 启动基础环境

项目依赖 MySQL, Redis, Nacos, MinIO 等中间件，使用 Docker Compose 一键启动。

```bash
# 将doc/deploy/dev-ops目录拷贝到服务器上
cd doc/deploy/dev-ops

# 启动基础环境 (加上 -d 后台运行)
docker-compose -f docker-compose-base-env.yml up -d
```

> **⚠️ 环境自检**
> 启动命令执行后，请务必执行以下检查，确保所有容器运行正常：

**1. 检查容器状态**

```bash
docker compose -f docker-compose-base-env.yml ps
```
请确认以下关键容器的状态为 `Up`。

**2. 检查数据库是否已经成功创建**

默认会自动创建数据库，不需要再手动创建脚本，连接并检查数据库是否已经创建完成。

### 4. Nacos 初始化配置

务必完成以下配置，否则微服务将无法启动。

#### 4.1 创建命名空间

登录 Nacos 控制台，进入 **命名空间 (Namespaces)**，创建以下两个命名空间（id可以使用下面表格中的 也可以自己生成）：

| 命名空间名称 | 命名空间 ID (必填) | 用途 |
|---|---|---|
| kaleido | 30d71fbd-2d24-4757-81f4-679d26f0ed93 | 主配置与服务发现 |
| dubbo | aa3a3ee8-fb98-43e5-b3da-11b368d88c21 | Dubbo RPC 服务 |

#### 4.2 导入配置

1. 进入 **配置管理 -> 配置列表**。
2. 顶部切换到 `kaleido` 命名空间。
3. 点击 **导入配置**，选择文件：`doc/deploy/nacos/nacos_config_export.zip`。
4. 检查配置列表是否已成功加载 `kaleido-gateway-dev.yml` 等文件。

### 5. 本地开发配置修改

#### 5.1 修改 ShardingSphere 数据源

修改 `kaleido-common/kaleido-ds/src/main/resources/sharding.yaml`，将数据库地址改成自己的。

#### 5.2 创建MinIO密钥

Docker 启动的 MinIO 默认密钥可能与配置不一致，建议手动创建或同步：

1. 登录 MinIO 控制台
2. 进入 **Access Keys -> Create access key**。
3. 记录生成的 Access Key 和 Secret Key。

#### 5.3 修改并执行环境变量脚本

1. **修改** ：修改 `doc/deploy/init_env.bat` 脚本，将配置替换为自己的，主要修改ip地址、minio密钥与模型的配置信息，对于AI的API调用，我是用的硅基流动，你可以替换为你自己的。
2. **执行**: 双击运行或在终端执行该脚本。
3. **重启**: 必须重启 IDE (IntelliJ IDEA) 以加载新的环境变量，如果还不行重启电脑。

### 6. 服务启动

按依赖顺序启动微服务：

1. **Gateway 服务**: `kaleido-gateway` (端口 9010)
2. **Auth 服务**: `kaleido-auth`
3. **Admin 服务**: `kaleido-admin`
4. **业务服务**: `kaleido-user`, `kaleido-wardrobe` 等

项目启动成功后检查服务是否已经成功注册到nacos上面。

---

## 📋 开发计划

### 近期规划

- [ ] 集成ELK可视化日志管理
- [ ] 部署Grafana + Prometheus监控体系
- [ ] 接入SkyWalking链路追踪
- [ ] 前端页面开发

### 长期规划

- [ ] 多端适配（小程序、App）
- [ ] 社区穿搭分享功能
- [ ] 智能购物推荐
- [ ] AR虚拟试衣

---

## 📖 文档

- [API文档](doc/api/)
- [部署文档](doc/deploy/)
- [数据库设计](doc/sql/)

---

## 📞 联系作者

<img src="images/%E5%BE%AE%E4%BF%A1%E5%90%8D%E7%89%87.png" width="400" alt="微信名片"/>

> 💬 有问题欢迎提Issue或者加我微信联系，我会尽可能解决 😺

---

## 📄 License

本项目采用 [MIT License](LICENSE) 开源协议。

---

<div align="center">

如果这个项目对你有帮助，请给一个 ⭐️ Star 支持一下！

</div>