# Kaleido-AI教程（八）DDD与整洁架构开发流程实践

> 本文以用户注册功能为例，展示如何基于DDD（领域驱动设计）与整洁架构进行开发。通过`RpcUserServiceImpl#register`方法的完整调用链路，讲解从接口层到领域层的开发流程。

## 引言：从理论到实践

在前面的文章中，我们分别介绍了DDD架构设计、代码分层结构、CQRS实践和整洁架构。这些理论概念如何在实际项目中落地？本文将通过一个具体的示例——用户注册功能，展示基于DDD与整洁架构的开发流程。

在Kaleido-AI项目中，用户注册功能通过`com.xiaoo.kaleido.user.trigger.rpc.RpcUserServiceImpl#register`方法实现。这个看似简单的方法背后，体现了DDD的分层思想和整洁架构的依赖规则。

## 一、功能概览：用户注册的完整流程

用户注册功能的完整调用链路如下：

```
RpcUserServiceImpl.register() → UserCommandService.createUser() → IUserDomainService.createUser() → UserAggregate.create() → UserRepository.save()
```

这个流程体现了整洁架构的四层结构：
1. **Trigger层（接口适配器层）**：`RpcUserServiceImpl`
2. **Application层（应用层）**：`UserCommandService`
3. **Domain层（领域层）**：`IUserDomainService`和`UserAggregate`
4. **Infrastructure层（基础设施层）**：`UserRepositoryImpl`

## 二、架构层次详解：从内到外的四层设计

基于DDD与整洁架构的开发应该采用**从内到外**的顺序。最稳定的领域层在最内层，最不稳定的接口适配层在最外层。

### 2.1 第一层：Domain层（领域层） - 最稳定

Domain层是系统的核心，封装所有业务规则和逻辑，不依赖任何技术实现。

**2.1.1 实体：User（充血模型）**

```java
public class User extends BaseEntity {
    private String mobile;      // 手机号
    private String nickName;    // 昵称
    private UserStatus status;  // 状态
    
    // 业务方法：修改昵称
    public void changeNickName(String newNickName) {
        if (!status.canModify()) {
            throw new UserException("用户状态不允许修改昵称");
        }
        this.nickName = newNickName;
    }
    
    // 业务方法：冻结用户
    public void freeze() {
        if (this.status == UserStatus.DELETED) {
            throw new UserException("用户已被删除，不能冻结");
        }
        this.status = UserStatus.FROZEN;
    }
}
```

**2.1.2 聚合根：UserAggregate**

```java
@Data
@Builder
public class UserAggregate {
    private User user;                      // 用户实体
    private List<UserOperateStream> operateStreams; // 操作流水

    public static UserAggregate create(String telephone, String nickName, 
            String inviteCode, String inviterId) {
        // 1.创建用户实体
        User user = User.create(telephone, nickName, inviteCode, inviterId);
        
        // 2.构建聚合根
        UserAggregate userAggregate = UserAggregate.builder()
                .user(user)
                .build();
        
        // 3.记录创建操作流水
        userAggregate.addOperateStream(UserOperateType.CREATE,
                String.format("用户创建，手机号：%s", telephone));
        
        return userAggregate;
    }
}
```

**2.1.3 领域服务接口：IUserDomainService**

```java
public interface IUserDomainService {
    UserAggregate createUser(String telephone, String inviteCode);
}
```

**2.1.4 仓储接口：UserRepository（依赖倒置）**

```java
// Domain层定义接口，实现依赖倒置
public interface UserRepository {
    void save(UserAggregate userAggregate);
    UserAggregate findById(String userId);
}
```

**Domain层开发要点**：
1. 先设计领域模型（实体、值对象、聚合根）
2. 封装业务规则在领域对象内部（充血模型）
3. 定义领域服务接口和仓储接口
4. 保持技术无关性，不依赖外部框架

### 2.2 第二层：Application层（应用层）

Application层依赖Domain层，负责业务流程编排。

**代码实现：UserCommandService**

```java
@Service
public class UserCommandService {

    // 依赖Domain层定义的接口
    private final UserRepository userRepository;
    private final IUserDomainService userDomainService;

    public String createUser(RegisterUserCommand command) {
        // 1.调用领域服务创建用户聚合根
        UserAggregate userAggregate = userDomainService.createUser(
                command.getTelephone(),
                command.getInviterCode()
        );

        // 2.通过仓储接口保存用户
        userRepository.save(userAggregate);

        // 3.发布领域事件
        eventPublisher.publish(new UserRegisteredEvent(userAggregate.getId()));

        return userAggregate.getId();
    }
}
```

**Application层开发要点**：
1. 依赖Domain层定义的接口，不依赖具体实现
2. 只做流程编排，不包含核心业务逻辑
3. 管理事务边界和领域事件
4. 每个方法对应一个完整的业务用例

### 2.3 第三层：Infrastructure层（基础设施层）

Infrastructure层实现Domain层定义的接口，处理技术细节。

**代码实现：UserRepositoryImpl**

```java
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserDao userDao;

    @Override
    @Transactional
    public void save(UserAggregate userAggregate) {
        // 1.保存用户实体
        User user = userAggregate.getUser();
        UserPO userPO = UserConvertor.toPO(user);
        userDao.insert(userPO);
        
        // 2.保存操作流水
        List<UserOperateStream> streams = userAggregate.getAndClearOperateStreams();
        if (!streams.isEmpty()) {
            List<UserOperateStreamPO> streamPOs = streams.stream()
                    .map(UserOperateStreamConvertor::toPO)
                    .collect(Collectors.toList());
            userOperateStreamDao.batchInsert(streamPOs);
        }
    }
}
```

**Infrastructure层开发要点**：
1. 实现Domain层定义的仓储接口
2. 处理数据持久化、缓存、消息队列等技术细节
3. 使用转换器进行领域对象和持久化对象的转换
4. 技术实现可替换，支持技术栈演进

### 2.4 第四层：Trigger层（接口适配器层） - 最不稳定

Trigger层是系统与外部世界的桥梁，负责接收外部请求。

**代码实现：RpcUserServiceImpl**

```java
@DubboService(version = RpcConstants.DUBBO_VERSION)
public class RpcUserServiceImpl implements IRpcUserService {

    private final UserCommandService userCommandService;

    @Override
    public Result<String> register(@Valid RegisterUserCommand command) {
        // 调用Application层服务
        String data = userCommandService.createUser(command);
        // 返回统一响应
        return Result.success(data);
    }
}
```

**Trigger层开发要点**：
1. 保持轻薄，不包含业务逻辑
2. 参数校验和协议适配
3. 调用Application层服务
4. 技术框架可替换（如从Dubbo切换到gRPC）

**从内到外的依赖关系**：
```
Domain层（最稳定）
    ↑
Application层（依赖Domain接口）
    ↑
Infrastructure层（实现Domain接口）
    ↑
Trigger层（最不稳定，依赖Application层）
```

## 三、DDD与整洁架构的结合点

### 3.1 依赖倒置原则

整洁架构的核心是依赖倒置原则：**内层不依赖外层，外层依赖内层定义的接口**。

在用户注册功能中：
- Domain层定义`UserRepository`接口
- Infrastructure层实现`UserRepositoryImpl`
- Application层依赖`UserRepository`接口

```java
// Domain层定义接口
public interface UserRepository {
    void save(UserAggregate userAggregate);
    UserAggregate findById(String userId);
}

// Infrastructure层实现接口
@Repository
public class UserRepositoryImpl implements UserRepository {
    // 具体实现...
}

// Application层依赖接口
@Service
public class UserCommandService {
    private final UserRepository userRepository; // 依赖接口，不依赖实现
}
```

### 3.2 业务逻辑与技术实现分离

DDD与整洁架构都强调业务逻辑与技术实现的分离：

| 关注点 | 所在层次 | 示例 |
|--------|----------|------|
| **业务规则** | Domain层 | 用户状态验证、昵称修改规则 |
| **业务流程** | Application层 | 用户注册的完整流程 |
| **技术实现** | Infrastructure层 | 数据库操作、缓存处理 |
| **外部适配** | Trigger层 | RPC协议适配、参数校验 |

### 3.3 领域模型的纯洁性

保持Domain层的纯洁性是DDD与整洁架构的共同要求：

**Domain层应该**：
- ✅ 包含业务规则和逻辑
- ✅ 定义仓储接口
- ✅ 使用领域语言（Ubiquitous Language）

**Domain层不应该**：
- ❌ 依赖数据库框架（如MyBatis、JPA）
- ❌ 依赖Web框架（如Spring MVC）
- ❌ 依赖外部服务（如Redis、MQ）

## 四、开发流程总结

### 4.1 从内到外的开发顺序

基于DDD与整洁架构的开发应该采用**从内到外**的顺序，先写稳定的内部（领域层），再写不稳定的外部。这种顺序体现了整洁架构的核心思想：**内部稳定，外部不稳定**。

**为什么从内到外？**
- **领域层最稳定**：业务规则变化最慢，是系统的核心
- **技术实现易变**：数据库、框架、协议等技术选型可能变化
- **外部接口最不稳定**：API契约、通信协议等经常调整

**具体开发步骤：**

1. **第一步：实现领域层（最稳定）**
   - 设计领域模型：实体、值对象、聚合根
   - 封装业务规则：将业务逻辑封装在领域对象内部
   - **定义仓储接口**：在Domain层定义`UserRepository`等接口，实现依赖倒置
   - 保持技术无关性：不依赖任何外部框架或技术

2. **第二步：设计应用层**
   - 编排业务流程：调用领域服务完成业务用例
   - 管理事务边界：确保数据一致性
   - **依赖领域接口**：通过Domain层定义的接口访问领域对象
   - 发布领域事件：实现系统解耦

3. **第三步：实现基础设施层**
   - **实现仓储接口**：在Infrastructure层实现Domain层定义的接口
   - 处理技术细节：数据库操作、缓存、消息队列等
   - 封装外部服务：隔离第三方系统的影响
   - 技术实现可替换：支持技术栈的演进

4. **第四步：定义接口适配层（最不稳定）**
   - 适配外部协议：HTTP、RPC、消息等
   - 参数校验和转换：将外部请求转换为内部命令
   - 调用应用层服务：保持接口层轻薄
   - 返回统一响应：处理异常和响应格式

**依赖倒置的关键**：
- Domain层定义`UserRepository`接口
- Infrastructure层实现`UserRepositoryImpl`
- Application层依赖`UserRepository`接口（不依赖实现）
- 这样Domain层保持纯洁，不依赖具体技术实现

### 4.2 各层职责的清晰划分

| 层次 | 核心问题 | 关注点 | 变化频率 |
|------|----------|--------|----------|
| **Trigger层** | "怎么接收请求" | 协议适配、参数校验 | 较高 |
| **Application层** | "业务流程是什么" | 流程编排、事务管理 | 中等 |
| **Domain层** | "业务规则是什么" | 业务规则、领域模型 | 较低 |
| **Infrastructure层** | "技术怎么实现" | 技术选型、性能优化 | 较高 |

### 4.3 测试策略的配合

不同层次采用不同的测试策略：

1. **Domain层单元测试**
   - 不依赖外部环境
   - 测试业务规则的正确性
   - 快速执行，反馈及时

2. **Application层集成测试**
   - Mock基础设施
   - 测试业务流程的完整性
   - 验证事务和事件发布

3. **端到端测试**
   - 验证完整调用链路
   - 测试系统集成点
   - 确保外部契约稳定

## 五、实践价值与思考

### 5.1 对开发团队的价值

1. **代码结构清晰**：分层架构让代码组织更有条理
2. **职责划分明确**：各层有明确的职责边界
3. **团队协作高效**：不同团队可以并行开发不同层次
4. **新人上手快速**：清晰的架构降低学习成本

### 5.2 对业务需求的价值

1. **快速响应变化**：业务规则变化只需修改Domain层
2. **降低修改风险**：清晰的边界减少意外影响
3. **保证业务一致性**：聚合根确保业务规则的一致性

### 5.3 务实的态度

在实践中，采取务实的态度：

1. **不追求完美**：在理论完美和实际可行间找到平衡
2. **渐进式演进**：从核心领域开始，逐步重构
3. **持续改进**：根据实际情况调整架构设计

## 结语

通过用户注册功能的示例，展示了基于DDD与整洁架构的开发流程。从Trigger层接收请求，到Application层编排流程，再到Domain层实现业务规则，最后通过Infrastructure层完成技术实现，这个完整的链路体现了分层架构的思想。

DDD与整洁架构不是银弹，而是帮助我们更好地组织代码、分离关注点的工具。在实际项目中，我们需要根据团队能力、业务复杂度和技术约束，找到适合自己的架构实践方式。