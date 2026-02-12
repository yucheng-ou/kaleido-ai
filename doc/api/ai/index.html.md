# kaleido-ai API 文档

| Version | Update Time | Status | Author | Description |
|---------|-------------|--------|--------|-------------|
|v2026-02-12 18:51:00|2026-02-12 18:51:00|auto|@Administrator|Created by smart-doc|



## Agent API控制器
### 查询Agent列表
**URL:** http://localhost:8082/ai/agent

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 查询Agent列表

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:8082/ai/agent
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|array|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─agentId|string|Agent ID|-|
|└─code|string|Agent编码（唯一）|-|
|└─name|string|Agent名称|-|
|└─description|string|Agent描述|-|
|└─systemPrompt|string|系统提示词|-|
|└─modelName|string|AI模型名称|-|
|└─temperature|number|温度参数|-|
|└─maxTokens|int32|最大token数|-|
|└─status|string|Agent状态|-|
|└─tools|array|工具列表|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─toolCode|string|工具编码|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─toolName|string|工具名称|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─toolType|string|工具类型|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─toolConfig|string|工具配置|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "hx0j48",
  "success": true,
  "data": [
    {
      "agentId": "124",
      "code": "12367",
      "name": "giovanna.kshlerin",
      "description": "nza2yk",
      "systemPrompt": "j2kvrw",
      "modelName": "giovanna.kshlerin",
      "temperature": 435,
      "maxTokens": 924,
      "status": "8i5ww4",
      "tools": [
        {
          "toolCode": "12367",
          "toolName": "giovanna.kshlerin",
          "toolType": "sjua6i",
          "toolConfig": "8jppfz"
        }
      ]
    }
  ]
}
```

### 与Agent进行聊天
**URL:** http://localhost:8082/ai/agent/{agentId}/chat

**Type:** POST

**Author:** ouyucheng

**Content-Type:** application/json

**Description:** 与Agent进行聊天

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|agentId|string|true|Agent ID|-|

**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|message|string|true|用户消息<br/>Validate[max: 5000; ]|-|
|conversationId|string|false|会话ID（可选）<br/>Validate[max: 100; ]|-|

**Request-example:**
```bash
curl -X POST -H 'Content-Type: application/json' -H 'Authorization:Bearer ' -i http://localhost:8082/ai/agent/{agentId}/chat --data '{
  "message": "success",
  "conversationId": "124"
}'
```

**Response-example:**
```json
[
  "xrghru",
  "ai90pp"
]
```

### 默认聊天（不使用特定Agent）
**URL:** http://localhost:8082/ai/agent/chat

**Type:** POST

**Author:** ouyucheng

**Content-Type:** application/json

**Description:** 默认聊天（不使用特定Agent）

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|message|string|true|用户消息<br/>Validate[max: 5000; ]|-|
|conversationId|string|false|会话ID（可选）<br/>Validate[max: 100; ]|-|

**Request-example:**
```bash
curl -X POST -H 'Content-Type: application/json' -H 'Authorization:Bearer ' -i http://localhost:8082/ai/agent/chat --data '{
  "message": "success",
  "conversationId": "124"
}'
```

**Response-example:**
```json
[
  "jamz9p",
  "p0o9n6"
]
```

## 会话API控制器
### 创建会话
**URL:** http://localhost:8082/ai/conversation

**Type:** POST

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 创建会话

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Request-example:**
```bash
curl -X POST -H 'Authorization:Bearer ' -i http://localhost:8082/ai/conversation
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|object|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─conversationId|string|会话ID|-|
|└─userId|string|用户ID|-|
|└─title|string|会话标题|-|
|└─messages|array|消息列表|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─role|string|消息角色：USER, ASSISTANT, SYSTEM|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─content|string|消息内容|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "e5y72h",
  "success": true,
  "data": {
    "conversationId": "124",
    "userId": "124",
    "title": "hm7mfu",
    "messages": [
      {
        "role": "lr3g7l",
        "content": "q0vou2"
      }
    ]
  }
}
```

### 更新会话标题
**URL:** http://localhost:8082/ai/conversation/{conversationId}/title

**Type:** PUT

**Author:** ouyucheng

**Content-Type:** application/json

**Description:** 更新会话标题

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|conversationId|string|true|会话ID|-|

**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|title|string|true|会话标题<br/>Validate[max: 200; ]|-|

**Request-example:**
```bash
curl -X PUT -H 'Content-Type: application/json' -H 'Authorization:Bearer ' -i http://localhost:8082/ai/conversation/{conversationId}/title --data '{
  "title": "9oowdm"
}'
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|object|响应数据<br/>成功时返回具体数据，失败时为null|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "3mrg8y",
  "success": true
}
```

### 查询会话详情
**URL:** http://localhost:8082/ai/conversation/{conversationId}

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 查询会话详情

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|conversationId|string|true|会话ID|-|

**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:8082/ai/conversation/{conversationId}
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|object|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─conversationId|string|会话ID|-|
|└─userId|string|用户ID|-|
|└─title|string|会话标题|-|
|└─messages|array|消息列表|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─role|string|消息角色：USER, ASSISTANT, SYSTEM|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─content|string|消息内容|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "61u59j",
  "success": true,
  "data": {
    "conversationId": "124",
    "userId": "124",
    "title": "niev6q",
    "messages": [
      {
        "role": "6hryro",
        "content": "5nr6e5"
      }
    ]
  }
}
```

### 查询用户会话列表
**URL:** http://localhost:8082/ai/conversation

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 查询用户会话列表

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:8082/ai/conversation
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|array|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─conversationId|string|会话ID|-|
|└─userId|string|用户ID|-|
|└─title|string|会话标题|-|
|└─messages|array|消息列表|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─role|string|消息角色：USER, ASSISTANT, SYSTEM|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─content|string|消息内容|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "50a49g",
  "success": true,
  "data": [
    {
      "conversationId": "124",
      "userId": "124",
      "title": "ai3kuw",
      "messages": [
        {
          "role": "otdfzr",
          "content": "kij4ka"
        }
      ]
    }
  ]
}
```

### 删除会话
**URL:** http://localhost:8082/ai/conversation/{conversationId}

**Type:** DELETE

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 删除会话

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|conversationId|string|true|会话ID|-|

**Request-example:**
```bash
curl -X DELETE -H 'Authorization:Bearer ' -i http://localhost:8082/ai/conversation/{conversationId}
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|object|响应数据<br/>成功时返回具体数据，失败时为null|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "gewcrb",
  "success": true
}
```

## 工作流API控制器
### 查询工作流详情
**URL:** http://localhost:8082/ai/workflow/{workflowId}

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 查询工作流详情

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|workflowId|string|true|工作流ID|-|

**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:8082/ai/workflow/{workflowId}
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|object|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─workflowId|string|工作流ID|-|
|└─code|string|工作流编码|-|
|└─name|string|工作流名称|-|
|└─description|string|工作流描述|-|
|└─definition|string|工作流定义|-|
|└─status|string|工作流状态|-|
|└─createTime|string|创建时间|-|
|└─updateTime|string|更新时间|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "izytcq",
  "success": true,
  "data": {
    "workflowId": "124",
    "code": "12367",
    "name": "giovanna.kshlerin",
    "description": "5uoxxi",
    "definition": "9hxfl2",
    "status": "kt9yf1",
    "createTime": "2026-02-12 18:58:51",
    "updateTime": "2026-02-12 18:58:51"
  }
}
```

### 执行工作流
**URL:** http://localhost:8082/ai/workflow/execute

**Type:** POST

**Author:** ouyucheng

**Content-Type:** application/json

**Description:** 执行工作流

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|workflowId|string|true|工作流ID<br/>Validate[max: 50; ]|-|
|inputData|string|false|输入数据<br/>Validate[max: 5000; ]|-|

**Request-example:**
```bash
curl -X POST -H 'Content-Type: application/json' -H 'Authorization:Bearer ' -i http://localhost:8082/ai/workflow/execute --data '{
  "workflowId": "124",
  "inputData": "6a6gmx"
}'
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|string|响应数据<br/>成功时返回具体数据，失败时为null|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "aqb25d",
  "success": true,
  "data": "kxjyvn"
}
```

### 查询用户的工作流执行记录
**URL:** http://localhost:8082/ai/workflow/executions/my

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 查询用户的工作流执行记录

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:8082/ai/workflow/executions/my
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|array|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─executionId|string|执行ID|-|
|└─workflowId|string|工作流ID|-|
|└─inputData|string|输入数据|-|
|└─outputData|string|输出数据|-|
|└─status|string|执行状态|-|
|└─errorMessage|string|错误信息|-|
|└─startAt|string|开始时间|-|
|└─endAt|string|结束时间|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "7eas4n",
  "success": true,
  "data": [
    {
      "executionId": "124",
      "workflowId": "124",
      "inputData": "d3x7f3",
      "outputData": "tmwdhz",
      "status": "8ybypc",
      "errorMessage": "success",
      "startAt": "2026-02-12 18:58:51",
      "endAt": "2026-02-12 18:58:51"
    }
  ]
}
```


