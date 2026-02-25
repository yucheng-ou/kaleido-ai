# Kaleido-AI代码分层实战：从入门到应用

> 在开发中，我们常遇到Service层越来越臃肿、代码难以维护的问题。Kaleido-AI的分层架构提供了一种清晰的解决方案，下面分享一些实践心得。

## 引言：代码分层能解决什么问题？

在传统Java Web开发中，MVC架构（Controller、Service、DAO三层）简单直观，但随着业务复杂度增长，一些常见问题会出现：

- **Service层臃肿**：一个Service类可能包含几十个方法，混合了业务验证、数据库操作、缓存处理等各种逻辑
- **代码维护困难**：业务逻辑分散，新人上手需要较长时间
- **测试成本高**：依赖外部环境，单元测试编写复杂

当Service类代码量较大时，可以考虑采用分层架构来改善可维护性。

## 架构全景：四层架构设计

Kaleido-AI采用领域驱动设计（DDD）的分层架构，核心思想是：**让代码结构反映业务结构**。

### 四层架构概览

1. **Trigger层（接口层）** - 接收外部请求，如HTTP、RPC等
2. **Application层（应用层）** - 协调业务流程，调用领域服务
3. **Domain层（领域层）** - 封装核心业务逻辑和规则
4. **Infrastructure层（基础设施层）** - 提供技术实现，如数据库操作、缓存等

**特殊层**：**Types层** - 提供公共类型定义、异常处理等横切关注点

### 依赖关系原则

- **外层依赖内层**：Trigger → Application → Domain ← Infrastructure
- **Domain层是核心**：定义仓储接口，不依赖具体技术实现
- **Infrastructure层实现Domain接口**：仓储实现在Infrastructure层，依赖Domain层
- **技术实现可替换**：通过接口抽象，Infrastructure层可以更换技术栈

这种设计在多个大型项目中验证了其价值，能有效提升代码的可维护性。

---

## 第一层：Trigger层 - 系统的入口

### 核心职责

Trigger层负责与外部系统交互，主要职责包括：

- ✅ 接收外部请求（HTTP、RPC、消息等）
- ✅ 参数校验（确保输入合法性）
- ✅ 调用Application层服务
- ✅ 返回统一格式响应

### 代码示例：RPC服务

```java
// RpcUserServiceImpl.java - Trigger层实现
@DubboService(version = "1.0.0")
public class RpcUserServiceImpl implements IRpcUserService {

    private final UserCommandService userCommandService;

    public Result<String> register(RegisterUserCommand command) {
        // 参数校验
        // 调用Application层服务
        String userId = userCommandService.createUser(command);
        // 返回统一响应
        return Result.success(userId);
    }
}
```

### Trigger层目录结构

```
trigger/
├── controller/     # HTTP控制器
├── rpc/           # RPC服务实现
├── event/         # 事件监听器
└── job/           # 定时任务
```

### 实践要点

1. **保持Trigger层轻薄**：只做技术适配，不包含业务逻辑
2. **统一响应格式**：使用`Result<T>`包装响应
3. **技术框架可替换**：从Dubbo切换到gRPC只需修改Trigger层

**思考**：如果Controller层包含了业务逻辑，这可能意味着架构需要进一步优化。

---

## 第二层：Application层 - 业务流程协调

### 核心职责

Application层负责协调完整的业务流程：

- ✅ 业务流程编排（调用多个领域服务）
- ✅ 事务管理（确保数据一致性）
- ✅ 协调领域服务和基础设施
- ✅ 发布领域事件

### 代码示例：用户注册服务

```java
// UserCommandService.java - Application层服务
@Service
@Transactional
public class UserCommandService {

    private final UserRepository userRepository;      // Domain层定义的仓储接口
    private final IUserDomainService userDomainService;

    public String createUser(RegisterUserCommand command) {
        // 1. 调用领域服务创建用户
        UserAggregate userAggregate = userDomainService.createUser(
                command.getTelephone(),
                command.getInviterCode()
        );

        // 2. 保存到数据库（通过Infrastructure层实现的仓储）
        userRepository.save(userAggregate);

        // 3. 发布注册事件
        eventPublisher.publish(new UserRegisteredEvent(userAggregate.getId()));

        return userAggregate.getId();
    }
}
```

### Application层目录结构

```
application/
├── command/     # 命令服务（处理CUD操作）
├── query/       # 查询服务（处理R操作）
└── convertor/   # 转换器（DTO↔领域对象）
```

### CQRS设计模式

命令查询职责分离（CQRS）带来几个好处：

1. **性能优化**：读写分离，针对性优化
2. **职责清晰**：读写逻辑分离，代码更易维护
3. **扩展灵活**：可独立扩展读服务或写服务

### 实践经验

1. **事务边界管理**：Application层定义事务边界
2. **流程编排**：复杂业务流程拆分为多个原子领域服务调用
3. **事件驱动**：通过领域事件实现系统解耦

**常见情况**：避免在Application层编写业务逻辑，这是Domain层的职责。

---

## 第三层：Domain层 - 业务规则核心

### Domain层价值

Domain层是系统的核心，封装所有业务规则和逻辑，不依赖任何技术实现。

### 1. 实体（Entity） - 充血模型

实体是有唯一标识的业务对象：

```java
// User.java - 用户实体
public class User extends BaseEntity {
    private String mobile;
    private String nickName;
    private UserStatus status;

    // 修改昵称
    public void changeNickName(String newNickName) {
        if (!status.canModify()) {
            throw new UserException("用户状态不允许修改昵称");
        }
        this.nickName = newNickName;
    }
}
```

### 2. 聚合根（Aggregate Root） - 业务一致性

聚合根确保聚合内的业务规则一致性：

```java
// UserAggregate.java - 用户聚合根
public class UserAggregate {
    private User user;                      // 用户实体
    private List<UserOperateStream> logs;  // 操作流水

    // 修改昵称
    public void changeNickName(String newNickName) {
        user.changeNickName(newNickName);
        logs.add(UserOperateStream.create(
                user.getId(),
                "修改昵称",
                "新昵称：" + newNickName
        ));
    }
}
```

### 3. 领域服务（Domain Service）

处理不适合放在单个聚合内的业务逻辑：

```java
// IUserDomainService.java - 用户领域服务接口
public interface IUserDomainService {
    UserAggregate createUser(String telephone, String inviterCode);
}
```

### Domain层目录结构

```
domain/
├── model/           # 领域模型
│   ├── aggregate/   # 聚合根
│   ├── entity/      # 实体
│   └── valobj/      # 值对象
├── service/         # 领域服务
├── adapter/         # 适配器接口
├── event/           # 领域事件
└── constant/        # 领域常量
```

### 实践思考

1. **领域模型纯度**：Domain层应该"纯净"，不依赖外部技术
2. **测试友好**：纯业务逻辑易于单元测试
3. **核心价值集中**：所有业务规则集中管理

**注意**：如果Domain层出现了数据库操作或`@Autowired`，说明架构可能需要调整。

---

## 第四层：Infrastructure层 - 技术实现

### 核心职责

Infrastructure层实现Domain层定义的接口，处理技术细节：

- ✅ 数据持久化（数据库操作）
- ✅ 缓存处理
- ✅ 消息队列
- ✅ 外部服务调用

### 代码示例：用户仓储实现

```java
// UserRepositoryImpl.java - 用户仓储实现
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserDao userDao;

    @Override
    @Transactional
    public void save(UserAggregate userAggregate) {
        // 保存用户实体
        User user = userAggregate.getUser();
        UserPO userPO = UserConvertor.toPO(user);
        userDao.insert(userPO);
    }

    @Override
    @Cached(name = "user", key = "#id", expire = 60)
    public UserAggregate findById(String id) {
        // 查询数据库并转换为领域对象
        UserPO userPO = userDao.findById(id);
        User user = UserConvertor.toEntity(userPO);
        return UserAggregate.create(user);
    }
}
```

### Infrastructure层目录结构

```
infrastructure/
├── adapter/      # 适配器实现
├── dao/          # 数据访问对象
├── component/    # 组件配置
└── config/       # 配置类
```

### 性能考虑

1. **缓存策略**：可以考虑本地缓存+分布式缓存的多级架构
2. **数据库优化**：读写分离、索引优化等
3. **异步处理**：非核心业务异步化，提升吞吐量

---

## Types层：公共类型定义

### Types层职责

Types层为所有层次提供公共的类型定义：

- ✅ 异常定义：统一错误码和异常处理
- ✅ 常量定义：业务和技术常量
- ✅ 配置类：系统配置
- ✅ 工具类：公共工具方法

### 统一异常处理示例

```java
// UserErrorCode.java - 错误码枚举
public enum UserErrorCode implements ErrorCode {
    DUPLICATE_TELEPHONE("DUPLICATE_TELEPHONE", "重复的电话号码"),
    USER_NOT_EXIST("USER_NOT_EXIST", "用户不存在");

    // 使用方式
    throw UserException.of(UserErrorCode.USER_NOT_EXIST);
}
```

### Types层价值

1. **标准化**：统一异常、常量管理
2. **可维护性**：集中管理，便于维护
3. **可观测性**：统一日志、监控

---

## 分层架构的实践价值

### 对开发团队的价值

1. **新人快速上手**：清晰的分层结构，便于理解代码
2. **代码质量提升**：分层职责明确，代码评审有依据
3. **技术债务可控**：架构约束防止代码腐化

### 对业务的价值

1. **快速响应变化**：业务规则集中管理，需求变更影响可控
2. **系统稳定性**：清晰的边界和依赖，故障隔离和恢复更快
3. **技术演进平滑**：基础设施层可替换，技术升级风险低

### 实践建议

1. **渐进式演进**：可以从MVC逐步过渡到分层架构
2. **团队共识**：分层架构需要团队共同理解和遵守规范
3. **工具支持**：代码生成、规范检查等工具可以提高效率

---

## 总结

分层架构的核心思想：

1. **Domain层是核心**：保护业务逻辑的纯粹性，定义仓储接口
2. **依赖方向正确**：外层依赖内层，Domain层独立，Infrastructure层实现Domain接口
3. **仓储依赖领域层**：仓储实现在Infrastructure层，依赖Domain层定义的接口
4. **每层有明确价值**：各层都有清晰的职责和边界
