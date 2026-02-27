# Kaleido-AI整洁架构实践详解

整洁架构（Clean Architecture）由Robert C. Martin提出，核心目标是**实现关注点分离**，让系统更易理解、测试和维护。

## 核心原则
- **依赖规则**：依赖关系从外层指向内层
- **独立于框架**：业务逻辑不依赖外部框架
- **独立于UI/数据库/外部服务**：业务逻辑保持纯净

## 为什么选择整洁架构？
传统分层架构（如MVC）随着业务复杂度增加会导致：
- 业务逻辑分散在各个层次
- 技术耦合严重
- 测试困难
- 难以演进

整洁架构通过**依赖倒置**和**清晰边界**解决这些问题。

## 架构分层设计
```
kaleido-user/
├── trigger/          # 接口适配器层
├── application/      # 用例层
├── domain/           # 领域层（核心）
└── infrastructure/   # 基础设施层
```

### 依赖关系
1. **domain层是核心**：不依赖任何其他层
2. **application层依赖domain层**
3. **infrastructure层依赖domain层**
4. **trigger层依赖application层**

## 各层核心职责

### 1. 领域层（Domain Layer）
**业务核心，技术无关**
- 包含所有业务规则和逻辑
- 不依赖数据库、框架、外部服务
- 易于单独测试

```java
// 示例：用户实体
public class User {
    public void changePassword(String oldPassword, String newPassword) {
        if (!passwordEncoder.matches(oldPassword, this.password)) {
            throw new UserException("旧密码错误");
        }
        validatePasswordStrength(newPassword);
        this.password = passwordEncoder.encode(newPassword);
    }
}
```

### 2. 应用层（Application Layer）
**用例协调者**
- 编排领域对象完成业务用例
- 管理事务边界
- 发布领域事件
- 不包含业务规则，只做流程控制

### 3. 基础设施层（Infrastructure Layer）
**技术实现提供者**
- 实现domain层定义的接口
- 处理数据持久化、外部服务集成
- 封装技术细节（数据库、缓存、消息队列等）

### 4. 接口适配器层（Trigger Layer）
**外部世界桥梁**
- 适配不同通信协议（HTTP/RPC/消息队列）
- 数据格式转换
- 统一异常处理和响应格式

## 实践价值

### 对开发者的价值
- **易于理解**：代码结构清晰，职责明确
- **易于测试**：领域层可独立测试，Mock简单
- **易于维护**：业务逻辑与技术实现分离

### 对业务的价值
- **快速响应变化**：业务规则变化只需修改domain层
- **降低复杂度**：清晰边界分解复杂系统
- **提高质量**：代码质量高，可测试性强

### 对团队的价值
- **明确分工**：领域专家、应用开发、基础设施开发各司其职
- **降低沟通成本**：统一语言，清晰边界

## 关键实践

### 1. 依赖倒置
```java
// domain层定义接口
public interface UserRepository {
    void save(User user);
    User findById(String userId);
}

// infrastructure层实现
@Repository
public class UserRepositoryImpl implements UserRepository {
    // 具体实现...
}

// application层依赖接口
@Service
public class UserCommandService {
    private final UserRepository userRepository; // 依赖接口
}
```

### 2. 测试策略
- **领域层单元测试**：不依赖外部环境
- **应用层集成测试**：Mock基础设施
- **全面覆盖**：针对每个业务规则编写测试

### 3. 异常处理
- **定义领域异常**：统一异常类型
- **统一异常处理**：trigger层统一转换异常响应

## 实践经验

### 1. 务实态度
不追求"完全"整洁架构，而是"尽量整洁"，在理论完美和实际可行间找到平衡。

### 2. 渐进式演进
- 从核心领域开始
- 按模块逐步重构
- 持续改进优化

### 3. 核心原则
- **简单优于复杂**
- **实用优于完美**
- **演进优于颠覆**