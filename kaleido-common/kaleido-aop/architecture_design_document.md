# Kaleido项目架构设计规范文档

## 文档概述

本文档基于对Kaleido项目的深入分析，详细阐述了基于DDD（领域驱动设计）+ CQRS（命令查询职责分离）+ 整洁架构的微服务架构设计规范。通过用户服务（kaleido-user）和字典服务（kaleido-admin）的实际代码分析，总结出一套完整的代码编写规范，确保新开发人员能够快速理解并编写出风格一致、质量可靠的代码。

## 一、项目整体架构

### 1.1 技术栈选型

| 技术组件 | 版本 | 用途说明 |
|---------|------|---------|
| **Spring Boot** | 3.5.6 | 核心应用框架 |
| **Spring Cloud** | 2025.0.0 | 微服务治理 |
| **Spring Cloud Alibaba** | 2025.0.0.0 | 阿里云微服务组件 |
| **MyBatis-Plus** | 3.5.9 | ORM框架，简化数据库操作 |
| **MySQL** | 8.4.0 | 关系型数据库 |
| **Druid** | 1.2.24 | 数据库连接池 |
| **Redisson** | 3.52.0 | Redis客户端，分布式锁 |
| **JetCache** | 2.7.8 | 多级缓存框架 |
| **Caffeine** | 3.2.3 | 本地缓存 |
| **Dubbo** | 3.3.0 | RPC框架，服务间调用 |
| **MapStruct** | 1.6.3 | 对象映射，替代手动转换代码 |
| **SpringDoc OpenAPI** | 2.7.0 | API文档生成（Swagger 3） |
| **PageHelper** | 1.4.7 | MyBatis分页插件 |
| **Java** | 21 | 编程语言 |

### 1.2 模块化设计

```
kaleido/ (父POM)
├── kaleido-common/                    # 公共模块
│   ├── kaleido-aop/                  # AOP切面（如空返回检查）
│   ├── kaleido-api/                  # API接口定义（Command/Query/Response）
│   ├── kaleido-base/                 # 基础类库（BaseEntity、Result等）
│   ├── kaleido-ds/                   # 数据源配置
│   ├── kaleido-redis/                # Redis配置
│   ├── kaleido-nacos/                # 服务发现配置
│   ├── kaleido-web/                  # Web相关配置
│   ├── kaleido-doc/                  # 文档相关
│   ├── kaleido-rpc/                  # RPC相关
│   ├── kaleido-distribute/           # 分布式工具（雪花算法等）
│   └── kaleido-sms/                  # 短信服务
├── kaleido-gateway/                  # API网关
├── kaleido-auth/                     # 认证授权服务
├── kaleido-biz/                      # 业务服务模块
│   └── kaleido-user/                 # 用户服务（本文主要分析对象）
├── kaleido-notice/                   # 通知服务
└── kaleido-admin/                    # 后台管理服务
```

### 1.3 架构特点

1. **微服务架构**：服务独立部署，通过Dubbo进行RPC调用
2. **DDD领域驱动**：以业务领域为核心进行建模
3. **CQRS模式**：命令（写）和查询（读）分离
4. **整洁架构**：依赖方向由外向内，核心业务不依赖外部框架
5. **前后端分离**：RESTful API + 统一响应格式

## 二、代码分层架构（四层架构）

### 2.1 分层结构图

```
┌─────────────────────────────────────────┐
│            Trigger Layer                │ ← 最外层：接收外部请求
│  (Controller/RPC/EventListener)         │   职责：参数校验、权限验证、结果包装
└─────────────────────────────────────────┘
                    ↓ 依赖
┌─────────────────────────────────────────┐
│          Application Layer              │ ← 应用层：业务流程编排
│    (CommandService/QueryService)        │   职责：事务管理、日志记录、对象转换
└─────────────────────────────────────────┘
                    ↓ 依赖
┌─────────────────────────────────────────┐
│            Domain Layer                 │ ← 领域层：业务核心
│ (Entity/ValueObject/Aggregate/DomainService)│ 职责：业务规则、领域模型、仓储接口
└─────────────────────────────────────────┘
                    ↓ 依赖
┌─────────────────────────────────────────┐
│        Infrastructure Layer             │ ← 基础设施层：技术实现
│ (RepositoryImpl/Dao/PO/Mapper/Convertor)│ 职责：数据持久化、外部服务调用
└─────────────────────────────────────────┘
```

### 2.2 各层详细职责

#### 2.2.1 Trigger Layer（触发层）
- **位置**：`com.xiaoo.kaleido.{module}.trigger.*`
- **包含组件**：
  - `controller/`：RESTful API控制器
  - `rpc/`：Dubbo RPC接口实现
  - `event/`：领域事件监听器
- **核心职责**：
  - 接收HTTP/RPC/事件请求
  - 参数校验（使用Jakarta Validation）
  - 权限验证（如登录态检查）
  - 调用应用层服务
  - 返回统一响应格式（`Result<T>`）
  - 生成API文档（SpringDoc注解）

#### 2.2.2 Application Layer（应用层）
- **位置**：`com.xiaoo.kaleido.{module}.application.*`
- **包含组件**：
  - `command/`：命令服务（写操作）
  - `query/`：查询服务（读操作）
  - `convertor/`：对象转换器（MapStruct）
- **核心职责**：
  - 业务流程编排
  - 事务管理（`@Transactional`）
  - 日志记录
  - 对象转换（领域对象 → DTO/Response）
  - 异常处理（捕获领域异常并记录）

#### 2.2.3 Domain Layer（领域层）
- **位置**：`com.xiaoo.kaleido.{module}.domain.*`
- **包含组件**：
  - `model/`：领域模型
    - `aggregate/`：聚合根
    - `entity/`：实体
    - `valobj/`：值对象
  - `service/`：领域服务
  - `adapter/`：适配器接口
    - `repository/`：仓储接口
    - `port/`：其他端口接口
  - `event/`：领域事件
  - `constant/`：领域常量
- **核心职责**：
  - 定义业务实体和值对象
  - 封装业务规则和行为
  - 定义仓储接口（面向接口编程）
  - 发布领域事件

#### 2.2.4 Infrastructure Layer（基础设施层）
- **位置**：`com.xiaoo.kaleido.{module}.infrastructure.*`
- **包含组件**：
  - `adapter/`：适配器实现
    - `repository/`：仓储实现
    - `port/`：端口实现
  - `dao/`：数据访问对象
    - `po/`：持久化对象
  - `mapper/`：MyBatis XML映射文件
- **核心职责**：
  - 实现领域层的仓储接口
  - 数据库访问（MyBatis-Plus + XML）
  - 对象转换（Entity ↔ PO）
  - 外部服务调用（如短信、邮件）

### 2.3 包结构示例（用户服务）

```
com.xiaoo.kaleido.user/
├── KaleidoUserApplication.java          # Spring Boot启动类
├── application/                         # 应用层
│   ├── command/                        # 命令服务
│   │   └── UserCommandService.java     # 用户命令服务
│   ├── query/                         # 查询服务
│   │   ├── UserQueryService.java      # 查询服务接口
│   │   └── impl/                      # 查询服务实现
│   │       └── UserQueryServiceImpl.java
│   └── convertor/                     # 应用层转换器
│       └── UserConvertor.java         # MapStruct转换器
├── domain/                             # 领域层
│   ├── adapter/                       # 适配器接口
│   │   ├── port/                     # 端口接口
│   │   │   └── DomainEventPublisher.java
│   │   └── repository/               # 仓储接口
│   │       └── UserRepository.java
│   ├── constant/                     # 领域常量
│   │   ├── UserStatus.java           # 用户状态枚举
│   │   └── UserOperateType.java      # 用户操作类型
│   ├── event/                       # 领域事件
│   │   ├── BaseDomainEvent.java     # 基础领域事件
│   │   ├── UserCreatedEvent.java    # 用户创建事件
│   │   └── UserStatusChangedEvent.java
│   ├── model/                      # 领域模型
│   │   ├── aggregate/             # 聚合根
│   │   │   └── UserAggregate.java
│   │   ├── entity/               # 实体
│   │   │   ├── User.java        # 用户实体
│   │   │   └── UserOperateStream.java
│   │   └── valobj/             # 值对象
│   │       ├── InvitationCode.java
│   │       └── UserInfoVO.java
│   └── service/                  # 领域服务
│       ├── UserDomainService.java      # 领域服务接口
│       └── impl/                      # 领域服务实现
│           └── UserDomainServiceImpl.java
├── infrastructure/                    # 基础设施层
│   ├── adapter/                     # 适配器实现
│   │   ├── port/                  # 端口实现
│   │   │   └── DomainEventPublisherImpl.java
│   │   └── repository/           # 仓储实现
│   │       └── UserRepositoryImpl.java
│   ├── dao/                       # DAO
│   │   ├── UserDao.java          # 用户DAO接口
│   │   ├── UserOperateStreamDao.java
│   │   └── po/                  # 持久化对象
│   │       ├── UserPO.java
│   │       └── UserOperateStreamPO.java
│   └── mapper/                  # MyBatis XML映射
│       ├── UserDao.xml
│       └── UserOperateStreamDao.xml
├── trigger/                         # 触发层
│   ├── controller/                # 控制器
│   │   └── UserController.java
│   ├── rpc/                     # RPC实现
│   │   └── IRpcUserServiceImpl.java
│   └── event/                  # 事件监听器
│       └── UserEventListener.java
└── types/                         # 类型定义
    ├── exception/               # 异常定义
    │   ├── UserErrorCode.java   # 错误码枚举
    │   └── UserException.java   # 用户异常
    └── constant/              # 常量定义（包信息）
```

## 三、数据对象转换规范

### 3.1 数据对象类型定义

| 对象类型 | 所在模块 | 命名规范 | 用途说明 | 示例 |
|---------|---------|---------|---------|------|
| **Command** | kaleido-api | XxxCommand | 写操作输入参数，包含业务校验注解 | `AddUserCommand` |
| **Query/Request** | kaleido-api | XxxQueryReq/XxxRequest | 读操作查询条件 | `UserPageQueryReq` |
| **Response** | kaleido-api | XxxResponse | 对外API响应，字段精简 | `UserInfoResponse` |
| **DTO** | 应用层 | XxxDTO | 应用层内部数据传输对象 | `DictDTO` |
| **Aggregate** | 领域层 | XxxAggregate | 领域聚合根，封装业务完整性 | `UserAggregate` |
| **Entity** | 领域层 | Xxx | 领域实体，包含业务行为 | `User` |
| **PO** | 基础设施层 | XxxPO | 持久化对象，对应数据库表结构 | `UserPO` |
| **VO** | 领域层 | XxxVO | 值对象，不可变 | `UserInfoVO` |

### 3.2 转换器使用规范（MapStruct）

#### 3.2.1 基础设施层转换器
```java
// 位置：infrastructure/adapter/repository/convertor/
// 职责：Entity ↔ PO 转换
@Mapper
public interface UserConvertor {
    // 使用静态实例，不依赖Spring
    UserConvertor INSTANCE = Mappers.getMapper(UserConvertor.class);
    
    // Entity → PO
    UserPO toPO(User user);
    
    // PO → Entity
    User toEntity(UserPO po);
}
```

#### 3.2.2 应用层转换器
```java
// 位置：application/convertor/
// 职责：Aggregate → Response/DTO 转换
@Mapper(componentModel = "spring")  // 注册为Spring Bean
public interface UserConvertor {
    
    // 简单字段自动映射
    UserInfoResponse toResponse(UserAggregate aggregate);
    
    // 复杂映射使用@Mapping注解
    @Mapping(source = "user.mobile", target = "telephone")
    @Mapping(source = "user.status.code", target = "status")
    @Mapping(source = "user.lastLoginTime", target = "lastLoginTime")
    UserInfoResponse toDetailedResponse(UserAggregate aggregate);
    
    // 默认方法处理特殊逻辑
    default UserInfoResponse toResponseWithMask(UserAggregate aggregate) {
        UserInfoResponse response = toResponse(aggregate);
        // 手机号脱敏
        if (response.getTelephone() != null) {
            response.setTelephone(maskTelephone(response.getTelephone()));
        }
        return response;
    }
    
    private String maskTelephone(String telephone) {
        return telephone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }
}
```

#### 3.2.3 转换原则
1. **禁止手动转换**：所有对象转换必须使用MapStruct
2. **明确映射关系**：使用`@Mapping`注解明确字段映射
3. **复杂逻辑在默认方法中处理**
4. **保持转换器单一职责**：一个转换器只处理一组相关对象的转换

### 3.3 各层数据流转规范

#### 3.3.1 写操作数据流（以用户注册为例）
```
HTTP Request (AddUserCommand)
    ↓ 参数校验 (@Valid)
UserController.register()
    ↓ 传递Command对象
UserCommandService.createUser(AddUserCommand command)
    ↓ 提取原始参数（不传递Command到领域层）
UserDomainService.createUser(String telephone, String password, String inviteCode)
    ↓ 创建聚合根（包含业务规则）
UserAggregate（包含User实体和操作流水）
    ↓ 调用仓储接口
UserRepository.save(UserAggregate aggregate)
    ↓ 内部转换：User → UserPO（MapStruct）
UserDao.insert(UserPO)
    ↓ 返回业务标识
String userId
```

#### 3.3.2 读操作数据流（以查询用户为例）
```
HTTP Request (GET /user/{id})
    ↓ 参数校验
UserController.getUserById(String userId)
    ↓ 调用查询服务
UserQueryService.findById(String userId)
    ↓ 调用仓储接口
UserRepository.findById(String id)
    ↓ 内部：UserPO → User → UserAggregate
UserAggregate
    ↓ 应用层转换：Aggregate → Response（MapStruct）
UserInfoResponse
    ↓ 统一结果包装
Result<UserInfoResponse>
```

### 3.4 关键设计原则

1. **数据隔离原则**：
   - API层对象不进入领域层
   - 领域对象不直接暴露给外层
   - 每层使用自己的数据对象

2. **依赖方向原则**：
   - 内层定义接口，外层实现
   - 领域层不依赖基础设施层
   - 应用层不依赖具体技术实现

3. **CQRS分离原则**：
   - 命令服务处理写操作，返回简单类型
   - 查询服务处理读操作，返回Response对象
   - 读写可以使用不同的数据模型

## 四、SQL查询规范

### 4.1 DAO层设计规范

#### 4.1.1 DAO接口定义
```java
// 继承MyBatis-Plus的BaseMapper获得基础CRUD能力
@Mapper
public interface UserDao extends BaseMapper<UserPO> {
    
    // 自定义查询方法使用@Param注解明确参数名
    UserPO findById(@Param("id") String id);
    
    UserPO findByTelephone(@Param("telephone") String telephone);
    
    // 存在性检查返回boolean
    boolean existsByTelephone(@Param("telephone") String telephone);
    
    // 复杂查询使用XML映射
    List<UserPO> selectByCondition(@Param("req") UserPageQueryReq req);
    
    // 禁止使用@Select、@Update等注解
    // ❌ @Select("SELECT * FROM user WHERE id = #{id}")
    // ❌ UserPO findById(@Param("id") String id);
}
```

#### 4.1.2 PO类定义
```java
// 继承BasePO获得公共字段
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user")  // 指定表名
public class UserPO extends BasePO {
    
    // 使用@TableField映射字段名（驼峰转下划线自动映射可省略）
    @TableField("mobile")
    private String mobile;
    
    @TableField("password_hash")
    private String passwordHash;
    
    @TableField("nick_name")
    private String nickName;
    
    // 枚举字段存储为字符串
    @TableField("status")
    private String status;
    
    // 不映射的字段使用exist = false
    @TableField(exist = false)
    private String temporaryField;
}
```

### 4.2 XML映射文件规范

#### 4.2.1 文件结构
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.xiaoo.kaleido.user.infrastructure.dao.UserDao">
    
    <!-- 1. ResultMap定义 -->
    <resultMap id="UserMap" type="com.xiaoo.kaleido.user.infrastructure.dao.po.UserPO">
        <!-- 继承BasePO的字段映射 -->
        <result property="id" column="id"/>
        <result property="createdAt" column="created_at"/>
        <result property="updatedAt" column="updated_at"/>
        <result property="deleted" column="deleted"/>
        <result property="lockVersion" column="lock_version"/>
        
        <!-- UserPO特有字段 -->
        <result property="mobile" column="mobile"/>
        <result property="passwordHash" column="password_hash"/>
        <result property="nickName" column="nick_name"/>
        <result property="status" column="status"/>
        <result property="inviteCode" column="invite_code"/>
        <result property="inviterId" column="inviter_id"/>
        <result property="lastLoginTime" column="last_login_time"/>
        <result property="avatar" column="avatar"/>
        <result property="gender" column="gender"/>
    </resultMap>
    
    <!-- 2. 可重用SQL片段 -->
    <sql id="UserColumns">
        id, mobile, password_hash, nick_name, status, invite_code, 
        inviter_id, last_login_time, avatar, gender, created_at, 
        updated_at, deleted, lock_version
    </sql>
    
    <!-- 3. 查询条件片段 -->
    <sql id="UserQueryConditions">
        <!-- 软删除条件（所有查询必须包含） -->
        AND deleted = 0
        
        <!-- 动态条件 -->
        <if test="req.nickName != null and req.nickName != ''">
            AND nick_name LIKE CONCAT('%', #{req.nickName}, '%')
        </if>
        <if test="req.telephone != null and req.telephone != ''">
            AND mobile LIKE CONCAT('%', #{req.telephone}, '%')
        </if>
        <if test="req.status != null">
            AND status = #{req.status}
        </if>
        <if test="req.gender != null">
            AND gender = #{req.gender}
        </if>
        <!-- 时间范围查询 -->
        <if test="req.startTime != null">
            AND created_at >= #{req.startTime}
        </if>
        <if test="req.endTime != null">
            AND created_at <= #{req.endTime}
        </if>
    </sql>
    
    <!-- 4. 查询语句 -->
    <select id="findById" resultMap="UserMap">
        SELECT <include refid="UserColumns"/>
        FROM user
        WHERE id = #{id} AND deleted = 0
    </select>
    
    <select id="selectByCondition" resultMap="UserMap">
        SELECT <include refid="UserColumns"/>
        FROM user
        WHERE 1=1
        <include refid="UserQueryConditions"/>
        ORDER BY created_at DESC
    </select>
    
    <!-- 5. 分页查询（配合PageHelper） -->
    <select id="pageQuery" resultMap="UserMap">
        SELECT <include refid="UserColumns"/>
        FROM user
        WHERE 1=1
        <include refid="UserQueryConditions"/>
        ORDER BY created_at DESC
    </select>
</mapper>
```

#### 4.2.2 XML映射最佳实践
1. **使用ResultMap**：明确所有字段映射，避免依赖自动映射
2. **SQL片段重用**：使用`<sql>`定义可重用的字段列表和条件
3. **软删除统一处理**：所有查询自动添加`deleted = 0`条件
4. **防SQL注入**：使用`#{}`占位符，不使用`${}`字符串拼接
5. **XML转义**：使用`<`代替`<`，`>`代替`>`
6. **分页处理**：使用PageHelper，不在XML中写`LIMIT`

### 4.3 MyBatis-Plus使用规范

#### 4.3.1 简单CRUD使用MyBatis-Plus
```java
// 插入
UserPO userPO = new UserPO();
userPO.setMobile("13800138000");
userPO.setNickName("张三");
userDao.insert(userPO);

// 更新
userPO.setNickName("李四");
userDao.updateById(userPO);

// 查询（简单条件）
List<UserPO> list = userDao.selectList(
    new LambdaQueryWrapper<UserPO>()
        .eq(UserPO::getStatus, UserStatus.ACTIVE)
        .like(UserPO::getNickName, "张")
        .ge(UserPO::getCreatedAt, startTime)
        .le(UserPO::getCreatedAt, endTime)
        .orderByDesc(UserPO::getCreatedAt)
);

// 分页查询（配合PageHelper）
PageHelper.startPage(pageNum, pageSize);
List<UserPO> userList = userDao.selectByCondition(req);
PageInfo<UserPO> pageInfo = new PageInfo<>(userList);
```

#### 4.3.2 复杂查询使用XML
```java
// 简单查询使用MyBatis-Plus Wrapper
// 复杂查询（多表关联、复杂条件、自定义SQL）使用XML映射

// ✅ 正确：复杂查询使用XML
List<UserPO> complexList = userDao.selectByComplexCondition(params);

// ❌ 错误：在Java代码中拼接复杂SQL
String sql = "SELECT * FROM user WHERE 1=1";
if (condition) {
    sql += " AND status = 'ACTIVE'";
}
// 这种写法难以维护且容易SQL注入
```

## 五、各层代码编写规范

### 5.1 Trigger Layer（控制器层）

#### 5.1.1 REST控制器模板
```java
@Slf4j
@Validated  // 启用参数校验
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor  // Lombok构造器注入
@Tag(name = "用户管理", description = "用户相关接口")  // Swagger分组
public class UserController {
    
    // 依赖注入：查询服务和命令服务
    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;
    
    /**
     * 根据ID查询用户信息
     * @param userId 用户ID
     * @return 用户信息
     */
    @Operation(summary = "查询用户信息", description = "根据用户ID查询用户详细信息")
    @GetMapping("/{userId}")
    public Result<UserInfoResponse> getUserById(
            @Parameter(description = "用户ID", required = true, example = "1234567890123456789")
            @NotBlank(message = "用户ID不能为空")
            @PathVariable String userId) {
        
        log.debug("查询用户信息，userId={}", userId);
        
        UserInfoResponse response = userQueryService.findById(userId);
        return Result.success(response);
    }
    
    /**
     * 用户注册
     * @param command 注册命令
     * @return 用户ID
     */
    @Operation(summary = "用户注册", description = "注册新用户")
    @PostMapping("/register")
    public Result<String> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "用户注册信息", required = true)
            @Valid @RequestBody AddUserCommand command) {
        
        log.info("用户注册，手机号: {}", command.getTelephone());
        
        String userId = userCommandService.createUser(command);
        return Result.success(userId);
    }
    
    /**
     * 分页查询用户
     * @param req 查询条件
     * @return 分页结果
     */
    @Operation(summary = "分页查询用户", description = "根据条件分页查询用户列表")
    @GetMapping("/page")
    public Result<PageResp<UserInfoResponse>> pageQuery(
            @Valid UserPageQueryReq req) {
        
        log.debug("分页查询用户，条件: {}", req);
        
        PageResp<UserInfoResponse> pageResult = userQueryService.pageQuery(req);
        return Result.success(pageResult);
    }
}
```

#### 5.1.2 控制器规范要点
1. **使用Lombok注解**：`@Slf4j`日志，`@RequiredArgsConstructor`依赖注入
2. **完整的Swagger注解**：`@Tag`、`@Operation`、`@Parameter`、`@RequestBody`
3. **参数校验**：`@Validated` + `@Valid` + Jakarta Validation注解
4. **统一返回格式**：所有接口返回`Result<T>`包装
5. **日志记录**：关键操作记录INFO日志，调试信息记录DEBUG日志
6. **不处理业务逻辑**：只做参数校验、权限验证、结果包装

### 5.2 Application Layer（应用层）

#### 5.2.1 命令服务模板
```java
@Slf4j
@Service
@RequiredArgsConstructor
public class UserCommandService {
    
    // 依赖领域层接口，不依赖具体实现
    private final UserRepository userRepository;
    private final UserDomainService userDomainService;
    
    /**
     * 创建用户
     * @param command 创建用户命令
     * @return 用户ID
     */
    @Transactional(rollbackFor = Exception.class)  // 声明式事务
    public String createUser(AddUserCommand command) {
        
        // 1. 参数预处理（如密码加密）
        String passwordHash = passwordEncoder.encode(command.getPassword());
        
        // 2. 调用领域服务执行业务逻辑
        UserAggregate userAggregate = userDomainService.createUser(
            command.getTelephone(),
            passwordHash,
            command.getInviterCode()
        );
        
        // 3. 调用仓储保存聚合根
        userRepository.save(userAggregate);
        
        // 4. 记录业务日志
        log.info("用户创建成功，用户ID: {}, 手机号: {}", 
                 userAggregate.getId(), command.getTelephone());
        
        // 5. 返回业务标识（不返回领域对象）
        return userAggregate.getId();
    }
    
    /**
     * 修改用户昵称
     * @param command 修改昵称命令
     */
    @Transactional(rollbackFor = Exception.class)
    public void changeNickName(ChangeNickNameCommand command) {
        
        // 1. 调用领域服务
        UserAggregate userAggregate = userDomainService.changeNickName(
            command.getUserId(),
            command.getNickName()
        );
        
        // 2. 保存变更
        userRepository.update(userAggregate);
        
        log.info("用户昵称修改成功，用户ID: {}, 新昵称: {}", 
                 command.getUserId(), command.getNickName());
    }
}
```

#### 5.2.2 查询服务模板
```java
@Slf4j
@Service
@RequiredArgsConstructor
public class UserQueryServiceImpl implements UserQueryService {
    
    // 依赖仓储接口
    private final UserRepository userRepository;
    
    // 依赖MapStruct转换器
    private final UserConvertor userConvertor;
    
    @Override
    public UserInfoResponse findById(String userId) {
        log.debug("根据ID查询用户，userId={}", userId);
        
        // 1. 调用仓储获取聚合根
        return userRepository.findById(userId)
                .map(userConvertor::toResponse)  // 2. 使用MapStruct转换
                .orElse(null);  // 3. 处理空值
    }
    
    @Override
    public PageResp<UserInfoResponse> pageQuery(UserPageQueryReq req) {
        log.debug("分页查询用户，条件: {}", req);
        
        // 1. 调用仓储分页查询
        PageResp<UserAggregate> pageResult = userRepository.pageQuery(req);
        
        // 2. 使用Stream + MapStruct转换列表
        List<UserInfoResponse> responseList = pageResult.getList().stream()
                .map(userConvertor::toResponse)
                .collect(Collectors.toList());
        
        // 3. 构建分页响应
        return PageResp.success(
            responseList,
            pageResult.getTotal(),
            pageResult.getPageNum(),
            pageResult.getPageSize()
        );
    }
    
    @Override
    public UserInfoResponse findByTelephone(String telephone) {
        log.debug("根据手机号查询用户，telephone={}", telephone);
        
        return userRepository.findByTelephone(telephone)
                .map(userConvertor::toResponse)
                .orElse(null);
    }
}
```

#### 5.2.3 应用层规范要点
1. **事务管理**：命令服务使用`@Transactional`，查询服务一般不使用
2. **依赖接口**：依赖领域层接口，不依赖具体实现
3. **使用MapStruct**：所有对象转换使用MapStruct，不手动编写转换代码
4. **日志记录**：关键操作记录INFO日志，查询记录DEBUG日志
5. **异常处理**：捕获领域异常，记录日志后重新抛出
6. **返回类型**：命令服务返回业务标识或void，查询服务返回Response或DTO

### 5.3 Domain Layer（领域层）

#### 5.3.1 实体模板
```java
/**
 * 用户实体
 * 包含用户的核心属性和业务行为
 */
@Data
@SuperBuilder  // 支持Builder模式继承
@EqualsAndHashCode(callSuper = true)  // 包含父类字段
@NoArgsConstructor  // MapStruct需要无参构造
@AllArgsConstructor  // 全参构造
public class User extends BaseEntity {
    
    /** 手机号（唯一） */
    private String mobile;
    
    /** 密码哈希值 */
    private String passwordHash;
    
    /** 昵称 */
    private String nickName;
    
    /** 用户状态 */
    private UserStatus status;
    
    /** 用户邀请码（唯一） */
    private String inviteCode;
    
    /** 邀请人ID */
    private String inviterId;
    
    /** 最后登录时间 */
    private Date lastLoginTime;
    
    /** 头像URL */
    private String avatar;
    
    /** 性别 */
    private UserGenderEnum gender;
    
    // ========== 业务行为方法 ==========
    
    /**
     * 创建用户实体（静态工厂方法）
     */
    public static User create(String telephone, String passwordHash,
                              String nickName, String inviteCode, String inviterId) {
        
        return User.builder()
                .id(SnowflakeUtil.newSnowflakeId())  // 使用雪花算法生成ID
                .mobile(telephone)
                .passwordHash(passwordHash)
                .nickName(nickName)
                .inviteCode(inviteCode)
                .inviterId(inviterId)
                .status(UserStatus.ACTIVE)  // 默认激活状态
                .build();
    }
    
    /**
     * 修改昵称
     * @param newNickName 新昵称
     * @throws UserException 如果用户状态不允许修改
     */
    public void changeNickName(String newNickName) {
        // 前置条件检查
        if (!status.canModify()) {
            throw UserException.of(UserErrorCode.USER_IS_FROZEN);
        }
        
        // 业务规则
        if (newNickName == null || newNickName.trim().isEmpty()) {
            throw UserException.of(UserErrorCode.NICKNAME_EMPTY);
        }
        
        if (newNickName.length() > 20) {
            throw UserException.of(UserErrorCode.NICKNAME_TOO_LONG);
        }
        
        // 执行业务操作
        this.nickName = newNickName;
    }
    
    /**
     * 冻结用户
     */
    public void freeze() {
        if (checkUserStatusIsActive()) {
            this.status = UserStatus.FROZEN;
        }
    }
    
    /**
     * 解冻用户
     */
    public void unfreeze() {
        if (checkUserStatusIsFrozen()) {
            this.status = UserStatus.ACTIVE;
        }
    }
    
    /**
     * 验证密码
     * @param passwordHash 待验证的密码哈希
     * @return 是否匹配
     */
    public boolean verifyPassword(String passwordHash) {
        return this.passwordHash.equals(passwordHash);
    }
    
    /**
     * 更新最后登录时间
     */
    public void updateLastLoginTime() {
        this.lastLoginTime = new Date();
    }
    
    // ========== 状态检查方法 ==========
    
    /**
     * 检查用户状态是否为活跃
     * @return 是否活跃
     * @throws UserException 如果用户被冻结或删除
     */
    private boolean checkUserStatusIsActive() {
        if (isFrozen()) {
            throw UserException.of(UserErrorCode.USER_IS_FROZEN);
        }
        if (isDeleted()) {
            throw UserException.of(UserErrorCode.USER_IS_DELETED);
        }
        return isActive();
    }
    
    /**
     * 检查用户状态是否为冻结
     */
    private boolean checkUserStatusIsFrozen() {
        if (isActive()) {
            throw UserException.of(UserErrorCode.USER_IS_ACTIVE);
        }
        if (isDeleted()) {
            throw UserException.of(UserErrorCode.USER_IS_DELETED);
        }
        return isFrozen();
    }
    
    public boolean isActive() {
        return status.isActive();
    }
    
    public boolean isFrozen() {
        return status.isFrozen();
    }
    
    public boolean isDeleted() {
        return status.isDeleted();
    }
    
    public boolean isOperable() {
        return status.isOperable();
    }
}
```

#### 5.3.2 聚合根模板
```java
/**
 * 用户聚合根
 * 封装用户相关的实体和值对象，维护业务完整性
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAggregate {
    
    /** 用户实体（聚合根的核心） */
    private User user;
    
    /** 用户操作流水列表 */
    @Builder.Default
    private List<UserOperateStream> operateStreams = new ArrayList<>();
    
    /**
     * 创建用户聚合根（静态工厂方法）
     */
    public static UserAggregate create(User user) {
        return UserAggregate.builder()
                .user(user)
                .operateStreams(new ArrayList<>())
                .build();
    }
    
    /**
     * 添加操作流水
     */
    public void addOperateStream(UserOperateStream stream) {
        this.operateStreams.add(stream);
    }
    
    /**
     * 添加操作流水（便捷方法）
     */
    public void addOperateStream(UserOperateType type, String remark) {
        UserOperateStream stream = UserOperateStream.create(
            this.user.getId(),
            type,
            remark
        );
        this.operateStreams.add(stream);
    }
    
    /**
     * 获取用户ID
     */
    public String getId() {
        return user != null ? user.getId() : null;
    }
}
```

#### 5.3.3 领域服务模板
```java
/**
 * 用户领域服务接口
 * 处理跨实体的业务逻辑
 */
public interface UserDomainService {
    
    /**
     * 创建用户
     * @param telephone 手机号
     * @param passwordHash 密码哈希
     * @param inviteCode 邀请码（可选）
     * @return 用户聚合根
     */
    UserAggregate createUser(String telephone, String passwordHash, String inviteCode);
    
    /**
     * 根据ID查找用户，不存在则抛出异常
     * @param userId 用户ID
     * @return 用户聚合根
     * @throws UserException 用户不存在
     */
    UserAggregate findByIdOrThrow(String userId);
    
    /**
     * 修改用户昵称
     * @param userId 用户ID
     * @param nickName 新昵称
     * @return 用户聚合根
     */
    UserAggregate changeNickName(String userId, String nickName);
    
    /**
     * 冻结用户
     * @param userId 用户ID
     * @return 用户聚合根
     */
    UserAggregate freezeUser(String userId);
    
    /**
     * 解冻用户
     * @param userId 用户ID
     * @return 用户聚合根
     */
    UserAggregate unfreezeUser(String userId);
    
    /**
     * 验证用户密码
     * @param userId 用户ID
     * @param passwordHash 密码哈希
     * @return 是否验证通过
     */
    boolean verifyPassword(String userId, String passwordHash);
    
    /**
     * 用户登录
     * @param userId 用户ID
     * @return 用户聚合根
     */
    UserAggregate login(String userId);
    
    /**
     * 用户登出
     * @param userId 用户ID
     * @return 用户聚合根
     */
    UserAggregate logout(String userId);
}
```

#### 5.3.4 仓储接口模板
```java
/**
 * 用户仓储接口
 * 定义用户聚合根的持久化操作
 */
public interface UserRepository {
    
    /**
     * 保存用户聚合根
     * @param userAggregate 用户聚合根
     */
    void save(UserAggregate userAggregate);
    
    /**
     * 更新用户聚合根
     * @param userAggregate 用户聚合根
     */
    void update(UserAggregate userAggregate);
    
    /**
     * 根据ID查找用户聚合根
     * @param id 用户ID
     * @return 用户聚合根Optional
     */
    Optional<UserAggregate> findById(String id);
    
    /**
     * 根据ID查找用户聚合根（包含操作流水）
     * @param id 用户ID
     * @return 用户聚合根Optional
     */
    Optional<UserAggregate> findUserAndStreamById(String id);
    
    /**
     * 根据ID查找用户聚合根，不存在则抛出异常
     * @param id 用户ID
     * @return 用户聚合根
     * @throws UserException 用户不存在
     */
    UserAggregate findByIdOrThrow(String id);
    
    /**
     * 根据手机号查找用户聚合根
     * @param telephone 手机号
     * @return 用户聚合根Optional
     */
    Optional<UserAggregate> findByTelephone(String telephone);
    
    /**
     * 根据邀请码查找用户聚合根
     * @param inviteCode 邀请码
     * @return 用户聚合根Optional
     */
    Optional<UserAggregate> findByInviteCode(String inviteCode);
    
    /**
     * 检查手机号是否存在
     * @param telephone 手机号
     * @return 是否存在
     */
    boolean existsByTelephone(String telephone);
    
    /**
     * 检查邀请码是否存在
     * @param inviteCode 邀请码
     * @return 是否存在
     */
    boolean existsByInviteCode(String inviteCode);
    
    /**
     * 分页查询用户聚合根
     * @param req 查询条件
     * @return 分页结果
     */
    PageResp<UserAggregate> pageQuery(UserPageQueryReq req);
}
```

#### 5.3.5 领域层规范要点
1. **富血模型**：实体包含业务行为方法，不只是数据容器
2. **聚合根设计**：聚合根维护业务完整性，封装内部实体
3. **工厂方法**：使用静态工厂方法创建复杂对象
4. **异常抛出**：业务规则违反时抛出领域异常
5. **接口定义**：仓储和领域服务定义接口，不依赖具体实现
6. **值对象不可变**：值对象创建后不可修改

### 5.4 Infrastructure Layer（基础设施层）

#### 5.4.1 仓储实现模板
```java
@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    
    // 依赖DAO接口
    private final UserDao userDao;
    private final UserOperateStreamDao userOperateStreamDao;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(UserAggregate userAggregate) {
        // 1. 转换：User → UserPO
        User user = userAggregate.getUser();
        UserPO userPO = UserConvertor.INSTANCE.toPO(user);
        
        // 2. 保存用户
        userDao.insert(userPO);
        
        // 3. 保存操作流水
        batchSaveOperateStream(userAggregate.getOperateStreams());
        
        log.debug("用户保存成功，用户ID: {}", user.getId());
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserAggregate userAggregate) {
        // 1. 转换：User → UserPO
        User user = userAggregate.getUser();
        UserPO userPO = UserConvertor.INSTANCE.toPO(user);
        
        // 2. 更新用户
        userDao.updateById(userPO);
        
        // 3. 保存操作流水
        batchSaveOperateStream(userAggregate.getOperateStreams());
        
        log.debug("用户更新成功，用户ID: {}", user.getId());
    }
    
    @Override
    public Optional<UserAggregate> findById(String id) {
        // 1. 查询PO
        UserPO userPO = userDao.findById(id);
        if (userPO == null) {
            return Optional.empty();
        }
        
        // 2. 转换：UserPO → User
        User user = UserConvertor.INSTANCE.toEntity(userPO);
        
        // 3. 创建聚合根（不加载操作流水，按需加载）
        UserAggregate aggregate = UserAggregate.create(user);
        return Optional.of(aggregate);
    }
    
    @Override
    public Optional<UserAggregate> findUserAndStreamById(String id) {
        // 1. 查询用户
        UserPO userPO = userDao.findById(id);
        if (userPO == null) {
            return Optional.empty();
        }
        
        // 2. 转换：UserPO → User
        User user = UserConvertor.INSTANCE.toEntity(userPO);
        
        // 3. 查询操作流水（最近100条）
        List<UserOperateStreamPO> streamPOs = 
            userOperateStreamDao.findByIdWithLimit(userPO.getId(), 100);
        List<UserOperateStream> streams = streamPOs.stream()
                .map(UserOperateStreamConvertor.INSTANCE::toEntity)
                .toList();
        
        // 4. 创建聚合根并添加操作流水
        UserAggregate aggregate = UserAggregate.create(user);
        aggregate.getOperateStreams().addAll(streams);
        return Optional.of(aggregate);
    }
    
    @Override
    public PageResp<UserAggregate> pageQuery(UserPageQueryReq req) {
        // 1. 使用PageHelper分页
        PageHelper.startPage(req.getPageNum(), req.getPageSize());
        
        // 2. 执行查询
        List<UserPO> userPOList = userDao.selectByCondition(req);
        
        // 3. 转换为PageInfo获取分页信息
        PageInfo<UserPO> pageInfo = new PageInfo<>(userPOList);
        
        // 4. 转换为聚合根列表
        List<UserAggregate> aggregateList = userPOList.stream()
                .map(userPO -> {
                    User user = UserConvertor.INSTANCE.toEntity(userPO);
                    return UserAggregate.create(user);
                })
                .collect(Collectors.toList());
        
        // 5. 构建分页响应
        return PageResp.success(
            aggregateList,
            pageInfo.getTotal(),
            pageInfo.getPageNum(),
            pageInfo.getPageSize()
        );
    }
    
    /**
     * 批量保存用户操作流水
     */
    private void batchSaveOperateStream(List<UserOperateStream> userOperateStreamList) {
        for (UserOperateStream stream : userOperateStreamList) {
            UserOperateStreamPO streamPO = 
                UserOperateStreamConvertor.INSTANCE.toPO(stream);
            userOperateStreamDao.insert(streamPO);
        }
    }
}
```

#### 5.4.2 基础设施层规范要点
1. **实现领域接口**：实现领域层定义的仓储接口
2. **使用MapStruct转换**：Entity ↔ PO 转换使用MapStruct
3. **事务管理**：在仓储实现层使用`@Transactional`
4. **按需加载**：关联数据按需加载，避免N+1查询
5. **日志记录**：关键操作记录DEBUG日志
6. **异常转换**：将持久层异常转换为领域异常

## 六、异常处理规范

### 6.1 异常体系设计

```
Throwable
├── Exception
│   ├── RuntimeException
│   │   ├── BusinessException (业务异常基类)
│   │   │   ├── UserException (用户领域异常)
│   │   │   ├── OrderException (订单领域异常)
│   │   │   └── ...
│   │   └── SystemException (系统异常)
│   └── checked exceptions (已检查异常)
└── Error (系统错误)
```

### 6.2 领域异常定义
```java
/**
 * 用户领域异常
 */
public class UserException extends BusinessException {
    
    private final UserErrorCode errorCode;
    
    private UserException(UserErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
    
    private UserException(UserErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
    
    private UserException(UserErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }
    
    public static UserException of(UserErrorCode errorCode) {
        return new UserException(errorCode);
    }
    
    public static UserException of(UserErrorCode errorCode, String message) {
        return new UserException(errorCode, message);
    }
    
    public static UserException of(UserErrorCode errorCode, Throwable cause) {
        return new UserException(errorCode, cause);
    }
    
    public UserErrorCode getErrorCode() {
        return errorCode;
    }
}

/**
 * 用户错误码枚举
 */
public enum UserErrorCode {
    
    // 用户不存在相关
    USER_NOT_EXIST("USER_001", "用户不存在"),
    
    // 用户状态相关
    USER_IS_FROZEN("USER_002", "用户已被冻结"),
    USER_IS_DELETED("USER_003", "用户已被删除"),
    USER_IS_ACTIVE("USER_004", "用户已是活跃状态"),
    
    // 数据校验相关
    TELEPHONE_EXISTS("USER_005", "手机号已存在"),
    INVITE_CODE_EXISTS("USER_006", "邀请码已存在"),
    NICKNAME_EMPTY("USER_007", "昵称不能为空"),
    NICKNAME_TOO_LONG("USER_008", "昵称长度不能超过20个字符"),
    
    // 业务操作相关
    PASSWORD_ERROR("USER_009", "密码错误"),
    OPERATION_NOT_ALLOWED("USER_010", "当前状态不允许此操作");
    
    private final String code;
    private final String message;
    
    UserErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getMessage() {
        return message;
    }
}
```

### 6.3 异常处理策略

#### 6.3.1 领域层抛出异常
```java
// 在实体或领域服务中
public void changeNickName(String newNickName) {
    if (!status.canModify()) {
        throw UserException.of(UserErrorCode.USER_IS_FROZEN);
    }
    this.nickName = newNickName;
}
```

#### 6.3.2 应用层处理异常
```java
// 在应用服务中
@Transactional
public void changeNickName(ChangeNickNameCommand command) {
    try {
        UserAggregate aggregate = userDomainService.changeNickName(
            command.getUserId(),
            command.getNickName()
        );
        userRepository.update(aggregate);
    } catch (UserException e) {
        log.error("修改昵称失败，用户ID: {}, 错误: {}", 
                 command.getUserId(), e.getMessage());
        throw e; // 重新抛出，由全局异常处理器处理
    }
}
```

#### 6.3.3 全局异常处理
```java
// 在web模块中
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(UserException.class)
    public Result<Void> handleUserException(UserException e) {
        log.warn("业务异常: {}", e.getMessage());
        return Result.fail(e.getErrorCode().getCode(), e.getMessage());
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleValidationException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return Result.fail("VALIDATION_ERROR", message);
    }
    
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error("系统异常: ", e);
        return Result.fail("SYSTEM_ERROR", "系统繁忙，请稍后重试");
    }
}
```

## 七、新功能开发指南

### 7.1 开发流程（以"修改用户邮箱"功能为例）

#### Step 1: 分析需求，设计API（kaleido-api模块）
```java
// 1.1 定义Command对象
package com.xiaoo.kaleido.api.user.command;

@Data
@Schema(description = "修改邮箱命令")
public class ChangeEmailCommand {
    
    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "用户ID不能为空")
    private String userId;
    
    @Schema(description = "新邮箱", requiredMode = Schema.RequiredMode.REQUIRED, example = "user@example.com")
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String newEmail;
    
    @Schema(description = "验证码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "验证码不能为空")
    private String verificationCode;
}

// 1.2 定义Response对象（如果需要）
@Data
@Schema(description = "修改邮箱响应")
public class ChangeEmailResponse {
    
    @Schema(description = "用户ID")
    private String userId;
    
    @Schema(description = "旧邮箱")
    private String oldEmail;
    
    @Schema(description = "新邮箱")
    private String newEmail;
    
    @Schema(description = "修改时间")
    private LocalDateTime updatedAt;
}
```

#### Step 2: 领域层实现（kaleido-user模块）

```java
// 2.1 在User实体中添加邮箱字段和行为方法
public class User extends BaseEntity {
    private String email;
    
    public void changeEmail(String newEmail) {
        if (!status.canModify()) {
            throw UserException.of(UserErrorCode.USER_IS_FROZEN);
        }
        
        if (newEmail == null || newEmail.trim().isEmpty()) {
            throw UserException.of(UserErrorCode.EMAIL_EMPTY);
        }
        
        if (!isValidEmail(newEmail)) {
            throw UserException.of(UserErrorCode.EMAIL_INVALID);
        }
        
        this.email = newEmail;
    }
    
    private boolean isValidEmail(String email) {
        // 邮箱验证逻辑
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}

// 2.2 在UserErrorCode中添加错误码
public enum UserErrorCode {
    // ... 其他错误码
    EMAIL_EMPTY("USER_011", "邮箱不能为空"),
    EMAIL_INVALID("USER_012", "邮箱格式不正确"),
    EMAIL_EXISTS("USER_013", "邮箱已存在"),
    VERIFICATION_CODE_ERROR("USER_014", "验证码错误");
}

// 2.3 领域服务接口
public interface UserDomainService {
    UserAggregate changeEmail(String userId, String newEmail, String verificationCode);
}

// 2.4 领域服务实现
@Service
public class UserDomainServiceImpl implements UserDomainService {
    
    private final UserRepository userRepository;
    private final VerificationCodeService verificationCodeService;
    
    @Override
    public UserAggregate changeEmail(String userId, String newEmail, String verificationCode) {
        // 1. 验证验证码
        boolean valid = verificationCodeService.verify(userId, verificationCode);
        if (!valid) {
            throw UserException.of(UserErrorCode.VERIFICATION_CODE_ERROR);
        }
        
        // 2. 查找用户
        UserAggregate aggregate = userRepository.findByIdOrThrow(userId);
        
        // 3. 检查邮箱是否已存在
        boolean emailExists = userRepository.existsByEmail(newEmail);
        if (emailExists) {
            throw UserException.of(UserErrorCode.EMAIL_EXISTS);
        }
        
        // 4. 执行业务逻辑
        String oldEmail = aggregate.getUser().getEmail();
        aggregate.getUser().changeEmail(newEmail);
        
        // 5. 记录操作流水
        aggregate.addOperateStream(
            UserOperateType.CHANGE_EMAIL,
            String.format("修改邮箱: %s -> %s", oldEmail, newEmail)
        );
        
        return aggregate;
    }
}
```

#### Step 3: 应用层实现

```java
// 3.1 命令服务
@Service
public class UserCommandService {
    
    public void changeEmail(ChangeEmailCommand command) {
        UserAggregate aggregate = userDomainService.changeEmail(
            command.getUserId(),
            command.getNewEmail(),
            command.getVerificationCode()
        );
        userRepository.update(aggregate);
        
        log.info("用户邮箱修改成功，用户ID: {}, 新邮箱: {}", 
                 command.getUserId(), command.getNewEmail());
    }
}

// 3.2 查询服务（如果需要）
@Service
public class UserQueryService {
    
    public ChangeEmailResponse getEmailChangeInfo(String userId) {
        UserAggregate aggregate = userRepository.findByIdOrThrow(userId);
        return emailConvertor.toResponse(aggregate);
    }
}

// 3.3 转换器（MapStruct）
@Mapper(componentModel = "spring")
public interface EmailConvertor {
    
    @Mapping(source = "user.email", target = "newEmail")
    @Mapping(source = "updatedAt", target = "updatedAt")
    ChangeEmailResponse toResponse(UserAggregate aggregate);
}
```

#### Step 4: 基础设施层实现

```java
// 4.1 在UserRepository接口中添加方法
public interface UserRepository {
    boolean existsByEmail(String email);
}

// 4.2 在UserRepositoryImpl中实现
@Override
public boolean existsByEmail(String email) {
    return userDao.existsByEmail(email);
}

// 4.3 在UserDao接口中添加方法
boolean existsByEmail(@Param("email") String email);

// 4.4 在UserDao.xml中添加SQL
<select id="existsByEmail" resultType="boolean">
    SELECT COUNT(*) > 0
    FROM user
    WHERE email = #{email} AND deleted = 0
</select>

// 4.5 在UserPO中添加email字段
@TableField("email")
private String email;
```

#### Step 5: 控制器层实现

```java
// 5.1 在UserController中添加接口
@RestController
@RequestMapping("/user")
public class UserController {
    
    @Operation(summary = "修改邮箱", description = "修改用户邮箱地址")
    @PutMapping("/{userId}/email")
    public Result<Void> changeEmail(
            @Parameter(description = "用户ID", required = true)
            @NotBlank @PathVariable String userId,
            @Valid @RequestBody ChangeEmailCommand command) {
        
        // 确保路径参数和请求体中的userId一致
        if (!userId.equals(command.getUserId())) {
            return Result.fail("PARAM_ERROR", "用户ID不一致");
        }
        
        userCommandService.changeEmail(command);
        return Result.success();
    }
    
    @Operation(summary = "获取邮箱修改信息", description = "获取用户邮箱修改的详细信息")
    @GetMapping("/{userId}/email-change-info")
    public Result<ChangeEmailResponse> getEmailChangeInfo(
            @Parameter(description = "用户ID", required = true)
            @NotBlank @PathVariable String userId) {
        
        ChangeEmailResponse response = userQueryService.getEmailChangeInfo(userId);
        return Result.success(response);
    }
}
```

#### Step 6: 数据库变更

```sql
-- 6.1 添加email字段到user表
ALTER TABLE user ADD COLUMN email VARCHAR(100) COMMENT '邮箱地址';

-- 6.2 添加唯一索引（如果需要）
CREATE UNIQUE INDEX idx_user_email ON user(email) WHERE deleted = 0;
```

### 7.2 开发检查清单

- [ ] API对象定义完整（Command/Query/Response）
- [ ] Swagger注解完整（@Tag, @Operation, @Parameter）
- [ ] 参数校验注解完整（@NotBlank, @Email, @Size等）
- [ ] 领域实体添加了业务行为方法
- [ ] 领域异常和错误码定义完整
- [ ] 领域服务接口和实现完整
- [ ] 应用层服务实现完整（命令服务和查询服务）
- [ ] MapStruct转换器定义完整
- [ ] 仓储接口和实现完整
- [ ] DAO接口和XML映射完整
- [ ] 控制器接口完整
- [ ] 数据库变更脚本准备
- [ ] 单元测试覆盖核心逻辑

## 八、最佳实践总结

### 8.1 架构设计原则

1. **依赖倒置原则**：高层模块不依赖低层模块，二者都依赖抽象
2. **单一职责原则**：每个类、每个方法只做一件事
3. **开闭原则**：对扩展开放，对修改关闭
4. **接口隔离原则**：客户端不应该依赖它不需要的接口
5. **迪米特法则**：一个对象应该对其他对象有最少的了解

### 8.2 代码质量要求

1. **可读性**：代码自解释，命名规范，注释完整
2. **可维护性**：结构清晰，职责单一，依赖合理
3. **可测试性**：依赖接口，便于单元测试和集成测试
4. **可扩展性**：通过接口扩展，符合开闭原则
5. **一致性**：遵循团队规范，风格统一

### 8.3 性能优化建议

1. **数据库**：
   - 合理设计索引，避免全表扫描
   - 使用分页查询，避免大数据量查询
   - 使用连接查询代替多次查询

2. **缓存**：
   - 热点数据使用缓存
   - 缓存穿透、击穿、雪崩防护
   - 缓存一致性策略

3. **代码层面**：
   - 使用Stream API时注意性能
   - 避免在循环中创建对象
   - 合理使用连接池

### 8.4 安全考虑

1. **输入验证**：所有用户输入必须验证
2. **SQL注入防护**：使用预编译语句，不使用字符串拼接
3. **XSS防护**：输出编码，使用安全框架
4. **CSRF防护**：使用Token验证
5. **权限控制**：接口级权限验证
6. **数据脱敏**：敏感信息脱敏后返回

## 九、常见问题解答

### 9.1 为什么使用MapStruct而不是手动转换？

1. **类型安全**：编译时检查，避免运行时错误
2. **性能高**：生成原生Java代码，无反射开销
3. **可维护**：映射关系明确，易于理解和修改
4. **减少样板代码**：自动生成转换代码，提高开发效率

### 9.2 为什么使用XML而不是注解配置SQL？

1. **可维护性**：SQL与Java代码分离，便于DBA评审
2. **灵活性**：支持复杂SQL和动态SQL
3. **可读性**：XML结构清晰，便于理解复杂查询
4. **工具支持**：IDE对XML有更好的语法高亮和提示

### 9.3 什么时候使用MyBatis-Plus，什么时候使用XML？

1. **使用MyBatis-Plus**：
   - 简单的CRUD操作
   - 单表条件查询
   - 不需要复杂SQL的场景

2. **使用XML**：
   - 多表关联查询
   - 复杂条件查询
   - 需要自定义SQL优化的场景
   - 存储过程调用

### 9.4 如何保证代码质量？

1. **代码审查**：所有代码必须经过Review
2. **单元测试**：核心逻辑必须有单元测试
3. **集成测试**：关键流程必须有集成测试
4. **静态代码分析**：使用SonarQube等工具
5. **持续集成**：自动化构建和测试

## 十、附录

### 10.1 相关工具和插件

1. **IDE插件**：
   - Lombok Plugin
   - MapStruct Support
   - MyBatisX (MyBatis插件)
   - SonarLint (代码质量检查)

2. **构建工具**：
   - Maven 3.6+
   - JDK 21

3. **代码质量工具**：
   - SonarQube
   - JaCoCo (代码覆盖率)
   - Checkstyle (代码风格检查)

### 10.2 参考文档

1. **官方文档**：
   - [Spring Boot Documentation](https://spring.io/projects/spring-boot)
   - [MyBatis-Plus Documentation](https://baomidou.com/)
   - [MapStruct Documentation](https://mapstruct.org/)

2. **设计模式**：
   - 《领域驱动设计：软件核心复杂性应对之道》
   - 《实现领域驱动设计》
   - 《整洁架构》

3. **项目内部文档**：
   - 数据库设计文档
   - API接口文档
   - 部署运维文档

---

**文档版本**: 1.0  
**最后更新**: 2025年12月30日  
**维护团队**: Kaleido开发团队  
**适用范围**: 所有基于Kaleido框架的新功能开发
