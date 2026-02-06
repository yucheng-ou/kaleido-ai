

# Kaleido AI智能衣柜

## 项目介绍

Kaleido AI智能衣柜是基于微服务架构的AI衣物管理系统，为用户提供衣物管理、个性化穿搭方案和智能推荐服务。

本项目采用 **DDD（领域驱动设计）** + **CQRS（命令查询职责分离）** + **整洁架构（Clean Architecture）** 设计理念，遵循依赖倒置原则，通过分层架构（接口层、应用层、领域层、基础设施层）确保领域核心的独立性与稳定性。

技术栈基于主流微服务架构构建，具备高可用、易扩展的特性。

## 业务功能

本系统旨在为用户提供一站式的衣物智能管理体验，主要功能包括：

*   **衣物管理**：支持用户添加、编辑、分类和整理个人衣物库。
*   **智能穿搭**：基于AI算法，根据天气、场合、风格偏好自动生成穿搭方案。
*   **智能推荐**：利用向量数据库（Milvus）进行相似度匹配，推荐潮流搭配。
*   **标签服务**：对衣物进行多维度打标，便于检索和推荐。
*   **金币激励**：内置金币系统，提升用户活跃度。

## 技术栈

| 分类 | 技术栈 | 版本 | 说明 |
| :--- | :--- | :--- | :--- |
| **核心框架** | Java | 21 | 开发语言 |
| | Spring Boot | 3.5.6 | 应用框架 |
| | Spring Cloud | 2025.0.0 | 微服务框架 |
| | Spring Cloud Alibaba | 2025.0.0.0 | 阿里云微服务组件 |
| | Spring AI | 1.1.2 | AI集成框架 |
| **数据存储** | MySQL | 8.4.0 | 关系型数据库 |
| | MongoDB | 7.0+ | 文档数据库，存储非结构化数据 |
| | Milvus | 2.4+ | 向量数据库，支撑AI特征存储与检索 |
| | Redis | 6+ | 缓存数据库 |
| | MinIO | 8.6.0 | 对象存储服务 |
| | ShardingSphere | 5.5.2 | 分库分表解决方案 |
| **微服务组件** | Nacos | 2.3.0 | 服务注册与配置中心 |
| | Sentinel | 1.8.7 | 流量控制与熔断降级 |
| | Seata | 1.8.0 | 分布式事务解决方案 |
| | Dubbo | 3.3.0 | 高性能RPC框架 |
| **中间件** | RabbitMQ | 3.13+ | 消息中间件 |
| | XXL-Job | 3.3.2 | 分布式任务调度平台 |
| **应用组件** | MyBatis-Plus | 3.5.9 | ORM框架 |
| | Sa-Token | 1.44.0 | 权限认证框架 |
| | JetCache | 2.7.8 | 多级缓存框架 |
| | Dynamic-Tp | 1.2.2 | 动态线程池 |
| | MapStruct | 1.6.3 | 高性能对象映射 |

## 项目目录

```
kaleido-server/
├── kaleido-common/                    # 公共基础模块
│   ├── kaleido-aop/                   # AOP切面封装
│   ├── kaleido-api/                   # 通用API定义
│   ├── kaleido-base/                  # 基础工具类
│   ├── kaleido-cache/                 # 缓存封装
│   ├── kaleido-distribute/            # 分布式锁与协调
│   ├── kaleido-file/                  # 文件服务封装
│   ├── kaleido-lock/                  # 分布式锁实现
│   ├── kaleido-mq/                    # 消息队列封装
│   ├── kaleido-nacos/                 # Nacos配置封装
│   ├── kaleido-rpc/                   # RPC调用封装
│   ├── kaleido-sa-token/              # 认证授权封装
│   ├── kaleido-sentinel/              # 限流降级封装
│   └── kaleido-web/                   # Web通用配置
│
├── kaleido-gateway/                   # API网关服务
├── kaleido-auth/                      # 认证授权服务
├── kaleido-biz/                       # 核心业务服务集合
│   ├── kaleido-user/                  # 用户服务
│   ├── kaleido-wardrobe/              # 衣柜管理服务
│   ├── kaleido-ai/                    # AI智能服务
│   ├── kaleido-recommend/             # 推荐服务
│   ├── kaleido-tag/                   # 标签管理服务
│   ├── kaleido-coin/                  # 金币服务
│   └── kaleido-mcp/                   # MCP协议服务
├── kaleido-notice/                    # 通知服务
├── kaleido-admin/                     # 管理后台服务
├── doc/                               # 项目文档
│   ├── api/                           # API接口文档
│   ├── sql/                           # 数据库脚本
│   └── deploy/                        # 部署配置
├── scripts/                           # 运维脚本
├── .gitignore                         # Git忽略配置
├── pom.xml                            # 父POM文件
└── README.md                          # 项目说明文档
```

## 快速开始

### 环境要求

在开始之前，请确保您的开发或部署环境满足以下版本要求：

| 组件 | 版本要求 | 说明 |
| :--- | :--- | :--- |
| JDK | 21+ | 建议使用 OpenJDK 21 |
| Maven | 3.8+ | 项目构建工具 |
| MySQL | 8.0+ | 主数据库 |
| MongoDB | 7.0+ | 文档数据库 |
| Redis | 6+ | 缓存数据库 |
| RabbitMQ | 4.2+ | 消息队列 |
| Milvus | 2.4+ | 向量数据库 |
| XXL-Job | 3.0+ | 任务调度 |

### 部署步骤

1.  **克隆项目**
    ```bash
    git clone https://gitee.com/ou-yucheng/kaleido-ai.git
    cd kaleido-ai
    ```

2.  **启动中间件**
    请确保所有环境要求中的中间件服务已启动并正常运行。
    *   **Nacos**：服务注册与配置中心
    *   **MySQL**：主数据库 (需导入初始化脚本)
    *   **MongoDB**：文档数据库
    *   **Redis**：缓存数据库
    *   **RabbitMQ**：消息队列
    *   **MinIO**：对象存储
    *   **Milvus**：向量数据库
    *   **XXL-Job**：任务调度平台

3.  **初始化数据库**
    依次执行 `doc/sql` 目录下的数据库脚本，完成表结构和初始数据的创建。

4.  **导入配置**
    将 `doc/nacos` 目录中的配置文件导入到 Nacos 配置中心。

5.  **编译项目**
    在项目根目录执行 Maven 编译：
    ```bash
    mvn clean compile -DskipTests
    ```

6.  **启动服务**
    按顺序启动核心服务（也可根据需要只启动部分服务进行开发调试）：
    ```bash
    # 启动网关
    cd kaleido-gateway && mvn spring-boot:run
    
    # 启动认证
    cd kaleido-auth && mvn spring-boot:run
    
    # 启动业务服务 (示例：用户服务)
    cd kaleido-biz/kaleido-user && mvn spring-boot:run
    
    # ... 启动其他 biz 服务
    ```

### 验证部署

1.  **访问 Nacos 控制台**
    *   地址：`http://localhost:8848/nacos`
    *   默认账号/密码：`nacos` / `nacos`
    *   检查所有微服务实例是否已成功注册。

2.  **查看 API 文档**
    *   文档位于 `doc/api/{服务名}/index.html`。
    *   可使用浏览器直接打开对应服务的 API 文档进行接口测试。

## TODO

项目后续规划待办事项：

1.  集成 ELK (Elasticsearch, Logstash, Kibana) 实现可视化日志管理。
2.  集成 Grafana + Prometheus 建立完整的监控体系。
3.  集成 SkyWalking 实现分布式链路追踪。
4.  前端页面开发与集成。