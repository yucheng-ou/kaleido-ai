# kaleido-admin API 文档

| Version | Update Time | Status | Author | Description |
|---------|-------------|--------|--------|-------------|
|v2026-02-12 18:51:00|2026-02-12 18:51:00|auto|@Administrator|Created by smart-doc|



## 管理员API
### 更新管理员信息(用于修改用户自身信息 不需要鉴权)
**URL:** http://localhost:9010/kaleido-admin/admin

**Type:** PUT

**Author:** ouyucheng

**Content-Type:** application/json

**Description:** 更新管理员信息(用于修改用户自身信息 不需要鉴权)

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|realName|string|false|真实姓名<br/>Validate[max: 20; ]|-|
|mobile|string|false|手机号<br/>Validate[regexp: ^1[3-9]\\d{9}$; ]|-|

**Request-example:**
```bash
curl -X PUT -H 'Content-Type: application/json' -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin --data '{
  "realName": "giovanna.kshlerin",
  "mobile": "1-425-254-1688"
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
  "msg": "wh74s9",
  "success": true
}
```

### 解冻管理员
**URL:** http://localhost:9010/kaleido-admin/admin/{adminId}/enable

**Type:** PUT

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 解冻管理员

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|adminId|string|true|管理员ID|-|

**Request-example:**
```bash
curl -X PUT -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin/{adminId}/enable
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
  "msg": "ig5frs",
  "success": true
}
```

### 冻结管理员
**URL:** http://localhost:9010/kaleido-admin/admin/{adminId}/freeze

**Type:** PUT

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 冻结管理员

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|adminId|string|true|管理员ID|-|

**Request-example:**
```bash
curl -X PUT -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin/{adminId}/freeze
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
  "msg": "n851k1",
  "success": true
}
```

### 分配角色给管理员
**URL:** http://localhost:9010/kaleido-admin/admin/{adminId}/roles

**Type:** POST

**Author:** ouyucheng

**Content-Type:** application/json

**Description:** 分配角色给管理员

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|adminId|string|true|管理员ID（从路径参数获取）|-|

**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|roleIds|array|true|角色ID列表|-|

**Request-example:**
```bash
curl -X POST -H 'Content-Type: application/json' -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin/{adminId}/roles --data '{
  "roleIds": [
    "zzj6rh"
  ]
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
  "msg": "is2r3s",
  "success": true
}
```

### 根据ID查询管理员信息
**URL:** http://localhost:9010/kaleido-admin/admin/{adminId}

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 根据ID查询管理员信息

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|adminId|string|true|管理员ID|-|

**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin/{adminId}
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|object|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─adminId|string|管理员ID|-|
|└─realName|string|真实姓名|-|
|└─mobile|string|手机号|-|
|└─status|string|管理员状态|-|
|└─lastLoginTime|string|最后登录时间|-|
|└─roleIds|array|角色ID列表|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "4at3zz",
  "success": true,
  "data": {
    "adminId": "124",
    "realName": "giovanna.kshlerin",
    "mobile": "1-425-254-1688",
    "status": "ri5hu7",
    "lastLoginTime": "2026-02-12 19:01:15",
    "roleIds": [
      "vqgznh"
    ]
  }
}
```

### 查询管理员自身信息 不需要鉴权
**URL:** http://localhost:9010/kaleido-admin/admin

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 查询管理员自身信息 不需要鉴权

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|object|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─adminId|string|管理员ID|-|
|└─realName|string|真实姓名|-|
|└─mobile|string|手机号|-|
|└─status|string|管理员状态|-|
|└─lastLoginTime|string|最后登录时间|-|
|└─roleIds|array|角色ID列表|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "y8bbhq",
  "success": true,
  "data": {
    "adminId": "124",
    "realName": "giovanna.kshlerin",
    "mobile": "1-425-254-1688",
    "status": "ouevhz",
    "lastLoginTime": "2026-02-12 19:01:15",
    "roleIds": [
      "sbx203"
    ]
  }
}
```

### 分页查询管理员
**URL:** http://localhost:9010/kaleido-admin/admin/page

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 分页查询管理员

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Query-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|pageNum|int32|false|页码|-|
|pageSize|int32|false|分页大小|-|
|realName|string|false|真实姓名|-|
|mobile|string|false|手机号|-|
|status|string|false|管理员状态|-|
|roleId|string|false|角色ID|-|
|startTime|string|false|开始时间（创建时间）|-|
|endTime|string|false|结束时间（创建时间）|-|

**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin/page?pageNum=167&pageSize=10&realName=giovanna.kshlerin&mobile=1-425-254-1688&status=t9m3q0&roleId=124&startTime=2026-02-12 18:51:01&endTime=2026-02-12 18:51:01
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|object|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─total|int64|No comments found.|-|
|└─list|array|No comments found.|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─adminId|string|管理员ID|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─realName|string|真实姓名|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─mobile|string|手机号|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─status|string|管理员状态|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─lastLoginTime|string|最后登录时间|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─roleIds|array|角色ID列表|-|
|└─pageNum|int32|当前页|-|
|└─pageSize|int32|每页的数量|-|
|└─size|int32|当前页的数量|-|
|└─startRow|int64|由于startRow和endRow不常用，这里说个具体的用法<br/>可以在页面中"显示startRow到endRow 共size条数据"<br/>当前页面第一个元素在数据库中的行号|-|
|└─endRow|int64|当前页面最后一个元素在数据库中的行号|-|
|└─pages|int32|总页数|-|
|└─prePage|int32|前一页|-|
|└─nextPage|int32|下一页|-|
|└─firstPage|boolean|是否为第一页|-|
|└─lastPage|boolean|是否为最后一页|-|
|└─hasPreviousPage|boolean|是否有前一页|-|
|└─hasNextPage|boolean|是否有下一页|-|
|└─navigatePages|int32|导航页码数|-|
|└─navigatepageNums|array|所有导航页号|-|
|└─navigateFirstPage|int32|导航条上的第一页|-|
|└─navigateLastPage|int32|导航条上的最后一页|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "s6w9z5",
  "success": true,
  "data": {
    "total": 10,
    "list": [
      {
        "adminId": "124",
        "realName": "giovanna.kshlerin",
        "mobile": "1-425-254-1688",
        "status": "vbnu5v",
        "lastLoginTime": "2026-02-12 19:01:15",
        "roleIds": [
          "src1tc"
        ]
      }
    ],
    "pageNum": 176,
    "pageSize": 10,
    "size": 10,
    "startRow": 706,
    "endRow": 842,
    "pages": 774,
    "prePage": 1,
    "nextPage": 1,
    "firstPage": true,
    "lastPage": true,
    "hasPreviousPage": true,
    "hasNextPage": true,
    "navigatePages": 390,
    "navigatepageNums": [
      34
    ],
    "navigateFirstPage": 1,
    "navigateLastPage": 1
  }
}
```

### 获取管理员的所有权限
**URL:** http://localhost:9010/kaleido-admin/admin/{adminId}/permissions

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 获取管理员的所有权限

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|adminId|string|true|管理员ID|-|

**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin/{adminId}/permissions
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|array|响应数据<br/>成功时返回具体数据，失败时为null|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "rsmioi",
  "success": true,
  "data": [
    "hueunr",
    "z3kkm4"
  ]
}
```

### 获取管理员的目录和菜单树（过滤按钮）
**URL:** http://localhost:9010/kaleido-admin/admin/{adminId}/directory-menus

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 获取管理员的目录和菜单树（过滤按钮）

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|adminId|string|true|管理员ID|-|

**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin/{adminId}/directory-menus
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|array|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─permissionId|string|权限ID|-|
|└─code|string|权限编码|-|
|└─name|string|权限名称|-|
|└─type|enum|权限类型<br/>[Enum values:<br/>DIRECTORY("DIRECTORY", "目录")<br/>MENU("MENU", "菜单")<br/>BUTTON("BUTTON", "按钮")<br/>]|-|
|└─parentId|string|父权限ID|-|
|└─sort|int32|排序|-|
|└─icon|string|图标|-|
|└─path|string|前端路由路径|-|
|└─component|string|前端组件路径|-|
|└─isHidden|boolean|是否隐藏|-|
|└─children|array|子权限列表|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "5px7i3",
  "success": true,
  "data": [
    {
      "permissionId": "124",
      "code": "12367",
      "name": "giovanna.kshlerin",
      "type": "DIRECTORY",
      "parentId": "124",
      "sort": 581,
      "icon": "tppvw6",
      "path": "7b1z96",
      "component": "csnjke",
      "isHidden": true,
      "children": [
        {
          "$ref": ".."
        }
      ]
    }
  ]
}
```

## AI管理API
### 创建Agent
**URL:** http://localhost:9010/kaleido-admin/admin/ai/agent

**Type:** POST

**Author:** ouyucheng

**Content-Type:** application/json

**Description:** 创建Agent

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|code|string|true|Agent编码<br/>Validate[max: 50; ]|-|
|name|string|true|Agent名称<br/>Validate[max: 100; ]|-|
|description|string|false|Agent描述<br/>Validate[max: 500; ]|-|
|systemPrompt|string|true|系统提示词<br/>Validate[max: 5000; ]|-|
|modelName|string|false|AI模型名称<br/>Validate[max: 100; ]|-|
|temperature|number|false|温度参数|-|
|maxTokens|int32|false|最大token数|-|

**Request-example:**
```bash
curl -X POST -H 'Content-Type: application/json' -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin/ai/agent --data '{
  "code": "12367",
  "name": "giovanna.kshlerin",
  "description": "4sppzz",
  "systemPrompt": "fgag18",
  "modelName": "giovanna.kshlerin",
  "temperature": 9,
  "maxTokens": 803
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
  "msg": "e98ced",
  "success": true,
  "data": "ux3toq"
}
```

### 更新Agent
**URL:** http://localhost:9010/kaleido-admin/admin/ai/agent/{agentId}

**Type:** PUT

**Author:** ouyucheng

**Content-Type:** application/json

**Description:** 更新Agent

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
|name|string|true|Agent名称<br/>Validate[max: 100; ]|-|
|description|string|false|Agent描述<br/>Validate[max: 500; ]|-|
|systemPrompt|string|true|系统提示词<br/>Validate[max: 5000; ]|-|
|modelName|string|false|AI模型名称<br/>Validate[max: 100; ]|-|
|temperature|number|false|温度参数|-|
|maxTokens|int32|false|最大token数|-|

**Request-example:**
```bash
curl -X PUT -H 'Content-Type: application/json' -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin/ai/agent/{agentId} --data '{
  "name": "giovanna.kshlerin",
  "description": "1wpyqz",
  "systemPrompt": "54td79",
  "modelName": "giovanna.kshlerin",
  "temperature": 265,
  "maxTokens": 222
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
  "msg": "2k1608",
  "success": true
}
```

### 启用Agent
**URL:** http://localhost:9010/kaleido-admin/admin/ai/agent/{agentId}/enable

**Type:** PUT

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 启用Agent

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|agentId|string|true|Agent ID|-|

**Request-example:**
```bash
curl -X PUT -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin/ai/agent/{agentId}/enable
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
  "msg": "oq1hol",
  "success": true
}
```

### 禁用Agent
**URL:** http://localhost:9010/kaleido-admin/admin/ai/agent/{agentId}/disable

**Type:** PUT

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 禁用Agent

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|agentId|string|true|Agent ID|-|

**Request-example:**
```bash
curl -X PUT -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin/ai/agent/{agentId}/disable
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
  "msg": "e2hgt6",
  "success": true
}
```

### 添加工具到Agent
**URL:** http://localhost:9010/kaleido-admin/admin/ai/agent/{agentId}/tools

**Type:** POST

**Author:** ouyucheng

**Content-Type:** application/json

**Description:** 添加工具到Agent

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
|toolCode|string|true|工具编码<br/>Validate[max: 50; ]|-|
|toolName|string|true|工具名称<br/>Validate[max: 100; ]|-|
|toolType|enum|true|工具类型<br/>[Enum values:<br/>MEMORY("MEMORY", "记忆")<br/>VECTOR_STORE("VECTOR_STORE", "向量存储")<br/>MCP("MCP", "MCP工具")<br/>]|-|
|toolConfig|string|false|工具配置<br/>Validate[max: 5000; ]|-|

**Request-example:**
```bash
curl -X POST -H 'Content-Type: application/json' -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin/ai/agent/{agentId}/tools --data '{
  "toolCode": "12367",
  "toolName": "giovanna.kshlerin",
  "toolType": "MEMORY",
  "toolConfig": "nyf7k9"
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
  "msg": "youixn",
  "success": true
}
```

### 从Agent移除工具
**URL:** http://localhost:9010/kaleido-admin/admin/ai/agent/{agentId}/tools/{toolCode}

**Type:** DELETE

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 从Agent移除工具

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|agentId|string|true| Agent ID|-|
|toolCode|string|true|工具编码|-|

**Request-example:**
```bash
curl -X DELETE -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin/ai/agent/{agentId}/tools/{toolCode}
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
  "msg": "43n89p",
  "success": true
}
```

### 查询Agent详情
**URL:** http://localhost:9010/kaleido-admin/admin/ai/agent/{agentId}

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 查询Agent详情

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|agentId|string|true|Agent ID|-|

**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin/ai/agent/{agentId}
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|object|响应数据<br/>成功时返回具体数据，失败时为null|-|
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
  "msg": "9gj7op",
  "success": true,
  "data": {
    "agentId": "124",
    "code": "12367",
    "name": "giovanna.kshlerin",
    "description": "037fox",
    "systemPrompt": "xhee0s",
    "modelName": "giovanna.kshlerin",
    "temperature": 222,
    "maxTokens": 210,
    "status": "w8h5mv",
    "tools": [
      {
        "toolCode": "12367",
        "toolName": "giovanna.kshlerin",
        "toolType": "o1c3fo",
        "toolConfig": "i9gg25"
      }
    ]
  }
}
```

### 根据编码查询Agent
**URL:** http://localhost:9010/kaleido-admin/admin/ai/agent/by-code/{code}

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 根据编码查询Agent

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|code|string|true|Agent编码|-|

**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin/ai/agent/by-code/{code}
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|object|响应数据<br/>成功时返回具体数据，失败时为null|-|
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
  "msg": "xq6121",
  "success": true,
  "data": {
    "agentId": "124",
    "code": "12367",
    "name": "giovanna.kshlerin",
    "description": "qj93uf",
    "systemPrompt": "eul2oc",
    "modelName": "giovanna.kshlerin",
    "temperature": 454,
    "maxTokens": 8,
    "status": "3qxng8",
    "tools": [
      {
        "toolCode": "12367",
        "toolName": "giovanna.kshlerin",
        "toolType": "fnzex9",
        "toolConfig": "2tc5yn"
      }
    ]
  }
}
```

### 查询Agent列表
**URL:** http://localhost:9010/kaleido-admin/admin/ai/agent

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
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin/ai/agent
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
  "msg": "v91sff",
  "success": true,
  "data": [
    {
      "agentId": "124",
      "code": "12367",
      "name": "giovanna.kshlerin",
      "description": "oz8cnz",
      "systemPrompt": "qypgwe",
      "modelName": "giovanna.kshlerin",
      "temperature": 831,
      "maxTokens": 790,
      "status": "gdfv5u",
      "tools": [
        {
          "toolCode": "12367",
          "toolName": "giovanna.kshlerin",
          "toolType": "kiu7l3",
          "toolConfig": "6j4kch"
        }
      ]
    }
  ]
}
```

### 创建工作流
**URL:** http://localhost:9010/kaleido-admin/admin/ai/workflow

**Type:** POST

**Author:** ouyucheng

**Content-Type:** application/json

**Description:** 创建工作流

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|code|string|true|工作流编码<br/>Validate[max: 50; ]|-|
|name|string|true|工作流名称<br/>Validate[max: 100; ]|-|
|description|string|false|工作流描述<br/>Validate[max: 500; ]|-|
|definition|string|true|工作流DSL定义<br/>Validate[max: 5000; ]|-|

**Request-example:**
```bash
curl -X POST -H 'Content-Type: application/json' -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin/ai/workflow --data '{
  "code": "12367",
  "name": "giovanna.kshlerin",
  "description": "x7bdz6",
  "definition": "wjpbbc"
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
  "msg": "ugbbk3",
  "success": true,
  "data": "fdvj31"
}
```

### 更新工作流
**URL:** http://localhost:9010/kaleido-admin/admin/ai/workflow/{workflowId}

**Type:** PUT

**Author:** ouyucheng

**Content-Type:** application/json

**Description:** 更新工作流

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|workflowId|string|true|工作流ID|-|

**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|name|string|true|工作流名称<br/>Validate[max: 100; ]|-|
|description|string|false|工作流描述<br/>Validate[max: 500; ]|-|
|definition|string|true|工作流DSL定义<br/>Validate[max: 5000; ]|-|

**Request-example:**
```bash
curl -X PUT -H 'Content-Type: application/json' -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin/ai/workflow/{workflowId} --data '{
  "name": "giovanna.kshlerin",
  "description": "wg0uxz",
  "definition": "2q9993"
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
  "msg": "joyqai",
  "success": true
}
```

### 启用工作流
**URL:** http://localhost:9010/kaleido-admin/admin/ai/workflow/{workflowId}/enable

**Type:** PUT

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 启用工作流

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
curl -X PUT -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin/ai/workflow/{workflowId}/enable
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
  "msg": "3pjhw3",
  "success": true
}
```

### 禁用工作流
**URL:** http://localhost:9010/kaleido-admin/admin/ai/workflow/{workflowId}/disable

**Type:** PUT

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 禁用工作流

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
curl -X PUT -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin/ai/workflow/{workflowId}/disable
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
  "msg": "8yxz3z",
  "success": true
}
```

### 查询工作流详情
**URL:** http://localhost:9010/kaleido-admin/admin/ai/workflow/{workflowId}

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
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin/ai/workflow/{workflowId}
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
  "msg": "kwani6",
  "success": true,
  "data": {
    "workflowId": "124",
    "code": "12367",
    "name": "giovanna.kshlerin",
    "description": "kpbqy2",
    "definition": "i66u4n",
    "status": "y1zus3",
    "createTime": "2026-02-12 19:01:16",
    "updateTime": "2026-02-12 19:01:16"
  }
}
```

### 根据编码查询工作流
**URL:** http://localhost:9010/kaleido-admin/admin/ai/workflow/by-code/{code}

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 根据编码查询工作流

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|code|string|true|工作流编码|-|

**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin/ai/workflow/by-code/{code}
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
  "msg": "1dxt5u",
  "success": true,
  "data": {
    "workflowId": "124",
    "code": "12367",
    "name": "giovanna.kshlerin",
    "description": "4hmop1",
    "definition": "d6vnkp",
    "status": "j30a8i",
    "createTime": "2026-02-12 19:01:16",
    "updateTime": "2026-02-12 19:01:16"
  }
}
```

### 查询工作流列表
**URL:** http://localhost:9010/kaleido-admin/admin/ai/workflow

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 查询工作流列表

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin/ai/workflow
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|array|响应数据<br/>成功时返回具体数据，失败时为null|-|
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
  "msg": "74pnfk",
  "success": true,
  "data": [
    {
      "workflowId": "124",
      "code": "12367",
      "name": "giovanna.kshlerin",
      "description": "2obql0",
      "definition": "t2vo8h",
      "status": "9h974d",
      "createTime": "2026-02-12 19:01:16",
      "updateTime": "2026-02-12 19:01:16"
    }
  ]
}
```

## 品牌管理API（管理后台）
### 创建品牌
**URL:** http://localhost:9010/kaleido-admin/admin/wardrobe/brand

**Type:** POST

**Author:** ouyucheng

**Content-Type:** application/json

**Description:** 创建品牌

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|name|string|true|品牌名称<br/>Validate[max: 100; ]|-|
|logoPath|string|false|Logo路径（在MinIO中的文件路径）<br/>Validate[max: 500; ]|-|
|description|string|false|品牌描述<br/>Validate[max: 500; ]|-|

**Request-example:**
```bash
curl -X POST -H 'Content-Type: application/json' -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin/wardrobe/brand --data '{
  "name": "giovanna.kshlerin",
  "logoPath": "qt8jix",
  "description": "rf1f61"
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
  "msg": "24m6jp",
  "success": true,
  "data": "owev42"
}
```

### 更新品牌信息
**URL:** http://localhost:9010/kaleido-admin/admin/wardrobe/brand/{brandId}

**Type:** PUT

**Author:** ouyucheng

**Content-Type:** application/json

**Description:** 更新品牌信息

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|brandId|string|true|品牌ID，不能为空|-|

**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|logoPath|string|false|Logo路径（在MinIO中的文件路径）<br/>Validate[max: 500; ]|-|
|description|string|false|品牌描述<br/>Validate[max: 500; ]|-|

**Request-example:**
```bash
curl -X PUT -H 'Content-Type: application/json' -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin/wardrobe/brand/{brandId} --data '{
  "logoPath": "idwaiq",
  "description": "l56bgf"
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
  "msg": "ue439r",
  "success": true
}
```

### 删除品牌（逻辑删除）
**URL:** http://localhost:9010/kaleido-admin/admin/wardrobe/brand/{brandId}

**Type:** DELETE

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 删除品牌（逻辑删除）

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|brandId|string|true|品牌ID，不能为空|-|

**Request-example:**
```bash
curl -X DELETE -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin/wardrobe/brand/{brandId}
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
  "msg": "xkfrqb",
  "success": true
}
```

### 查询所有品牌列表
**URL:** http://localhost:9010/kaleido-admin/admin/wardrobe/brand

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 查询所有品牌列表

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin/wardrobe/brand
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|array|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─brandId|string|品牌ID|-|
|└─name|string|品牌名称|-|
|└─logoPath|string|Logo路径（在MinIO中的文件路径）|-|
|└─description|string|品牌描述|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "n2kowh",
  "success": true,
  "data": [
    {
      "brandId": "124",
      "name": "giovanna.kshlerin",
      "logoPath": "dvng2q",
      "description": "tqxpuz"
    }
  ]
}
```

### 根据ID查询品牌详情
**URL:** http://localhost:9010/kaleido-admin/admin/wardrobe/brand/{brandId}

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 根据ID查询品牌详情

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|brandId|string|true|品牌ID，不能为空|-|

**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin/wardrobe/brand/{brandId}
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|object|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─brandId|string|品牌ID|-|
|└─name|string|品牌名称|-|
|└─logoPath|string|Logo路径（在MinIO中的文件路径）|-|
|└─description|string|品牌描述|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "i3lj6g",
  "success": true,
  "data": {
    "brandId": "124",
    "name": "giovanna.kshlerin",
    "logoPath": "qy9ew9",
    "description": "slscg4"
  }
}
```

## 字典控制器
### 创建字典
**URL:** http://localhost:9010/kaleido-admin/dict

**Type:** POST

**Author:** ouyucheng

**Content-Type:** application/json

**Description:** 创建字典

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|typeCode|string|true|字典类型编码|-|
|typeName|string|true|字典类型名称|-|
|dictCode|string|true|字典编码|-|
|dictName|string|true|字典名称|-|
|dictValue|string|false|字典值|-|
|sort|int32|true|排序|-|

**Request-example:**
```bash
curl -X POST -H 'Content-Type: application/json' -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/dict --data '{
  "typeCode": "12367",
  "typeName": "giovanna.kshlerin",
  "dictCode": "12367",
  "dictName": "giovanna.kshlerin",
  "dictValue": "p2s2vh",
  "sort": 602
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
  "msg": "21dtig",
  "success": true,
  "data": "n1kv48"
}
```

### 更新字典
**URL:** http://localhost:9010/kaleido-admin/dict/{typeCode}/{dictCode}

**Type:** PUT

**Author:** ouyucheng

**Content-Type:** application/json

**Description:** 更新字典

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|typeCode|string|true|字典类型编码|-|
|dictCode|string|true|字典编码|-|

**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|typeName|string|false|字典类型名称|-|
|dictName|string|false|字典名称|-|
|dictValue|string|false|字典值|-|
|sort|int32|false|排序|-|

**Request-example:**
```bash
curl -X PUT -H 'Content-Type: application/json' -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/dict/{typeCode}/{dictCode} --data '{
  "typeName": "giovanna.kshlerin",
  "dictName": "giovanna.kshlerin",
  "dictValue": "4x2tu6",
  "sort": 919
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
  "msg": "fwaa3x",
  "success": true
}
```

### 删除字典
**URL:** http://localhost:9010/kaleido-admin/dict/{typeCode}/{dictCode}

**Type:** DELETE

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 删除字典

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|typeCode|string|true|字典类型编码|-|
|dictCode|string|true|字典编码|-|

**Request-example:**
```bash
curl -X DELETE -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/dict/{typeCode}/{dictCode}
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
  "msg": "7yogs9",
  "success": true
}
```

### 分页查询字典列表
**URL:** http://localhost:9010/kaleido-admin/dict/page

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 分页查询字典列表

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Query-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|pageNum|int32|false|页码|-|
|pageSize|int32|false|分页大小|-|
|typeCode|string|false|字典类型编码|-|
|typeName|string|false|字典类型名称（模糊查询）|-|
|dictCode|string|false|字典编码|-|
|dictName|string|false|字典名称（模糊查询）|-|
|dictValue|string|false|字典值（模糊查询）|-|

**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/dict/page?pageNum=132&pageSize=10&typeCode=12367&typeName=giovanna.kshlerin&dictCode=12367&dictName=giovanna.kshlerin&dictValue=qi01e5
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|object|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─total|int64|No comments found.|-|
|└─list|array|No comments found.|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─id|string|字典ID|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─typeCode|string|字典类型编码|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─typeName|string|字典类型名称|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─dictCode|string|字典编码|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─dictName|string|字典名称|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─dictValue|string|字典值|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─sort|int32|排序|-|
|└─pageNum|int32|当前页|-|
|└─pageSize|int32|每页的数量|-|
|└─size|int32|当前页的数量|-|
|└─startRow|int64|由于startRow和endRow不常用，这里说个具体的用法<br/>可以在页面中"显示startRow到endRow 共size条数据"<br/>当前页面第一个元素在数据库中的行号|-|
|└─endRow|int64|当前页面最后一个元素在数据库中的行号|-|
|└─pages|int32|总页数|-|
|└─prePage|int32|前一页|-|
|└─nextPage|int32|下一页|-|
|└─firstPage|boolean|是否为第一页|-|
|└─lastPage|boolean|是否为最后一页|-|
|└─hasPreviousPage|boolean|是否有前一页|-|
|└─hasNextPage|boolean|是否有下一页|-|
|└─navigatePages|int32|导航页码数|-|
|└─navigatepageNums|array|所有导航页号|-|
|└─navigateFirstPage|int32|导航条上的第一页|-|
|└─navigateLastPage|int32|导航条上的最后一页|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "ql7lhu",
  "success": true,
  "data": {
    "total": 180,
    "list": [
      {
        "id": "124",
        "typeCode": "12367",
        "typeName": "giovanna.kshlerin",
        "dictCode": "12367",
        "dictName": "giovanna.kshlerin",
        "dictValue": "8d7gqr",
        "sort": 269
      }
    ],
    "pageNum": 978,
    "pageSize": 10,
    "size": 10,
    "startRow": 960,
    "endRow": 718,
    "pages": 842,
    "prePage": 1,
    "nextPage": 1,
    "firstPage": true,
    "lastPage": true,
    "hasPreviousPage": true,
    "hasNextPage": true,
    "navigatePages": 724,
    "navigatepageNums": [
      363
    ],
    "navigateFirstPage": 1,
    "navigateLastPage": 1
  }
}
```

## 文件控制器
### 上传文件<br>接收前端上传的文件，保存到MinIO
**URL:** http://localhost:9010/kaleido-admin/public/file/upload

**Type:** POST

**Author:** ouyucheng

**Content-Type:** multipart/form-data

**Description:** 上传文件
接收前端上传的文件，保存到MinIO

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Query-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|file|file|true|上传的文件|-|

**Request-example:**
```bash
curl -X POST -H 'Content-Type: multipart/form-data' -H 'Authorization:Bearer ' -F 'file=' -i http://localhost:9010/kaleido-admin/public/file/upload
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|object|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─objectName|string|对象名称<br/>文件在MinIO中的存储路径，格式：yyyyMMdd/fileHash.extension|-|
|└─fileUrl|string|文件URL<br/>文件的完整访问URL|-|
|└─originalName|string|原始文件名<br/>用户上传时的原始文件名|-|
|└─fileSize|int64|文件大小<br/>文件大小，单位：字节|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "nw296j",
  "success": true,
  "data": {
    "objectName": "giovanna.kshlerin",
    "fileUrl": "www.roman-hills.co",
    "originalName": "giovanna.kshlerin",
    "fileSize": 46
  }
}
```

## 通知管理API（管理后台）
### 添加通知模板
**URL:** http://localhost:9010/kaleido-admin/admin/notice/template

**Type:** POST

**Author:** ouyucheng

**Content-Type:** application/json

**Description:** 添加通知模板

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|name|string|true|模板名称<br/>Validate[max: 50; ]|-|
|code|string|true|模板编码<br/>Validate[max: 50; ]|-|
|content|string|true|模板内容<br/>Validate[max: 500; ]|-|

**Request-example:**
```bash
curl -X POST -H 'Content-Type: application/json' -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin/notice/template --data '{
  "name": "giovanna.kshlerin",
  "code": "12367",
  "content": "y1aa3a"
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
  "msg": "ktz6n8",
  "success": true,
  "data": "v6abbz"
}
```

### 根据模板编码获取模板详情
**URL:** http://localhost:9010/kaleido-admin/admin/notice/template/{code}

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 根据模板编码获取模板详情

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|code|string|true|模板编码|-|

**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin/notice/template/{code}
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|object|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─id|string|模板ID|-|
|└─code|string|模板编码|-|
|└─name|string|模板名称|-|
|└─content|string|模板内容|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "eexvqj",
  "success": true,
  "data": {
    "id": "124",
    "code": "12367",
    "name": "giovanna.kshlerin",
    "content": "ccqvo9"
  }
}
```

### 根据ID获取通知详情
**URL:** http://localhost:9010/kaleido-admin/admin/notice/{id}

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 根据ID获取通知详情

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|id|string|true|通知ID|-|

**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin/notice/{id}
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|object|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─id|string|通知ID|-|
|└─noticeType|enum|通知类型<br/>[Enum values:<br/>SMS("短信通知")<br/>EMAIL("邮件通知")<br/>WECHAT("微信通知")<br/>]|-|
|└─targetAddress|string|目标地址|-|
|└─status|enum|通知状态<br/>[Enum values:<br/>PENDING("待发送")<br/>SUCCESS("发送成功")<br/>FAILED("发送失败")<br/>]|-|
|└─businessType|enum|业务类型<br/>[Enum values:<br/>VERIFY_CODE("验证码")<br/>]|-|
|└─content|string|通知内容|-|
|└─resultMessage|string|发送结果信息|-|
|└─sentAt|string|发送时间|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "9ct7xp",
  "success": true,
  "data": {
    "id": "124",
    "noticeType": "SMS",
    "targetAddress": "Apt. 419 2515 Chaya Forges， Deniceburgh， VT 17003",
    "status": "PENDING",
    "businessType": "VERIFY_CODE",
    "content": "dn2qhj",
    "resultMessage": "success",
    "sentAt": "2026-02-12 19:01:16"
  }
}
```

### 根据目标地址获取通知列表
**URL:** http://localhost:9010/kaleido-admin/admin/notice/target/{target}

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 根据目标地址获取通知列表

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|target|string|true|目标地址|-|

**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin/notice/target/{target}
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|array|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─id|string|通知ID|-|
|└─noticeType|enum|通知类型<br/>[Enum values:<br/>SMS("短信通知")<br/>EMAIL("邮件通知")<br/>WECHAT("微信通知")<br/>]|-|
|└─targetAddress|string|目标地址|-|
|└─status|enum|通知状态<br/>[Enum values:<br/>PENDING("待发送")<br/>SUCCESS("发送成功")<br/>FAILED("发送失败")<br/>]|-|
|└─businessType|enum|业务类型<br/>[Enum values:<br/>VERIFY_CODE("验证码")<br/>]|-|
|└─content|string|通知内容|-|
|└─resultMessage|string|发送结果信息|-|
|└─sentAt|string|发送时间|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "5vg2xz",
  "success": true,
  "data": [
    {
      "id": "124",
      "noticeType": "SMS",
      "targetAddress": "Apt. 419 2515 Chaya Forges， Deniceburgh， VT 17003",
      "status": "PENDING",
      "businessType": "VERIFY_CODE",
      "content": "anwklg",
      "resultMessage": "success",
      "sentAt": "2026-02-12 19:01:16"
    }
  ]
}
```

### 分页查询通知列表
**URL:** http://localhost:9010/kaleido-admin/admin/notice/page

**Type:** POST

**Author:** ouyucheng

**Content-Type:** application/json

**Description:** 分页查询通知列表

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|pageNum|int32|false|页码|-|
|pageSize|int32|false|分页大小|-|
|target|string|false|No comments found.|-|
|noticeType|string|false|No comments found.|-|
|status|string|false|No comments found.|-|
|businessType|string|false|No comments found.|-|

**Request-example:**
```bash
curl -X POST -H 'Content-Type: application/json' -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin/notice/page --data '{
  "pageNum": 880,
  "pageSize": 10,
  "target": "mx848b",
  "noticeType": "qtze0e",
  "status": "cel6gd",
  "businessType": "qvotfn"
}'
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|object|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─total|int64|No comments found.|-|
|└─list|array|No comments found.|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─id|string|通知ID|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─noticeType|enum|通知类型<br/>[Enum values:<br/>SMS("短信通知")<br/>EMAIL("邮件通知")<br/>WECHAT("微信通知")<br/>]|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─targetAddress|string|目标地址|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─status|enum|通知状态<br/>[Enum values:<br/>PENDING("待发送")<br/>SUCCESS("发送成功")<br/>FAILED("发送失败")<br/>]|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─businessType|enum|业务类型<br/>[Enum values:<br/>VERIFY_CODE("验证码")<br/>]|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─content|string|通知内容|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─resultMessage|string|发送结果信息|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─sentAt|string|发送时间|-|
|└─pageNum|int32|当前页|-|
|└─pageSize|int32|每页的数量|-|
|└─size|int32|当前页的数量|-|
|└─startRow|int64|由于startRow和endRow不常用，这里说个具体的用法<br/>可以在页面中"显示startRow到endRow 共size条数据"<br/>当前页面第一个元素在数据库中的行号|-|
|└─endRow|int64|当前页面最后一个元素在数据库中的行号|-|
|└─pages|int32|总页数|-|
|└─prePage|int32|前一页|-|
|└─nextPage|int32|下一页|-|
|└─firstPage|boolean|是否为第一页|-|
|└─lastPage|boolean|是否为最后一页|-|
|└─hasPreviousPage|boolean|是否有前一页|-|
|└─hasNextPage|boolean|是否有下一页|-|
|└─navigatePages|int32|导航页码数|-|
|└─navigatepageNums|array|所有导航页号|-|
|└─navigateFirstPage|int32|导航条上的第一页|-|
|└─navigateLastPage|int32|导航条上的最后一页|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "a777ai",
  "success": true,
  "data": {
    "total": 415,
    "list": [
      {
        "id": "124",
        "noticeType": "SMS",
        "targetAddress": "Apt. 419 2515 Chaya Forges， Deniceburgh， VT 17003",
        "status": "PENDING",
        "businessType": "VERIFY_CODE",
        "content": "dxlv11",
        "resultMessage": "success",
        "sentAt": "2026-02-12 19:01:16"
      }
    ],
    "pageNum": 573,
    "pageSize": 10,
    "size": 10,
    "startRow": 297,
    "endRow": 14,
    "pages": 608,
    "prePage": 1,
    "nextPage": 1,
    "firstPage": true,
    "lastPage": true,
    "hasPreviousPage": true,
    "hasNextPage": true,
    "navigatePages": 175,
    "navigatepageNums": [
      803
    ],
    "navigateFirstPage": 1,
    "navigateLastPage": 1
  }
}
```

### 分页查询通知模板列表
**URL:** http://localhost:9010/kaleido-admin/admin/notice/template/page

**Type:** POST

**Author:** ouyucheng

**Content-Type:** application/json

**Description:** 分页查询通知模板列表

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|pageNum|int32|false|页码|-|
|pageSize|int32|false|分页大小|-|
|name|string|false|No comments found.|-|
|code|string|false|No comments found.|-|
|noticeType|string|false|No comments found.|-|

**Request-example:**
```bash
curl -X POST -H 'Content-Type: application/json' -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin/notice/template/page --data '{
  "pageNum": 188,
  "pageSize": 10,
  "name": "giovanna.kshlerin",
  "code": "12367",
  "noticeType": "nk58oo"
}'
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|object|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─total|int64|No comments found.|-|
|└─list|array|No comments found.|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─id|string|模板ID|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─code|string|模板编码|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─name|string|模板名称|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─content|string|模板内容|-|
|└─pageNum|int32|当前页|-|
|└─pageSize|int32|每页的数量|-|
|└─size|int32|当前页的数量|-|
|└─startRow|int64|由于startRow和endRow不常用，这里说个具体的用法<br/>可以在页面中"显示startRow到endRow 共size条数据"<br/>当前页面第一个元素在数据库中的行号|-|
|└─endRow|int64|当前页面最后一个元素在数据库中的行号|-|
|└─pages|int32|总页数|-|
|└─prePage|int32|前一页|-|
|└─nextPage|int32|下一页|-|
|└─firstPage|boolean|是否为第一页|-|
|└─lastPage|boolean|是否为最后一页|-|
|└─hasPreviousPage|boolean|是否有前一页|-|
|└─hasNextPage|boolean|是否有下一页|-|
|└─navigatePages|int32|导航页码数|-|
|└─navigatepageNums|array|所有导航页号|-|
|└─navigateFirstPage|int32|导航条上的第一页|-|
|└─navigateLastPage|int32|导航条上的最后一页|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "xin9ul",
  "success": true,
  "data": {
    "total": 205,
    "list": [
      {
        "id": "124",
        "code": "12367",
        "name": "giovanna.kshlerin",
        "content": "vbr5ar"
      }
    ],
    "pageNum": 648,
    "pageSize": 10,
    "size": 10,
    "startRow": 363,
    "endRow": 875,
    "pages": 622,
    "prePage": 1,
    "nextPage": 1,
    "firstPage": true,
    "lastPage": true,
    "hasPreviousPage": true,
    "hasNextPage": true,
    "navigatePages": 520,
    "navigatepageNums": [
      177
    ],
    "navigateFirstPage": 1,
    "navigateLastPage": 1
  }
}
```

## 权限控制器
### 创建权限
**URL:** http://localhost:9010/kaleido-admin/permission

**Type:** POST

**Author:** ouyucheng

**Content-Type:** application/json

**Description:** 创建权限

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|code|string|true|权限编码<br/>Validate[max: 50; regexp: ^[a-zA-Z0-9:_-]+$; ]|-|
|name|string|true|权限名称<br/>Validate[max: 50; ]|-|
|type|enum|true|权限类型<br/>[Enum values:<br/>DIRECTORY("DIRECTORY", "目录")<br/>MENU("MENU", "菜单")<br/>BUTTON("BUTTON", "按钮")<br/>]|-|
|parentId|string|false|父权限ID|-|
|sort|int32|false|排序|-|
|icon|string|false|图标<br/>Validate[max: 100; ]|-|
|path|string|false|前端路由路径<br/>Validate[max: 200; ]|-|
|component|string|false|前端组件路径<br/>Validate[max: 200; ]|-|
|isHidden|boolean|false|是否隐藏|-|

**Request-example:**
```bash
curl -X POST -H 'Content-Type: application/json' -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/permission --data '{
  "code": "12367",
  "name": "giovanna.kshlerin",
  "type": "DIRECTORY",
  "parentId": "124",
  "sort": 211,
  "icon": "536tej",
  "path": "ipjbdw",
  "component": "1h5el1",
  "isHidden": true
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
  "msg": "5sba7q",
  "success": true,
  "data": "x17zxv"
}
```

### 更新权限信息
**URL:** http://localhost:9010/kaleido-admin/permission/{permissionId}

**Type:** PUT

**Author:** ouyucheng

**Content-Type:** application/json

**Description:** 更新权限信息

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|permissionId|string|true|权限ID（从路径参数获取）|-|

**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|name|string|true|权限名称<br/>Validate[max: 50; ]|-|
|type|enum|true|权限类型<br/>[Enum values:<br/>DIRECTORY("DIRECTORY", "目录")<br/>MENU("MENU", "菜单")<br/>BUTTON("BUTTON", "按钮")<br/>]|-|
|parentId|string|false|父权限ID|-|
|sort|int32|false|排序|-|
|icon|string|false|图标<br/>Validate[max: 100; ]|-|
|path|string|false|前端路由路径<br/>Validate[max: 200; ]|-|
|component|string|false|前端组件路径<br/>Validate[max: 200; ]|-|
|isHidden|boolean|false|是否隐藏|-|

**Request-example:**
```bash
curl -X PUT -H 'Content-Type: application/json' -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/permission/{permissionId} --data '{
  "name": "giovanna.kshlerin",
  "type": "DIRECTORY",
  "parentId": "124",
  "sort": 33,
  "icon": "w3gjbj",
  "path": "zqj9yv",
  "component": "38wmxx",
  "isHidden": true
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
  "msg": "or0b0e",
  "success": true
}
```

### 更新权限编码
**URL:** http://localhost:9010/kaleido-admin/permission/{permissionId}/code

**Type:** PUT

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 更新权限编码

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|permissionId|string|true|权限ID|-|

**Query-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|code|string|true|新的权限编码|-|

**Request-example:**
```bash
curl -X PUT -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/permission/{permissionId}/code
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
  "msg": "l3qlfj",
  "success": true
}
```

### 删除权限
**URL:** http://localhost:9010/kaleido-admin/permission/{permissionId}

**Type:** DELETE

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 删除权限

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|permissionId|string|true|权限ID|-|

**Request-example:**
```bash
curl -X DELETE -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/permission/{permissionId}
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
  "msg": "hhn8bx",
  "success": true
}
```

### 根据ID查询权限信息
**URL:** http://localhost:9010/kaleido-admin/permission/{permissionId}

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 根据ID查询权限信息

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|permissionId|string|true|权限ID|-|

**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/permission/{permissionId}
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|object|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─permissionId|string|权限ID|-|
|└─code|string|权限编码|-|
|└─name|string|权限名称|-|
|└─type|enum|权限类型<br/>[Enum values:<br/>DIRECTORY("DIRECTORY", "目录")<br/>MENU("MENU", "菜单")<br/>BUTTON("BUTTON", "按钮")<br/>]|-|
|└─parentId|string|父权限ID|-|
|└─sort|int32|排序|-|
|└─icon|string|图标|-|
|└─path|string|前端路由路径|-|
|└─component|string|前端组件路径|-|
|└─isHidden|boolean|是否隐藏|-|
|└─children|array|子权限列表|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "gj2dt5",
  "success": true,
  "data": {
    "permissionId": "124",
    "code": "12367",
    "name": "giovanna.kshlerin",
    "type": "DIRECTORY",
    "parentId": "124",
    "sort": 0,
    "icon": "3umqxf",
    "path": "gj7arc",
    "component": "sa9eu9",
    "isHidden": true,
    "children": [
      {
        "$ref": ".."
      }
    ]
  }
}
```

### 根据编码查询权限信息
**URL:** http://localhost:9010/kaleido-admin/permission/code/{code}

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 根据编码查询权限信息

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|code|string|true|权限编码|-|

**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/permission/code/{code}
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|object|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─permissionId|string|权限ID|-|
|└─code|string|权限编码|-|
|└─name|string|权限名称|-|
|└─type|enum|权限类型<br/>[Enum values:<br/>DIRECTORY("DIRECTORY", "目录")<br/>MENU("MENU", "菜单")<br/>BUTTON("BUTTON", "按钮")<br/>]|-|
|└─parentId|string|父权限ID|-|
|└─sort|int32|排序|-|
|└─icon|string|图标|-|
|└─path|string|前端路由路径|-|
|└─component|string|前端组件路径|-|
|└─isHidden|boolean|是否隐藏|-|
|└─children|array|子权限列表|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "9b05ru",
  "success": true,
  "data": {
    "permissionId": "124",
    "code": "12367",
    "name": "giovanna.kshlerin",
    "type": "DIRECTORY",
    "parentId": "124",
    "sort": 987,
    "icon": "s5pa6x",
    "path": "zh2q0k",
    "component": "xppdzw",
    "isHidden": true,
    "children": [
      {
        "$ref": ".."
      }
    ]
  }
}
```

### 根据父权限ID查询子权限列表
**URL:** http://localhost:9010/kaleido-admin/permission/parent/{parentId}

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 根据父权限ID查询子权限列表

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|parentId|string|true|父权限ID|-|

**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/permission/parent/{parentId}
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|array|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─permissionId|string|权限ID|-|
|└─code|string|权限编码|-|
|└─name|string|权限名称|-|
|└─type|enum|权限类型<br/>[Enum values:<br/>DIRECTORY("DIRECTORY", "目录")<br/>MENU("MENU", "菜单")<br/>BUTTON("BUTTON", "按钮")<br/>]|-|
|└─parentId|string|父权限ID|-|
|└─sort|int32|排序|-|
|└─icon|string|图标|-|
|└─path|string|前端路由路径|-|
|└─component|string|前端组件路径|-|
|└─isHidden|boolean|是否隐藏|-|
|└─children|array|子权限列表|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "mda300",
  "success": true,
  "data": [
    {
      "permissionId": "124",
      "code": "12367",
      "name": "giovanna.kshlerin",
      "type": "DIRECTORY",
      "parentId": "124",
      "sort": 69,
      "icon": "43msc7",
      "path": "wwbvge",
      "component": "zicosy",
      "isHidden": true,
      "children": [
        {
          "$ref": ".."
        }
      ]
    }
  ]
}
```

### 获取权限树
**URL:** http://localhost:9010/kaleido-admin/permission/tree

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 获取权限树

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/permission/tree
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|array|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─permissionId|string|权限ID|-|
|└─code|string|权限编码|-|
|└─name|string|权限名称|-|
|└─type|enum|权限类型<br/>[Enum values:<br/>DIRECTORY("DIRECTORY", "目录")<br/>MENU("MENU", "菜单")<br/>BUTTON("BUTTON", "按钮")<br/>]|-|
|└─parentId|string|父权限ID|-|
|└─sort|int32|排序|-|
|└─icon|string|图标|-|
|└─path|string|前端路由路径|-|
|└─component|string|前端组件路径|-|
|└─isHidden|boolean|是否隐藏|-|
|└─children|array|子权限列表|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "gssthz",
  "success": true,
  "data": [
    {
      "permissionId": "124",
      "code": "12367",
      "name": "giovanna.kshlerin",
      "type": "DIRECTORY",
      "parentId": "124",
      "sort": 418,
      "icon": "afgo8p",
      "path": "x3hzd4",
      "component": "4h1w09",
      "isHidden": true,
      "children": [
        {
          "$ref": ".."
        }
      ]
    }
  ]
}
```

## 角色控制器
### 创建角色
**URL:** http://localhost:9010/kaleido-admin/role

**Type:** POST

**Author:** ouyucheng

**Content-Type:** application/json

**Description:** 创建角色

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|code|string|true|角色编码<br/>Validate[max: 50; regexp: ^[a-zA-Z0-9:_-]+$; ]|-|
|name|string|true|角色名称<br/>Validate[max: 50; ]|-|
|description|string|false|角色描述<br/>Validate[max: 200; ]|-|

**Request-example:**
```bash
curl -X POST -H 'Content-Type: application/json' -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/role --data '{
  "code": "12367",
  "name": "giovanna.kshlerin",
  "description": "l74re4"
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
  "msg": "wz3uao",
  "success": true,
  "data": "a9o953"
}
```

### 更新角色信息
**URL:** http://localhost:9010/kaleido-admin/role/{roleId}

**Type:** PUT

**Author:** ouyucheng

**Content-Type:** application/json

**Description:** 更新角色信息

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|roleId|string|true|角色ID（从路径参数获取）|-|

**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|name|string|true|角色名称<br/>Validate[max: 50; ]|-|
|description|string|false|角色描述<br/>Validate[max: 200; ]|-|

**Request-example:**
```bash
curl -X PUT -H 'Content-Type: application/json' -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/role/{roleId} --data '{
  "name": "giovanna.kshlerin",
  "description": "6pbrc3"
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
  "msg": "8pauzo",
  "success": true
}
```

### 删除角色
**URL:** http://localhost:9010/kaleido-admin/role/{roleId}

**Type:** DELETE

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 删除角色

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|roleId|string|true|角色ID|-|

**Request-example:**
```bash
curl -X DELETE -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/role/{roleId}
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
  "msg": "7itlzg",
  "success": true
}
```

### 分配权限给角色
**URL:** http://localhost:9010/kaleido-admin/role/{roleId}/permissions

**Type:** POST

**Author:** ouyucheng

**Content-Type:** application/json

**Description:** 分配权限给角色

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|roleId|string|true|角色ID（从路径参数获取）|-|

**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|permissionIds|array|true|权限ID列表|-|

**Request-example:**
```bash
curl -X POST -H 'Content-Type: application/json' -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/role/{roleId}/permissions --data '{
  "permissionIds": [
    "vlokpy"
  ]
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
  "msg": "unb1m0",
  "success": true
}
```

### 根据ID查询角色信息
**URL:** http://localhost:9010/kaleido-admin/role/{roleId}

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 根据ID查询角色信息

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|roleId|string|true|角色ID|-|

**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/role/{roleId}
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|object|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─roleId|string|角色ID|-|
|└─code|string|角色编码|-|
|└─name|string|角色名称|-|
|└─description|string|角色描述|-|
|└─enabled|boolean|是否启用|-|
|└─permissionIds|array|权限ID列表|-|
|└─permissions|array|权限信息列表|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─permissionId|string|权限ID|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─code|string|权限编码|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─name|string|权限名称|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─type|enum|权限类型<br/>[Enum values:<br/>DIRECTORY("DIRECTORY", "目录")<br/>MENU("MENU", "菜单")<br/>BUTTON("BUTTON", "按钮")<br/>]|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─parentId|string|父权限ID|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─sort|int32|排序|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─icon|string|图标|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─path|string|前端路由路径|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─component|string|前端组件路径|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─isHidden|boolean|是否隐藏|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─children|array|子权限列表|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "m5gtj3",
  "success": true,
  "data": {
    "roleId": "124",
    "code": "12367",
    "name": "giovanna.kshlerin",
    "description": "6jvlzi",
    "enabled": true,
    "permissionIds": [
      "3frv19"
    ],
    "permissions": [
      {
        "permissionId": "124",
        "code": "12367",
        "name": "giovanna.kshlerin",
        "type": "DIRECTORY",
        "parentId": "124",
        "sort": 373,
        "icon": "lhvd8w",
        "path": "9ajhok",
        "component": "jzocza",
        "isHidden": true,
        "children": [
          {
            "$ref": ".."
          }
        ]
      }
    ]
  }
}
```

### 根据编码查询角色信息
**URL:** http://localhost:9010/kaleido-admin/role/code/{code}

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 根据编码查询角色信息

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|code|string|true|角色编码|-|

**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/role/code/{code}
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|object|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─roleId|string|角色ID|-|
|└─code|string|角色编码|-|
|└─name|string|角色名称|-|
|└─description|string|角色描述|-|
|└─enabled|boolean|是否启用|-|
|└─permissionIds|array|权限ID列表|-|
|└─permissions|array|权限信息列表|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─permissionId|string|权限ID|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─code|string|权限编码|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─name|string|权限名称|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─type|enum|权限类型<br/>[Enum values:<br/>DIRECTORY("DIRECTORY", "目录")<br/>MENU("MENU", "菜单")<br/>BUTTON("BUTTON", "按钮")<br/>]|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─parentId|string|父权限ID|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─sort|int32|排序|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─icon|string|图标|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─path|string|前端路由路径|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─component|string|前端组件路径|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─isHidden|boolean|是否隐藏|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─children|array|子权限列表|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "jdtfdd",
  "success": true,
  "data": {
    "roleId": "124",
    "code": "12367",
    "name": "giovanna.kshlerin",
    "description": "opa7b1",
    "enabled": true,
    "permissionIds": [
      "6nriw9"
    ],
    "permissions": [
      {
        "permissionId": "124",
        "code": "12367",
        "name": "giovanna.kshlerin",
        "type": "DIRECTORY",
        "parentId": "124",
        "sort": 583,
        "icon": "yptt6b",
        "path": "j6j3z9",
        "component": "0o8dsj",
        "isHidden": true,
        "children": [
          {
            "$ref": ".."
          }
        ]
      }
    ]
  }
}
```

### 获取角色列表
**URL:** http://localhost:9010/kaleido-admin/role/list

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 获取角色列表

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/role/list
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|array|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─roleId|string|角色ID|-|
|└─code|string|角色编码|-|
|└─name|string|角色名称|-|
|└─description|string|角色描述|-|
|└─enabled|boolean|是否启用|-|
|└─permissionIds|array|权限ID列表|-|
|└─permissions|array|权限信息列表|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─permissionId|string|权限ID|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─code|string|权限编码|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─name|string|权限名称|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─type|enum|权限类型<br/>[Enum values:<br/>DIRECTORY("DIRECTORY", "目录")<br/>MENU("MENU", "菜单")<br/>BUTTON("BUTTON", "按钮")<br/>]|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─parentId|string|父权限ID|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─sort|int32|排序|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─icon|string|图标|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─path|string|前端路由路径|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─component|string|前端组件路径|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─isHidden|boolean|是否隐藏|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─children|array|子权限列表|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "2f4xrj",
  "success": true,
  "data": [
    {
      "roleId": "124",
      "code": "12367",
      "name": "giovanna.kshlerin",
      "description": "0ztmxy",
      "enabled": true,
      "permissionIds": [
        "n6x8rx"
      ],
      "permissions": [
        {
          "permissionId": "124",
          "code": "12367",
          "name": "giovanna.kshlerin",
          "type": "DIRECTORY",
          "parentId": "124",
          "sort": 248,
          "icon": "xu3zpb",
          "path": "sxfsol",
          "component": "qwvb5e",
          "isHidden": true,
          "children": [
            {
              "$ref": ".."
            }
          ]
        }
      ]
    }
  ]
}
```

## 用户管理API（管理后台）
### 根据用户ID查询用户信息
**URL:** http://localhost:9010/kaleido-admin/admin/user/{userId}

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 根据用户ID查询用户信息

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|userId|string|true|用户ID|-|

**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin/user/{userId}
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|object|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─userId|string|用户ID|-|
|└─telephone|string|手机号|-|
|└─nickName|string|昵称|-|
|└─status|string|用户状态|-|
|└─inviteCode|string|邀请码|-|
|└─inviterId|string|邀请人ID|-|
|└─lastLoginTime|string|最后登录时间|-|
|└─avatar|string|头像URL|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "ad5ysn",
  "success": true,
  "data": {
    "userId": "124",
    "telephone": "251.775.6385",
    "nickName": "dena.rowe",
    "status": "kybh5q",
    "inviteCode": "12367",
    "inviterId": "124",
    "lastLoginTime": "2026-02-12 19:01:16",
    "avatar": "dmkbis"
  }
}
```

### 根据手机号查询用户信息
**URL:** http://localhost:9010/kaleido-admin/admin/user/by-telephone/{telephone}

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 根据手机号查询用户信息

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|telephone|string|true|手机号|-|

**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin/user/by-telephone/{telephone}
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|object|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─userId|string|用户ID|-|
|└─telephone|string|手机号|-|
|└─nickName|string|昵称|-|
|└─status|string|用户状态|-|
|└─inviteCode|string|邀请码|-|
|└─inviterId|string|邀请人ID|-|
|└─lastLoginTime|string|最后登录时间|-|
|└─avatar|string|头像URL|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "f8pw1n",
  "success": true,
  "data": {
    "userId": "124",
    "telephone": "251.775.6385",
    "nickName": "dena.rowe",
    "status": "3a8yfw",
    "inviteCode": "12367",
    "inviterId": "124",
    "lastLoginTime": "2026-02-12 19:01:16",
    "avatar": "m9h5gx"
  }
}
```

### 冻结用户
**URL:** http://localhost:9010/kaleido-admin/admin/user/{userId}/freeze

**Type:** PUT

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 冻结用户

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|userId|string|true|用户ID，不能为空|-|

**Request-example:**
```bash
curl -X PUT -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin/user/{userId}/freeze
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
  "msg": "5t4opp",
  "success": true
}
```

### 解冻用户
**URL:** http://localhost:9010/kaleido-admin/admin/user/{userId}/unfreeze

**Type:** PUT

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 解冻用户

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|userId|string|true|用户ID，不能为空|-|

**Request-example:**
```bash
curl -X PUT -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin/user/{userId}/unfreeze
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
  "msg": "e97bu1",
  "success": true
}
```

### 删除用户（软删除）
**URL:** http://localhost:9010/kaleido-admin/admin/user/{userId}

**Type:** DELETE

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 删除用户（软删除）

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|userId|string|true|用户ID，不能为空|-|

**Request-example:**
```bash
curl -X DELETE -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin/user/{userId}
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
  "msg": "ybqx73",
  "success": true
}
```

### 分页查询用户列表
**URL:** http://localhost:9010/kaleido-admin/admin/user/page

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 分页查询用户列表

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Query-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|pageNum|int32|false|页码|-|
|pageSize|int32|false|分页大小|-|
|nickName|string|false|No comments found.|-|
|telephone|string|false|No comments found.|-|
|status|int32|false|No comments found.|-|
|gender|enum|false|No comments found.<br/>[Enum values:<br/>MALE()<br/>FEMALE()<br/>]|-|

**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:9010/kaleido-admin/admin/user/page?pageNum=2&pageSize=10&nickName=dena.rowe&telephone=251.775.6385&status=583&gender=MALE
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|object|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─total|int64|No comments found.|-|
|└─list|array|No comments found.|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─userId|string|用户ID|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─telephone|string|手机号|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─nickName|string|昵称|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─status|string|用户状态|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─inviteCode|string|邀请码|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─inviterId|string|邀请人ID|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─lastLoginTime|string|最后登录时间|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─avatar|string|头像URL|-|
|└─pageNum|int32|当前页|-|
|└─pageSize|int32|每页的数量|-|
|└─size|int32|当前页的数量|-|
|└─startRow|int64|由于startRow和endRow不常用，这里说个具体的用法<br/>可以在页面中"显示startRow到endRow 共size条数据"<br/>当前页面第一个元素在数据库中的行号|-|
|└─endRow|int64|当前页面最后一个元素在数据库中的行号|-|
|└─pages|int32|总页数|-|
|└─prePage|int32|前一页|-|
|└─nextPage|int32|下一页|-|
|└─firstPage|boolean|是否为第一页|-|
|└─lastPage|boolean|是否为最后一页|-|
|└─hasPreviousPage|boolean|是否有前一页|-|
|└─hasNextPage|boolean|是否有下一页|-|
|└─navigatePages|int32|导航页码数|-|
|└─navigatepageNums|array|所有导航页号|-|
|└─navigateFirstPage|int32|导航条上的第一页|-|
|└─navigateLastPage|int32|导航条上的最后一页|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "58l4fn",
  "success": true,
  "data": {
    "total": 709,
    "list": [
      {
        "userId": "124",
        "telephone": "251.775.6385",
        "nickName": "dena.rowe",
        "status": "jddt82",
        "inviteCode": "12367",
        "inviterId": "124",
        "lastLoginTime": "2026-02-12 19:01:17",
        "avatar": "plhsyx"
      }
    ],
    "pageNum": 905,
    "pageSize": 10,
    "size": 10,
    "startRow": 495,
    "endRow": 811,
    "pages": 223,
    "prePage": 1,
    "nextPage": 1,
    "firstPage": true,
    "lastPage": true,
    "hasPreviousPage": true,
    "hasNextPage": true,
    "navigatePages": 156,
    "navigatepageNums": [
      673
    ],
    "navigateFirstPage": 1,
    "navigateLastPage": 1
  }
}
```


