# kaleido-recommend API 文档

| Version | Update Time | Status | Author | Description |
|---------|-------------|--------|--------|-------------|
|v2026-02-12 18:51:00|2026-02-12 18:51:00|auto|@Administrator|Created by smart-doc|



## 推荐API
### 创建推荐记录
**URL:** http://localhost:8082/recommend

**Type:** POST

**Author:** ouyucheng

**Content-Type:** application/json

**Description:** 创建推荐记录

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|prompt|string|true|用户输入的推荐需求提示词|-|

**Request-example:**
```bash
curl -X POST -H 'Content-Type: application/json' -H 'Authorization:Bearer ' -i http://localhost:8082/recommend --data '{
  "prompt": "7o9qf0"
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
  "msg": "y32qpx",
  "success": true,
  "data": "0o6fqw"
}
```

### 查询单个推荐记录详情
**URL:** http://localhost:8082/recommend/{recommendRecordId}

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 查询单个推荐记录详情

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|recommendRecordId|string|true|推荐记录ID|-|

**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:8082/recommend/{recommendRecordId}
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|object|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─id|string|推荐记录ID|-|
|└─userId|string|用户ID|-|
|└─prompt|string|用户输入的推荐需求提示词|-|
|└─outfitId|string|生成的穿搭ID（关联t_wardrobe_outfit）|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "xekvh0",
  "success": true,
  "data": {
    "id": "124",
    "userId": "124",
    "prompt": "40ia8z",
    "outfitId": "124"
  }
}
```

### 查询用户的推荐记录列表
**URL:** http://localhost:8082/recommend

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 查询用户的推荐记录列表

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:8082/recommend
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|array|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─id|string|推荐记录ID|-|
|└─userId|string|用户ID|-|
|└─prompt|string|用户输入的推荐需求提示词|-|
|└─outfitId|string|生成的穿搭ID（关联t_wardrobe_outfit）|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "ffam3n",
  "success": true,
  "data": [
    {
      "id": "124",
      "userId": "124",
      "prompt": "wsd9pi",
      "outfitId": "124"
    }
  ]
}
```


