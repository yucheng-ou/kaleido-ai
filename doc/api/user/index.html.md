# kaleido-user API 文档

| Version | Update Time | Status | Author | Description |
|---------|-------------|--------|--------|-------------|
|v2026-02-12 18:51:00|2026-02-12 18:51:00|auto|@Administrator|Created by smart-doc|



## 用户API
### 查询用户自身信息
**URL:** http://localhost:8082/user

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 查询用户自身信息

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:8082/user
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
  "msg": "eaqdhc",
  "success": true,
  "data": {
    "userId": "124",
    "telephone": "251.775.6385",
    "nickName": "dena.rowe",
    "status": "izjjw5",
    "inviteCode": "12367",
    "inviterId": "124",
    "lastLoginTime": "2026-02-12 18:54:22",
    "avatar": "6nh1jt"
  }
}
```

### 修改用户昵称
**URL:** http://localhost:8082/user/change-nickname

**Type:** PUT

**Author:** ouyucheng

**Content-Type:** application/json

**Description:** 修改用户昵称

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|userId|string|true|用户ID|-|
|nickName|string|true|新昵称<br/>Validate[max: 20; regexp: ^[\\u4e00-\\u9fa5a-zA-Z0-9]+$; ]|-|

**Request-example:**
```bash
curl -X PUT -H 'Content-Type: application/json' -H 'Authorization:Bearer ' -i http://localhost:8082/user/change-nickname --data '{
  "userId": "124",
  "nickName": "dena.rowe"
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
  "msg": "m791xk",
  "success": true
}
```

### 更新用户头像
**URL:** http://localhost:8082/user/avatar

**Type:** PUT

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 更新用户头像

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Query-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|avatarUrl|string|true|头像URL，不能为空|-|

**Request-example:**
```bash
curl -X PUT -H 'Authorization:Bearer ' -i http://localhost:8082/user/avatar
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
  "msg": "xfksne",
  "success": true
}
```

### 分页查询用户列表
**URL:** http://localhost:8082/user/page

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
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:8082/user/page?pageNum=469&pageSize=10&nickName=dena.rowe&telephone=251.775.6385&status=472&gender=MALE
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
  "msg": "26tw4l",
  "success": true,
  "data": {
    "total": 519,
    "list": [
      {
        "userId": "124",
        "telephone": "251.775.6385",
        "nickName": "dena.rowe",
        "status": "j5sgtb",
        "inviteCode": "12367",
        "inviterId": "124",
        "lastLoginTime": "2026-02-12 18:54:22",
        "avatar": "q4k6hk"
      }
    ],
    "pageNum": 917,
    "pageSize": 10,
    "size": 10,
    "startRow": 636,
    "endRow": 555,
    "pages": 672,
    "prePage": 1,
    "nextPage": 1,
    "firstPage": true,
    "lastPage": true,
    "hasPreviousPage": true,
    "hasNextPage": true,
    "navigatePages": 598,
    "navigatepageNums": [
      96
    ],
    "navigateFirstPage": 1,
    "navigateLastPage": 1
  }
}
```


