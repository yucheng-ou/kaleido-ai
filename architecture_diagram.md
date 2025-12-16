# Kaleido 微服务架构图

```mermaid
graph TB
    subgraph "客户端"
        Client[Web/移动端]
    end

    subgraph "API网关"
        Gateway[kaleido-gateway<br/>端口:9010]
    end

    subgraph "业务服务"
        Auth[kaleido-auth<br/>认证服务]
        User[kaleido-user<br/>用户服务]
    end

    subgraph "通用模块"
        Base[kaleido-base<br/>基础模型]
        DS[kaleido-ds<br/>数据源]
        Redis[kaleido-redis<br/>缓存]
        Nacos[kaleido-nacos<br/>配置中心]
        RPC[kaleido-rpc<br/>Dubbo RPC]
        Distribute[kaleido-distribute<br/>分布式ID]
        Web[kaleido-web<br/>Web配置]
        API[kaleido-api<br/>API接口定义]
    end

    subgraph "基础设施"
        MySQL[(MySQL数据库)]
        RedisStore[(Redis缓存)]
        NacosServer[(Nacos服务器)]
    end

    Client --> Gateway
    Gateway --> Auth
    Gateway --> User

    Auth --> Nacos
    User --> Nacos
    Auth --> Redis
    User --> Redis
    Auth --> DS
    User --> DS
    Auth --> RPC
    User --> RPC
    DS --> MySQL
    Redis --> RedisStore
    Nacos --> NacosServer

    subgraph "用户服务内部分层"
        Trigger[触发层<br/>Controller/Facade]
        Application[应用层<br/>Service/Converter]
        Domain[领域层<br/>Aggregate/Entity/Service]
        Infrastructure[基础设施层<br/>Repository/Mapper]
    end

    User --> Trigger
    Trigger --> Application
    Application --> Domain
    Domain --> Infrastructure
    Infrastructure --> DS
    Infrastructure --> Redis
```

## 分层说明

1. **客户端**：通过API网关访问服务。
2. **API网关**：基于Spring Cloud Gateway，负责路由、负载均衡和跨域。
3. **业务服务**：独立的微服务（auth, user等），每个服务包含完整的分层。
4. **通用模块**：可复用的组件，通过依赖注入提供能力。
5. **基础设施**：外部依赖（数据库、缓存、配置中心）。

## 数据流

- 外部请求 → 网关 → 业务服务 → 领域层 → 基础设施层 → 数据库/缓存。
- 服务间通信通过Dubbo RPC（基于Nacos服务发现）。
- 配置和发现通过Nacos集中管理。
- 缓存使用Redis + JetCache（本地缓存Caffeine +远程缓存Redisson）。
- 分布式ID使用雪花算法（基于Hutool + 自定义WorkerId）。