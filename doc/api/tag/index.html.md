# kaleido-tag API 文档

| Version | Update Time | Status | Author | Description |
|---------|-------------|--------|--------|-------------|
|v2026-02-12 18:51:00|2026-02-12 18:51:00|auto|@Administrator|Created by smart-doc|



## 标签API控制器
### 创建标签
**URL:** http://localhost:8082/tag

**Type:** POST

**Author:** ouyucheng

**Content-Type:** application/json

**Description:** 创建标签

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|name|string|true|标签名称<br/>Validate[max: 50; ]|-|
|typeCode|string|true|标签类型编码<br/>关联字典表t_dict.dict_code，字典类型为TAG_TYPE<br/>Validate[max: 50; ]|-|
|color|string|false|标签颜色<br/>Validate[max: 20; ]|-|
|description|string|false|标签描述<br/>Validate[max: 200; ]|-|

**Request-example:**
```bash
curl -X POST -H 'Content-Type: application/json' -H 'Authorization:Bearer ' -i http://localhost:8082/tag --data '{
  "name": "giovanna.kshlerin",
  "typeCode": "12367",
  "color": "wc8yqn",
  "description": "3lhzcm"
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
  "msg": "xc2yf7",
  "success": true
}
```

### 更新标签
**URL:** http://localhost:8082/tag/{tagId}

**Type:** PUT

**Author:** ouyucheng

**Content-Type:** application/json

**Description:** 更新标签

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|tagId|string|true|  标签ID，不能为空|-|

**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|name|string|true|标签名称<br/>Validate[max: 50; ]|-|
|color|string|false|标签颜色<br/>Validate[max: 20; ]|-|
|description|string|false|标签描述<br/>Validate[max: 200; ]|-|

**Request-example:**
```bash
curl -X PUT -H 'Content-Type: application/json' -H 'Authorization:Bearer ' -i http://localhost:8082/tag/{tagId} --data '{
  "name": "giovanna.kshlerin",
  "color": "54wvfd",
  "description": "jnwyq5"
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
  "msg": "r565fk",
  "success": true
}
```

### 根据ID查询标签
**URL:** http://localhost:8082/tag/{tagId}

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 根据ID查询标签

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|tagId|string|true|标签ID，不能为空|-|

**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:8082/tag/{tagId}
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|object|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─tagId|string|标签ID|-|
|└─userId|string|用户ID|-|
|└─name|string|标签名称|-|
|└─typeCode|string|标签类型编码|-|
|└─color|string|标签颜色|-|
|└─description|string|标签描述|-|
|└─usageCount|int32|使用次数|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "4xwtfp",
  "success": true,
  "data": {
    "tagId": "124",
    "userId": "124",
    "name": "giovanna.kshlerin",
    "typeCode": "12367",
    "color": "g8cbjn",
    "description": "beroth",
    "usageCount": 37
  }
}
```

### 查询标签列表
**URL:** http://localhost:8082/tag/list

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 查询标签列表

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Query-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|typeCode|string|true|标签类型编码，不能为空|-|

**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:8082/tag/list?typeCode=12367
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|array|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─tagId|string|标签ID|-|
|└─userId|string|用户ID|-|
|└─name|string|标签名称|-|
|└─typeCode|string|标签类型编码|-|
|└─color|string|标签颜色|-|
|└─description|string|标签描述|-|
|└─usageCount|int32|使用次数|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "ssvlx5",
  "success": true,
  "data": [
    {
      "tagId": "124",
      "userId": "124",
      "name": "giovanna.kshlerin",
      "typeCode": "12367",
      "color": "9xwh5s",
      "description": "ni96se",
      "usageCount": 620
    }
  ]
}
```


