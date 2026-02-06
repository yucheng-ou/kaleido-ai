<div align="center">

**Kaleido AI智能衣柜** - 基于微服务架构的AI智能衣柜管理系统

</div>

---

## 项目介绍

Kaleido AI智能衣柜是基于微服务架构的AI衣物管理系统，为用户提供衣物管理、个性化穿搭方案和智能推荐服务。

采用**DDD + CQRS + 整洁架构**，遵循依赖倒置原则，通过分层架构（接口层、应用层、领域层、基础设施层）确保领域核心独立性。

技术栈基于主流微服务架构：
- **框架**：JDK21、SpringBoot 3.5.6、SpringCloud 2025、SpringAI 1.1.2
- **存储**：MySQL、MongoDB、Milvus、Redis、MinIO、ShardingSphere
- **微服务**：Spring Cloud Alibaba、Nacos、Sentinel、Seata、Dubbo
- **中间件**：RabbitMQ、XXL-Job
- **组件**：Sa-Token、MyBatis-Plus、JetCache、Dynamic-Tp

系统采用模块化微服务架构，包含：

1. __公共模块__：提供缓存、分布式锁、消息队列、限流降级等基础能力
2. __核心业务服务__：用户服务、衣柜服务、AI服务、推荐服务、标签服务、金币服务、MCP服务
3. __支撑服务__：API网关、统一认证中心、通知服务、管理后台

各服务独立部署、领域驱动，形成清晰的技术架构和业务边界，支持快速迭代和业务扩展。

## 架构设计

### 系统架构

![输入图片说明](images/%E7%B3%BB%E7%BB%9F%E6%9E%B6%E6%9E%84.jpg)

### 业务架构

![输入图片说明](images/%E4%B8%9A%E5%8A%A1%E6%9E%B6%E6%9E%84.jpg)

### 代码分层设计

![输入图片说明](images/%E5%88%86%E5%B1%82%E6%9E%B6%E6%9E%84.jpg)

![输入图片说明](images/%E7%9B%AE%E5%BD%95%E7%BB%93%E6%9E%84.png)

## 技术栈

![输入图片说明](images/%E6%8A%80%E6%9C%AF%E6%A0%88.jpg)

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


## 项目目录

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

## 业务功能

![输入图片说明](images/%E4%B8%9A%E5%8A%A1%E5%8A%9F%E8%83%BD.jpg)
## 快速开始

### 环境要求

在开始之前，请确保您的开发环境满足以下要求：

| 组件       | 版本要求 | 说明        |
|----------|------|-----------|
| JDK      | 21+  | Java开发工具包 |
| Maven    | 3.8+ | 项目构建工具    |
| MySQL    | 8.0+ | 关系型数据库    |
| MongoDB  | 7.0+ | 文档数据库     |
| Redis    | 6+   | 缓存数据库     |
| RabbitMQ | 4.2+ | 消息队列      |
| Milvus   | 2.4+ | 向量数据库     |
| XXL-job  | 3.0+ | 计划任务      |

### 准备工作

1. **克隆项目**
   ```bash
   git clone https://github.com/your-org/kaleido-server.git
   cd kaleido-server
   ```
2. **部署环境**

    部署文件参考 doc/deploy
    
    需要启动的服务包括：
    
    - **Nacos**：服务注册与配置中心
      - **Sentinel**：服务注册与配置中心
      - **Seata**：服务注册与配置中心
      - **MySQL**：主数据库
      - **MongoDB**：文档数据库
      - **Redis**：缓存数据库
      - **RabbitMQ**：消息队列
      - **MinIO**：对象存储
      - **Milvus**：向量数据库
      - **XXL-job**：向量数据库
3. **配置环境变量**
   修改文件 doc/deploy/init_env.bat，将环境变量替换成你自己的然后执行

4. **初始化数据库**
   依次执行doc/sql目录中的数据库

5. **导入nacos配置**
   将目录doc/nacos中的配置文件导入到nacos中

### 构建与启动服务

1. **编译项目**
   ```bash
   # 在项目根目录执行
   mvn clean compile -DskipTests
   ```

2. **启动网关服务**
   ```bash
   cd kaleido-gateway
   mvn spring-boot:run
   ```

3. **启动认证服务**
   ```bash
   cd kaleido-auth
   mvn spring-boot:run
   ```

4. **启动业务服务**
   根据需要启动相应的业务服务：
   ```bash
   # 用户服务
   cd kaleido-biz/kaleido-user
   mvn spring-boot:run
   
   # 衣柜服务
   cd kaleido-biz/kaleido-wardrobe
   mvn spring-boot:run
   
   # AI服务
   cd kaleido-biz/kaleido-ai
   mvn spring-boot:run
   
   ...
   ```

### 验证部署

1. **访问 Nacos 控制台**
    - 地址：http://localhost:8848/nacos
    - 默认账号：nacos，密码：nacos
    - 确认所有服务已成功注册

2. **访问 API 文档**
   生成并查看 API 文档：
   ```bash
   # 生成文档
   mvn compile smart-doc:html
   
   # 文档位置：doc/api/{服务名}/index.html
   ```

## TODO

1. **集成ELK可视化日志管理**

2. **Grafana + Prometheus监控体系**

3. **SkyWalking链路追踪**

4. **前端页面开发**
