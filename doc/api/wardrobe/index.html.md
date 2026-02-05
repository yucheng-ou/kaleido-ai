# kaleido-wardrobe API 文档

| Version | Update Time | Status | Author | Description |
|---------|-------------|--------|--------|-------------|
|v2026-01-23 10:46:43|2026-01-23 10:46:43|auto|@ouyucheng|Created by smart-doc|



## 品牌API控制器（普通用户使用）
只提供读操作接口，写操作由管理员在Admin模块处理
### 查询所有品牌列表
**URL:** http://localhost:8082/wardrobe/brand

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
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:8082/wardrobe/brand
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
  "code": "40521",
  "msg": "pdj2ht",
  "success": true,
  "data": [
    {
      "brandId": "76",
      "name": "elwood.donnelly",
      "logoPath": "opyoei",
      "description": "2fm37l"
    }
  ]
}
```

### 根据ID查询品牌详情
**URL:** http://localhost:8082/wardrobe/brand/{brandId}

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
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:8082/wardrobe/brand/{brandId}
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
  "code": "40521",
  "msg": "1zmwbp",
  "success": true,
  "data": {
    "brandId": "76",
    "name": "elwood.donnelly",
    "logoPath": "0fvfco",
    "description": "3e9ra1"
  }
}
```

## 服装API控制器
### 创建服装（包含图片）
**URL:** http://localhost:8082/wardrobe/clothing

**Type:** POST

**Author:** ouyucheng

**Content-Type:** application/json

**Description:** 创建服装（包含图片）

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|name|string|true|服装名称<br/>Validate[max: 100; ]|-|
|typeCode|string|true|服装类型编码<br/>关联字典表t_dict.dict_code，字典类型为CLOTHING_TYPE|-|
|colorCode|string|false|颜色编码<br/>关联字典表t_dict.dict_code，字典类型为COLOR|-|
|seasonCode|string|false|季节编码<br/>关联字典表t_dict.dict_code，字典类型为SEASON|-|
|brandId|string|false|品牌ID<br/>关联品牌表t_wardrobe_brand|-|
|size|string|false|尺码|-|
|purchaseDate|string|false|购买日期|-|
|price|number|false|价格|-|
|description|string|false|描述<br/>Validate[max: 500; ]|-|
|currentLocationId|string|false|当前位置ID<br/>关联存储位置表t_wardrobe_storage_location|-|
|images|array|false|图片信息列表|-|
|└─path|string|true|图片路径（在MinIO中的文件路径）|-|
|└─imageOrder|int32|true|排序序号|-|
|└─isPrimary|boolean|true|是否为主图|-|

**Request-example:**
```bash
curl -X POST -H 'Content-Type: application/json' -H 'Authorization:Bearer ' -i http://localhost:8082/wardrobe/clothing --data '{
  "name": "elwood.donnelly",
  "typeCode": "40521",
  "colorCode": "40521",
  "seasonCode": "40521",
  "brandId": "76",
  "size": "xb3nnp",
  "purchaseDate": "2026-01-23",
  "price": 711,
  "description": "2s8iy0",
  "currentLocationId": "76",
  "images": [
    {
      "path": "8qyhyh",
      "imageOrder": 609,
      "isPrimary": true
    }
  ]
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
  "code": "40521",
  "msg": "zwhotf",
  "success": true,
  "data": "5cce0n"
}
```

### 更新服装信息（包含图片）
**URL:** http://localhost:8082/wardrobe/clothing/{clothingId}

**Type:** PUT

**Author:** ouyucheng

**Content-Type:** application/json

**Description:** 更新服装信息（包含图片）

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|clothingId|string|true|服装ID|-|
|name|string|true|服装名称<br/>Validate[max: 100; ]|-|
|typeCode|string|true|服装类型编码<br/>关联字典表t_dict.dict_code，字典类型为CLOTHING_TYPE|-|
|colorCode|string|false|颜色编码<br/>关联字典表t_dict.dict_code，字典类型为COLOR|-|
|seasonCode|string|false|季节编码<br/>关联字典表t_dict.dict_code，字典类型为SEASON|-|
|brandId|string|false|品牌ID<br/>关联品牌表t_wardrobe_brand|-|
|size|string|false|尺码|-|
|purchaseDate|string|false|购买日期|-|
|price|number|false|价格|-|
|description|string|false|描述<br/>Validate[max: 500; ]|-|
|currentLocationId|string|false|当前位置ID<br/>关联存储位置表t_wardrobe_storage_location|-|
|images|array|false|图片信息列表（更新后的完整图片列表）|-|
|└─path|string|true|图片路径（在MinIO中的文件路径）|-|
|└─imageOrder|int32|true|排序序号|-|
|└─isPrimary|boolean|true|是否为主图|-|

**Request-example:**
```bash
curl -X PUT -H 'Content-Type: application/json' -H 'Authorization:Bearer ' -i http://localhost:8082/wardrobe/clothing/{clothingId} --data '{
  "clothingId": "76",
  "name": "elwood.donnelly",
  "typeCode": "40521",
  "colorCode": "40521",
  "seasonCode": "40521",
  "brandId": "76",
  "size": "wro6eo",
  "purchaseDate": "2026-01-23",
  "price": 5,
  "description": "jnraya",
  "currentLocationId": "76",
  "images": [
    {
      "path": "q4q3y5",
      "imageOrder": 641,
      "isPrimary": true
    }
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
  "code": "40521",
  "msg": "vzq3oj",
  "success": true
}
```

### 删除服装
**URL:** http://localhost:8082/wardrobe/clothing/{clothingId}

**Type:** DELETE

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 删除服装

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|clothingId|string|true|服装ID，不能为空|-|

**Request-example:**
```bash
curl -X DELETE -H 'Authorization:Bearer ' -i http://localhost:8082/wardrobe/clothing/{clothingId}
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
  "code": "40521",
  "msg": "uvopuz",
  "success": true
}
```

### 查询用户服装列表
**URL:** http://localhost:8082/wardrobe/clothing/list

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 查询用户服装列表

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:8082/wardrobe/clothing/list
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|array|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─clothingId|string|服装ID|-|
|└─userId|string|用户ID|-|
|└─name|string|服装名称|-|
|└─typeCode|string|服装类型编码|-|
|└─colorCode|string|颜色编码|-|
|└─seasonCode|string|季节编码|-|
|└─brandId|string|品牌ID|-|
|└─brandName|string|品牌名称|-|
|└─size|string|尺码|-|
|└─purchaseDate|string|购买日期|-|
|└─price|number|价格|-|
|└─description|string|描述|-|
|└─currentLocationId|string|当前位置ID|-|
|└─currentLocationName|string|当前位置名称|-|
|└─wearCount|int32|穿着次数|-|
|└─lastWornDate|string|最后穿着日期|-|
|└─images|array|图片信息列表|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─imageId|string|图片ID|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─path|string|图片路径|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─imageOrder|int32|排序序号|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─isMain|boolean|是否为主图|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─width|int32|图片宽度|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─height|int32|图片高度|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─fileSize|int64|文件大小|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─imageTypeEnums|enum|图片类型<br/>[Enum values:<br/>JPEG("image/jpeg", "jpg", "jpeg")<br/>PNG("image/png", "png")<br/>GIF("image/gif", "gif")<br/>WEBP("image/webp", "webp")<br/>BMP("image/bmp", "bmp")<br/>SVG("image/svg+xml", "svg")<br/>UNKNOWN("application/octet-stream")<br/>]|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─description|string|图片描述|-|

**Response-example:**
```json
{
  "code": "40521",
  "msg": "oivvpd",
  "success": true,
  "data": [
    {
      "clothingId": "76",
      "userId": "76",
      "name": "elwood.donnelly",
      "typeCode": "40521",
      "colorCode": "40521",
      "seasonCode": "40521",
      "brandId": "76",
      "brandName": "elwood.donnelly",
      "size": "bi8cdm",
      "purchaseDate": "2026-01-23",
      "price": 706,
      "description": "3p572t",
      "currentLocationId": "76",
      "currentLocationName": "elwood.donnelly",
      "wearCount": 475,
      "lastWornDate": "2026-01-23",
      "images": [
        {
          "imageId": "76",
          "path": "wmewnx",
          "imageOrder": 697,
          "isMain": true,
          "width": 241,
          "height": 176,
          "fileSize": 8,
          "imageTypeEnums": "JPEG",
          "description": "gwewiz"
        }
      ]
    }
  ]
}
```

### 根据ID查询服装详情
**URL:** http://localhost:8082/wardrobe/clothing/{clothingId}

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 根据ID查询服装详情

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|clothingId|string|true|服装ID，不能为空|-|

**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:8082/wardrobe/clothing/{clothingId}
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|object|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─clothingId|string|服装ID|-|
|└─userId|string|用户ID|-|
|└─name|string|服装名称|-|
|└─typeCode|string|服装类型编码|-|
|└─colorCode|string|颜色编码|-|
|└─seasonCode|string|季节编码|-|
|└─brandId|string|品牌ID|-|
|└─brandName|string|品牌名称|-|
|└─size|string|尺码|-|
|└─purchaseDate|string|购买日期|-|
|└─price|number|价格|-|
|└─description|string|描述|-|
|└─currentLocationId|string|当前位置ID|-|
|└─currentLocationName|string|当前位置名称|-|
|└─wearCount|int32|穿着次数|-|
|└─lastWornDate|string|最后穿着日期|-|
|└─images|array|图片信息列表|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─imageId|string|图片ID|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─path|string|图片路径|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─imageOrder|int32|排序序号|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─isMain|boolean|是否为主图|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─width|int32|图片宽度|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─height|int32|图片高度|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─fileSize|int64|文件大小|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─imageTypeEnums|enum|图片类型<br/>[Enum values:<br/>JPEG("image/jpeg", "jpg", "jpeg")<br/>PNG("image/png", "png")<br/>GIF("image/gif", "gif")<br/>WEBP("image/webp", "webp")<br/>BMP("image/bmp", "bmp")<br/>SVG("image/svg+xml", "svg")<br/>UNKNOWN("application/octet-stream")<br/>]|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─description|string|图片描述|-|

**Response-example:**
```json
{
  "code": "40521",
  "msg": "a96fdv",
  "success": true,
  "data": {
    "clothingId": "76",
    "userId": "76",
    "name": "elwood.donnelly",
    "typeCode": "40521",
    "colorCode": "40521",
    "seasonCode": "40521",
    "brandId": "76",
    "brandName": "elwood.donnelly",
    "size": "g5lk0d",
    "purchaseDate": "2026-01-23",
    "price": 988,
    "description": "bggqgc",
    "currentLocationId": "76",
    "currentLocationName": "elwood.donnelly",
    "wearCount": 334,
    "lastWornDate": "2026-01-23",
    "images": [
      {
        "imageId": "76",
        "path": "gfbvti",
        "imageOrder": 443,
        "isMain": true,
        "width": 322,
        "height": 556,
        "fileSize": 947,
        "imageTypeEnums": "JPEG",
        "description": "vc29ln"
      }
    ]
  }
}
```

### 为服装添加标签
**URL:** http://localhost:8082/wardrobe/clothing/{clothingId}/tags/{tagId}

**Type:** POST

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 为服装添加标签

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|clothingId|string|true|服装ID，不能为空|-|
|tagId|string|true|     标签ID，不能为空|-|

**Request-example:**
```bash
curl -X POST -H 'Authorization:Bearer ' -i http://localhost:8082/wardrobe/clothing/{clothingId}/tags/{tagId}
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
  "code": "40521",
  "msg": "damxn2",
  "success": true
}
```

### 从服装移除标签
**URL:** http://localhost:8082/wardrobe/clothing/{clothingId}/tags/{tagId}

**Type:** DELETE

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 从服装移除标签

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|clothingId|string|true|服装ID，不能为空|-|
|tagId|string|true|     标签ID，不能为空|-|

**Request-example:**
```bash
curl -X DELETE -H 'Authorization:Bearer ' -i http://localhost:8082/wardrobe/clothing/{clothingId}/tags/{tagId}
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
  "code": "40521",
  "msg": "89qxtb",
  "success": true
}
```

## 位置API控制器
### 创建位置
**URL:** http://localhost:8082/wardrobe/location

**Type:** POST

**Author:** ouyucheng

**Content-Type:** application/json

**Description:** 创建位置

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|name|string|true|位置名称<br/>Validate[max: 100; ]|-|
|description|string|false|位置描述<br/>Validate[max: 500; ]|-|
|address|string|false|具体地址<br/>Validate[max: 500; ]|-|
|images|array|false|图片信息列表|-|
|└─path|string|true|图片路径（在MinIO中的文件路径）|-|
|└─imageOrder|int32|true|排序序号|-|
|└─isPrimary|boolean|true|是否为主图|-|

**Request-example:**
```bash
curl -X POST -H 'Content-Type: application/json' -H 'Authorization:Bearer ' -i http://localhost:8082/wardrobe/location --data '{
  "name": "elwood.donnelly",
  "description": "8absa2",
  "address": "028 Siobhan Manor， Riceport， OH 65289",
  "images": [
    {
      "path": "ta5xhk",
      "imageOrder": 477,
      "isPrimary": true
    }
  ]
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
  "code": "40521",
  "msg": "c69o9v",
  "success": true,
  "data": "rdf2vj"
}
```

### 更新位置信息
**URL:** http://localhost:8082/wardrobe/location/{locationId}

**Type:** PUT

**Author:** ouyucheng

**Content-Type:** application/json

**Description:** 更新位置信息

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|locationId|string|true|位置ID，不能为空|-|

**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|locationId|string|true|位置ID<br/>Validate[max: 50; ]|-|
|name|string|true|位置名称<br/>Validate[max: 100; ]|-|
|description|string|false|位置描述<br/>Validate[max: 500; ]|-|
|address|string|false|具体地址<br/>Validate[max: 500; ]|-|
|images|array|false|图片信息列表|-|
|└─path|string|true|图片路径（在MinIO中的文件路径）|-|
|└─imageOrder|int32|true|排序序号|-|
|└─isPrimary|boolean|true|是否为主图|-|

**Request-example:**
```bash
curl -X PUT -H 'Content-Type: application/json' -H 'Authorization:Bearer ' -i http://localhost:8082/wardrobe/location/{locationId} --data '{
  "locationId": "76",
  "name": "elwood.donnelly",
  "description": "e4h4qi",
  "address": "028 Siobhan Manor， Riceport， OH 65289",
  "images": [
    {
      "path": "tfznl2",
      "imageOrder": 886,
      "isPrimary": true
    }
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
  "code": "40521",
  "msg": "c0juqh",
  "success": true
}
```

### 删除位置（逻辑删除）
**URL:** http://localhost:8082/wardrobe/location/{locationId}

**Type:** DELETE

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 删除位置（逻辑删除）

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|locationId|string|true|位置ID，不能为空|-|

**Request-example:**
```bash
curl -X DELETE -H 'Authorization:Bearer ' -i http://localhost:8082/wardrobe/location/{locationId}
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
  "code": "40521",
  "msg": "q8aazk",
  "success": true
}
```

### 查询当前登录用户的位置列表
**URL:** http://localhost:8082/wardrobe/location

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 查询当前登录用户的位置列表

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:8082/wardrobe/location
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|array|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─locationId|string|位置ID|-|
|└─userId|string|用户ID|-|
|└─name|string|位置名称|-|
|└─description|string|位置描述|-|
|└─address|string|具体地址|-|
|└─primaryImageId|string|主图ID|-|
|└─images|array|图片列表|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─imageId|string|图片ID|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─path|string|图片路径（在minio中的文件路径）|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─imageOrder|int32|排序序号|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─isPrimary|boolean|是否为主图|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─width|int32|图片宽度|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─height|int32|图片高度|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─fileSize|int64|文件大小（字节）|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─imageTypeEnums|enum|图片类型<br/>[Enum values:<br/>JPEG("image/jpeg", "jpg", "jpeg")<br/>PNG("image/png", "png")<br/>GIF("image/gif", "gif")<br/>WEBP("image/webp", "webp")<br/>BMP("image/bmp", "bmp")<br/>SVG("image/svg+xml", "svg")<br/>UNKNOWN("application/octet-stream")<br/>]|-|

**Response-example:**
```json
{
  "code": "40521",
  "msg": "tgx3lj",
  "success": true,
  "data": [
    {
      "locationId": "76",
      "userId": "76",
      "name": "elwood.donnelly",
      "description": "azhptt",
      "address": "028 Siobhan Manor， Riceport， OH 65289",
      "primaryImageId": "76",
      "images": [
        {
          "imageId": "76",
          "path": "nm3nr4",
          "imageOrder": 760,
          "isPrimary": true,
          "width": 379,
          "height": 882,
          "fileSize": 946,
          "imageTypeEnums": "JPEG"
        }
      ]
    }
  ]
}
```

### 根据ID查询位置详情
**URL:** http://localhost:8082/wardrobe/location/{locationId}

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 根据ID查询位置详情

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|locationId|string|true|位置ID，不能为空|-|

**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:8082/wardrobe/location/{locationId}
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|object|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─locationId|string|位置ID|-|
|└─userId|string|用户ID|-|
|└─name|string|位置名称|-|
|└─description|string|位置描述|-|
|└─address|string|具体地址|-|
|└─primaryImageId|string|主图ID|-|
|└─images|array|图片列表|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─imageId|string|图片ID|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─path|string|图片路径（在minio中的文件路径）|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─imageOrder|int32|排序序号|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─isPrimary|boolean|是否为主图|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─width|int32|图片宽度|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─height|int32|图片高度|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─fileSize|int64|文件大小（字节）|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─imageTypeEnums|enum|图片类型<br/>[Enum values:<br/>JPEG("image/jpeg", "jpg", "jpeg")<br/>PNG("image/png", "png")<br/>GIF("image/gif", "gif")<br/>WEBP("image/webp", "webp")<br/>BMP("image/bmp", "bmp")<br/>SVG("image/svg+xml", "svg")<br/>UNKNOWN("application/octet-stream")<br/>]|-|

**Response-example:**
```json
{
  "code": "40521",
  "msg": "3sezov",
  "success": true,
  "data": {
    "locationId": "76",
    "userId": "76",
    "name": "elwood.donnelly",
    "description": "r6j6c2",
    "address": "028 Siobhan Manor， Riceport， OH 65289",
    "primaryImageId": "76",
    "images": [
      {
        "imageId": "76",
        "path": "e3bujg",
        "imageOrder": 726,
        "isPrimary": true,
        "width": 179,
        "height": 177,
        "fileSize": 685,
        "imageTypeEnums": "JPEG"
      }
    ]
  }
}
```

### 为位置添加标签
**URL:** http://localhost:8082/wardrobe/location/{locationId}/tags/{tagId}

**Type:** POST

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 为位置添加标签

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|locationId|string|true|位置ID，不能为空|-|
|tagId|string|true|     标签ID，不能为空|-|

**Request-example:**
```bash
curl -X POST -H 'Authorization:Bearer ' -i http://localhost:8082/wardrobe/location/{locationId}/tags/{tagId}
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
  "code": "40521",
  "msg": "rmcpcp",
  "success": true
}
```

### 从位置移除标签
**URL:** http://localhost:8082/wardrobe/location/{locationId}/tags/{tagId}

**Type:** DELETE

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 从位置移除标签

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|locationId|string|true|位置ID，不能为空|-|
|tagId|string|true|     标签ID，不能为空|-|

**Request-example:**
```bash
curl -X DELETE -H 'Authorization:Bearer ' -i http://localhost:8082/wardrobe/location/{locationId}/tags/{tagId}
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
  "code": "40521",
  "msg": "pajqoh",
  "success": true
}
```

## 穿搭API控制器
### 创建穿搭（包含服装和图片）
**URL:** http://localhost:8082/wardrobe/outfits

**Type:** POST

**Author:** ouyucheng

**Content-Type:** application/json

**Description:** 创建穿搭（包含服装和图片）

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|name|string|true|穿搭名称<br/>Validate[max: 100; ]|-|
|description|string|false|穿搭描述<br/>Validate[max: 500; ]|-|
|clothingIds|array|true|服装ID列表<br/>至少包含1件服装，最多20件<br/>Validate[max: 20; ]|-|
|images|array|true|图片信息列表<br/>Validate[max: 10; ]|-|
|└─path|string|true|图片路径（在MinIO中的文件路径）|-|
|└─imageOrder|int32|true|排序序号|-|
|└─isPrimary|boolean|true|是否为主图|-|

**Request-example:**
```bash
curl -X POST -H 'Content-Type: application/json' -H 'Authorization:Bearer ' -i http://localhost:8082/wardrobe/outfits --data '{
  "name": "elwood.donnelly",
  "description": "7xbmsz",
  "clothingIds": [
    "vppjiv"
  ],
  "images": [
    {
      "path": "4qfys7",
      "imageOrder": 784,
      "isPrimary": true
    }
  ]
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
  "code": "40521",
  "msg": "dry7xa",
  "success": true,
  "data": "4jynuo"
}
```

### 更新穿搭信息（包含服装和图片）
**URL:** http://localhost:8082/wardrobe/outfits/{outfitId}

**Type:** PUT

**Author:** ouyucheng

**Content-Type:** application/json

**Description:** 更新穿搭信息（包含服装和图片）

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|outfitId|string|true|穿搭ID，不能为空|-|

**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|outfitId|string|true|穿搭ID|-|
|name|string|true|穿搭名称<br/>Validate[max: 100; ]|-|
|description|string|false|穿搭描述<br/>Validate[max: 500; ]|-|
|clothingIds|array|true|服装ID列表<br/>至少包含1件服装，最多20件<br/>Validate[max: 20; ]|-|
|images|array|true|图片信息列表（更新后的完整图片列表）<br/>Validate[max: 10; ]|-|
|└─path|string|true|图片路径（在MinIO中的文件路径）|-|
|└─imageOrder|int32|true|排序序号|-|
|└─isPrimary|boolean|true|是否为主图|-|

**Request-example:**
```bash
curl -X PUT -H 'Content-Type: application/json' -H 'Authorization:Bearer ' -i http://localhost:8082/wardrobe/outfits/{outfitId} --data '{
  "outfitId": "76",
  "name": "elwood.donnelly",
  "description": "rqlovi",
  "clothingIds": [
    "m22nue"
  ],
  "images": [
    {
      "path": "1lnzli",
      "imageOrder": 858,
      "isPrimary": true
    }
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
  "code": "40521",
  "msg": "xz6064",
  "success": true
}
```

### 删除穿搭
**URL:** http://localhost:8082/wardrobe/outfits/{outfitId}

**Type:** DELETE

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 删除穿搭

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|outfitId|string|true|穿搭ID，不能为空|-|

**Query-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|userId|string|true|  用户ID，不能为空|-|

**Request-example:**
```bash
curl -X DELETE -H 'Authorization:Bearer ' -i http://localhost:8082/wardrobe/outfits/{outfitId}?userId=76
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
  "code": "40521",
  "msg": "y4brms",
  "success": true
}
```

### 记录穿搭穿着
**URL:** http://localhost:8082/wardrobe/outfits/{outfitId}/wear

**Type:** POST

**Author:** ouyucheng

**Content-Type:** application/json

**Description:** 记录穿搭穿着

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|outfitId|string|true|穿搭ID，不能为空|-|

**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|outfitId|string|false|No comments found.|-|
|wearDate|string|false|穿着日期（可选，默认为当前日期）|-|
|notes|string|false|备注<br/>Validate[max: 500; ]|-|

**Request-example:**
```bash
curl -X POST -H 'Content-Type: application/json' -H 'Authorization:Bearer ' -i http://localhost:8082/wardrobe/outfits/{outfitId}/wear --data '{
  "outfitId": "76",
  "wearDate": "2026-01-23",
  "notes": "p2a5xn"
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
  "code": "40521",
  "msg": "qa65cz",
  "success": true
}
```

### 根据用户ID查询穿搭列表
**URL:** http://localhost:8082/wardrobe/outfits/list

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 根据用户ID查询穿搭列表

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Query-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|userId|string|true|用户ID，不能为空|-|

**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:8082/wardrobe/outfits/list?userId=76
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|array|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─outfitId|string|穿搭ID|-|
|└─userId|string|用户ID|-|
|└─name|string|穿搭名称|-|
|└─description|string|穿搭描述|-|
|└─wearCount|int32|穿着次数|-|
|└─lastWornDate|string|最后穿着日期|-|
|└─clothings|array|服装信息列表|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─clothingId|string|服装ID|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─name|string|服装名称|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─typeCode|string|服装类型编码|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─colorCode|string|颜色编码|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─brandName|string|品牌名称|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─primaryImagePath|string|主图路径|-|
|└─images|array|图片信息列表|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─imageId|string|图片ID|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─path|string|图片路径（在minio中的文件路径）|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─imageOrder|int32|排序序号|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─isPrimary|boolean|是否为主图|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─width|int32|图片宽度|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─height|int32|图片高度|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─fileSize|int64|文件大小（字节）|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─imageTypeEnums|enum|图片类型<br/>[Enum values:<br/>JPEG("image/jpeg", "jpg", "jpeg")<br/>PNG("image/png", "png")<br/>GIF("image/gif", "gif")<br/>WEBP("image/webp", "webp")<br/>BMP("image/bmp", "bmp")<br/>SVG("image/svg+xml", "svg")<br/>UNKNOWN("application/octet-stream")<br/>]|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─description|string|图片描述|-|
|└─wearRecords|array|穿着记录列表|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─recordId|string|记录ID|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─userId|string|用户ID|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─outfitId|string|穿搭ID|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─wearDate|string|穿着日期|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─notes|string|备注|-|

**Response-example:**
```json
{
  "code": "40521",
  "msg": "0qfovo",
  "success": true,
  "data": [
    {
      "outfitId": "76",
      "userId": "76",
      "name": "elwood.donnelly",
      "description": "cfjyzk",
      "wearCount": 630,
      "lastWornDate": "2026-01-23",
      "clothings": [
        {
          "clothingId": "76",
          "name": "elwood.donnelly",
          "typeCode": "40521",
          "colorCode": "40521",
          "brandName": "elwood.donnelly",
          "primaryImagePath": "efuklp"
        }
      ],
      "images": [
        {
          "imageId": "76",
          "path": "mdkgkm",
          "imageOrder": 807,
          "isPrimary": true,
          "width": 63,
          "height": 414,
          "fileSize": 677,
          "imageTypeEnums": "JPEG",
          "description": "6t43aa"
        }
      ],
      "wearRecords": [
        {
          "recordId": "76",
          "userId": "76",
          "outfitId": "76",
          "wearDate": "2026-01-23",
          "notes": "hkxb0a"
        }
      ]
    }
  ]
}
```

### 根据ID查询穿搭详情
**URL:** http://localhost:8082/wardrobe/outfits/{outfitId}

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 根据ID查询穿搭详情

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|outfitId|string|true|穿搭ID，不能为空|-|

**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:8082/wardrobe/outfits/{outfitId}
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|object|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─outfitId|string|穿搭ID|-|
|└─userId|string|用户ID|-|
|└─name|string|穿搭名称|-|
|└─description|string|穿搭描述|-|
|└─wearCount|int32|穿着次数|-|
|└─lastWornDate|string|最后穿着日期|-|
|└─clothings|array|服装信息列表|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─clothingId|string|服装ID|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─name|string|服装名称|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─typeCode|string|服装类型编码|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─colorCode|string|颜色编码|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─brandName|string|品牌名称|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─primaryImagePath|string|主图路径|-|
|└─images|array|图片信息列表|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─imageId|string|图片ID|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─path|string|图片路径（在minio中的文件路径）|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─imageOrder|int32|排序序号|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─isPrimary|boolean|是否为主图|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─width|int32|图片宽度|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─height|int32|图片高度|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─fileSize|int64|文件大小（字节）|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─imageTypeEnums|enum|图片类型<br/>[Enum values:<br/>JPEG("image/jpeg", "jpg", "jpeg")<br/>PNG("image/png", "png")<br/>GIF("image/gif", "gif")<br/>WEBP("image/webp", "webp")<br/>BMP("image/bmp", "bmp")<br/>SVG("image/svg+xml", "svg")<br/>UNKNOWN("application/octet-stream")<br/>]|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─description|string|图片描述|-|
|└─wearRecords|array|穿着记录列表|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─recordId|string|记录ID|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─userId|string|用户ID|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─outfitId|string|穿搭ID|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─wearDate|string|穿着日期|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─notes|string|备注|-|

**Response-example:**
```json
{
  "code": "40521",
  "msg": "7repf7",
  "success": true,
  "data": {
    "outfitId": "76",
    "userId": "76",
    "name": "elwood.donnelly",
    "description": "v4djas",
    "wearCount": 676,
    "lastWornDate": "2026-01-23",
    "clothings": [
      {
        "clothingId": "76",
        "name": "elwood.donnelly",
        "typeCode": "40521",
        "colorCode": "40521",
        "brandName": "elwood.donnelly",
        "primaryImagePath": "e2drjk"
      }
    ],
    "images": [
      {
        "imageId": "76",
        "path": "npyn5q",
        "imageOrder": 636,
        "isPrimary": true,
        "width": 758,
        "height": 906,
        "fileSize": 152,
        "imageTypeEnums": "JPEG",
        "description": "5pb8ly"
      }
    ],
    "wearRecords": [
      {
        "recordId": "76",
        "userId": "76",
        "outfitId": "76",
        "wearDate": "2026-01-23",
        "notes": "iv8yde"
      }
    ]
  }
}
```

### 查询穿搭穿着记录
**URL:** http://localhost:8082/wardrobe/outfits/{outfitId}/records

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 查询穿搭穿着记录

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|outfitId|string|true|穿搭ID，不能为空|-|

**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:8082/wardrobe/outfits/{outfitId}/records
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|string|响应码<br/>200表示成功，其他表示失败|-|
|msg|string|响应消息<br/>成功时为'成功'，失败时为错误信息|-|
|success|boolean|是否成功<br/>true表示成功，false表示失败|-|
|data|array|响应数据<br/>成功时返回具体数据，失败时为null|-|
|└─recordId|string|记录ID|-|
|└─userId|string|用户ID|-|
|└─outfitId|string|穿搭ID|-|
|└─wearDate|string|穿着日期|-|
|└─notes|string|备注|-|

**Response-example:**
```json
{
  "code": "40521",
  "msg": "319101",
  "success": true,
  "data": [
    {
      "recordId": "76",
      "userId": "76",
      "outfitId": "76",
      "wearDate": "2026-01-23",
      "notes": "ett9jj"
    }
  ]
}
```


