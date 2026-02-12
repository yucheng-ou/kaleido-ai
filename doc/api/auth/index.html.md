# kaleido-auth API 文档

| Version | Update Time | Status | Author | Description |
|---------|-------------|--------|--------|-------------|
|v2026-02-12 18:51:00|2026-02-12 18:51:00|auto|@Administrator|Created by smart-doc|



## 管理员认证API
### 注册管理员
**URL:** http://localhost:8081/public/admin/register

**Type:** POST

**Author:** ouyucheng

**Content-Type:** application/json

**Description:** 注册管理员

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|mobile|string|true|手机号<br/>Validate[regexp: ^1[3-9]\\d{9}$; ]|-|
|verifyCode|string|true|短信验证码<br/>Validate[max: 8; ]|-|

**Request-example:**
```bash
curl -X POST -H 'Content-Type: application/json' -H 'Authorization:Bearer ' -i http://localhost:8081/public/admin/register --data '{
  "mobile": "1-425-254-1688",
  "verifyCode": "12367"
}'
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|object|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─userId|string|用户ID|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "a73iiv",
  "success": true,
  "data": {
    "userId": "124"
  }
}
```

### 管理员登录
**URL:** http://localhost:8081/public/admin/login

**Type:** POST

**Author:** ouyucheng

**Content-Type:** application/json

**Description:** 管理员登录

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|mobile|string|true|手机号<br/>Validate[regexp: ^1[3-9]\\d{9}$; ]|-|
|verifyCode|string|true|短信验证码<br/>Validate[max: 8; ]|-|

**Request-example:**
```bash
curl -X POST -H 'Content-Type: application/json' -H 'Authorization:Bearer ' -i http://localhost:8081/public/admin/login --data '{
  "mobile": "1-425-254-1688",
  "verifyCode": "12367"
}'
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|object|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─userId|string|用户ID|-|
|└─token|string|认证令牌|-|
|└─userInfo|object|用户信息|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─adminId|string|管理员ID|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─realName|string|真实姓名|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─mobile|string|手机号|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─status|string|管理员状态|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─lastLoginTime|string|最后登录时间|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─roleIds|array|角色ID列表|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "o9y0rd",
  "success": true,
  "data": {
    "userId": "124",
    "token": "qfsysc",
    "userInfo": {
      "adminId": "124",
      "realName": "giovanna.kshlerin",
      "mobile": "1-425-254-1688",
      "status": "i07mh9",
      "lastLoginTime": "2026-02-12 18:53:36",
      "roleIds": [
        "vpov0u"
      ]
    }
  }
}
```

## 短信验证码API
### 发送短信验证码
**URL:** http://localhost:8081/public/sms/verify-code

**Type:** POST

**Author:** ouyucheng

**Content-Type:** application/json

**Description:** 发送短信验证码

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|mobile|string|true|手机号<br/>Validate[regexp: ^1[3-9]\\d{9}$; ]|-|
|targetType|enum|true|推送目标类型<br/>[Enum values:<br/>USER("普通用户")<br/>ADMIN("管理员")<br/>]|-|

**Request-example:**
```bash
curl -X POST -H 'Content-Type: application/json' -H 'Authorization:Bearer ' -i http://localhost:8081/public/sms/verify-code --data '{
  "mobile": "1-425-254-1688",
  "targetType": "USER"
}'
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|object|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─mobile|string|手机号|-|
|└─code|string|验证码（测试环境返回）|-|
|└─sendTime|string|发送时间|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "06lt86",
  "success": true,
  "data": {
    "mobile": "1-425-254-1688",
    "code": "12367",
    "sendTime": "2026-02-12"
  }
}
```

## 普通用户认证API
### 用户注册
**URL:** http://localhost:8081/public/user/register

**Type:** POST

**Author:** ouyucheng

**Content-Type:** application/json

**Description:** 用户注册

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|telephone|string|true|手机号<br/>Validate[regexp: ^1[3-9]\\d{9}$; ]|-|
|verificationCode|string|true|短信验证码<br/>Validate[max: 8; ]|-|
|inviterCode|string|false|邀请码（可选）<br/>Validate[max: 20; ]|-|

**Request-example:**
```bash
curl -X POST -H 'Content-Type: application/json' -H 'Authorization:Bearer ' -i http://localhost:8081/public/user/register --data '{
  "telephone": "251.775.6385",
  "verificationCode": "12367",
  "inviterCode": "12367"
}'
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|object|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─userId|string|用户ID|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "mp1vu8",
  "success": true,
  "data": {
    "userId": "124"
  }
}
```

### 用户登录
**URL:** http://localhost:8081/public/user/login

**Type:** POST

**Author:** ouyucheng

**Content-Type:** application/json

**Description:** 用户登录

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|mobile|string|true|手机号<br/>Validate[regexp: ^1[3-9]\\d{9}$; ]|-|
|verifyCode|string|true|短信验证码<br/>Validate[max: 8; ]|-|

**Request-example:**
```bash
curl -X POST -H 'Content-Type: application/json' -H 'Authorization:Bearer ' -i http://localhost:8081/public/user/login --data '{
  "mobile": "1-425-254-1688",
  "verifyCode": "12367"
}'
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|object|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─userId|string|用户ID|-|
|└─token|string|认证令牌|-|
|└─userInfo|object|用户信息|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─userId|string|用户ID|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─telephone|string|手机号|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─nickName|string|昵称|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─status|string|用户状态|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─inviteCode|string|邀请码|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─inviterId|string|邀请人ID|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─lastLoginTime|string|最后登录时间|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─avatar|string|头像URL|-|

**Response-example:**
```json
{
  "code": "12367",
  "msg": "zfivbj",
  "success": true,
  "data": {
    "userId": "124",
    "token": "0rhvm7",
    "userInfo": {
      "userId": "124",
      "telephone": "251.775.6385",
      "nickName": "dena.rowe",
      "status": "zjbysn",
      "inviteCode": "12367",
      "inviterId": "124",
      "lastLoginTime": "2026-02-12 18:53:36",
      "avatar": "g5dhaf"
    }
  }
}
```


