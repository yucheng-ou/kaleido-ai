# Kaleido Distributed Lock

基于 Redisson 的分布式锁实现，支持 SpEL 表达式和注解方式使用。

## 特性

- ✅ 基于 Redisson 的分布式锁实现
- ✅ 支持 SpEL 表达式动态生成锁键
- ✅ 注解方式使用，简单易用
- ✅ 支持多种锁类型：可重入锁、公平锁、读写锁等
- ✅ 自动配置，开箱即用
- ✅ 完善的异常处理

## 快速开始

### 1. 添加依赖

确保项目中已经包含 `kaleido-lock` 模块依赖。

### 2. 使用示例

```java
@Service
public class OrderService {
    
    /**
     * 使用可重入锁保护订单处理
     * 锁键使用 SpEL 表达式动态生成：'lock:order:' + orderId
     */
    @DistributedLock(key = "'lock:order:' + #orderId", leaseTime = 10)
    public void processOrder(Long orderId) {
        // 业务逻辑
    }
    
    /**
     * 使用公平锁更新用户余额
     * 设置等待时间5秒，持有时间30秒
     */
    @DistributedLock(key = "'lock:user:balance:' + #userId", 
                    lockType = LockType.FAIR, 
                    waitTime = 5, 
                    leaseTime = 30,
                    message = "用户余额更新中，请稍后再试")
    public void updateUserBalance(Long userId, Double amount) {
        // 业务逻辑
    }
    
    /**
     * 使用读写锁保护配置信息
     * 读锁：允许多个线程同时读取
     */
    @DistributedLock(key = "'lock:config:' + #configKey", 
                    lockType = LockType.READ,
                    leaseTime = 5)
    public String readConfig(String configKey) {
        // 业务逻辑
        return "config-value";
    }
    
    /**
     * 使用读写锁更新配置信息
     * 写锁：只允许一个线程写入
     */
    @DistributedLock(key = "'lock:config:' + #configKey", 
                    lockType = LockType.WRITE,
                    leaseTime = 10)
    public void updateConfig(String configKey, String configValue) {
        // 业务逻辑
    }
}
```

## 注解参数说明

### @DistributedLock

| 参数 | 类型 | 默认值 | 说明 |
|------|------|--------|------|
| `key` | `String` | **必填** | 锁的键，支持 SpEL 表达式 |
| `waitTime` | `long` | `0` | 锁的等待时间（秒），0 表示不等待 |
| `leaseTime` | `long` | `30` | 锁的持有时间（秒） |
| `lockType` | `LockType` | `REENTRANT` | 锁类型 |
| `message` | `String` | `"系统繁忙，请稍后再试"` | 获取锁失败时的提示信息 |

### LockType 枚举

| 枚举值 | 说明 |
|--------|------|
| `REENTRANT` | 可重入锁（默认） |
| `FAIR` | 公平锁 |
| `READ` | 读锁（读写锁的读锁） |
| `WRITE` | 写锁（读写锁的写锁） |
| `MULTI` | 联锁（需要多个键） |

## SpEL 表达式示例

SpEL 表达式可以引用方法参数，支持复杂的表达式：

```java
// 简单参数引用
@DistributedLock(key = "'lock:user:' + #userId")

// 多个参数组合
@DistributedLock(key = "'lock:transfer:' + #fromAccountId + ':' + #toAccountId")

// 调用对象方法
@DistributedLock(key = "'lock:order:' + #order.getId()")

// 条件表达式
@DistributedLock(key = "#type == 'VIP' ? 'lock:vip:' + #userId : 'lock:normal:' + #userId")
```

## 实现原理

### 架构设计

1. **注解层** (`@DistributedLock`): 定义分布式锁的参数
2. **切面层** (`DistributedLockAspect`): 拦截注解方法，解析 SpEL，调用锁服务
3. **服务层** (`DistributedLockService`): 封装 Redisson 的锁操作
4. **SpEL 解析器** (`LockSpelExpressionParser`): 解析方法参数和 SpEL 表达式
5. **自动配置** (`DistributedLockAutoConfiguration`): Spring Boot 自动配置

### 锁键生成

锁键的生成过程：
1. 从注解中获取 SpEL 表达式
2. 使用 `LockSpelExpressionParser` 解析表达式，生成动态键
3. 添加前缀 `kaleido:lock:` 形成完整锁键
4. 示例：`'lock:order:' + #orderId` → `kaleido:lock:order:123`

### 锁获取流程

1. 方法被调用时，切面拦截执行
2. 解析 SpEL 表达式生成锁键
3. 根据锁类型创建对应的 Redisson 锁对象
4. 尝试获取锁（支持等待时间）
5. 获取成功：执行原方法
6. 获取失败：抛出 `BizException` 异常
7. 方法执行完成后自动释放锁

## 配置说明

### 自动配置

模块通过 `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports` 自动注册以下组件：

- `DistributedLockService`: 分布式锁服务
- `DistributedLockAspect`: 分布式锁切面
- `LockSpelExpressionParser`: SpEL 表达式解析器

### 依赖要求

- Spring Boot 3.x
- Redisson (通过 `kaleido-cache` 模块提供)
- AOP 支持

## 注意事项

1. **锁的粒度**: 锁键的设计要合理，避免锁粒度过粗或过细
2. **锁超时**: 合理设置 `leaseTime`，避免业务执行时间过长导致锁自动释放
3. **异常处理**: 获取锁失败会抛出 `BizException`，需要适当处理
4. **读写锁**: 读写锁适用于读多写少的场景，读锁之间不互斥
5. **公平锁**: 公平锁保证按照请求顺序获取锁，但性能略低于非公平锁

## 与 kaleido-limiter 的关系

本模块参考了 `kaleido-limiter` 的设计模式，保持了相同的架构风格：

- 类似的注解设计
- 相同的 SpEL 表达式解析机制
- 一致的切面处理流程
- 相同的自动配置方式

这使得两个模块可以无缝集成，为系统提供完整的分布式并发控制解决方案。

## 版本历史

- **v1.0.0** (2026-01-27): 初始版本，基于 Redisson 实现分布式锁
