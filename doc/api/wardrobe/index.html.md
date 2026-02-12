# kaleido-wardrobe API 文档

| Version | Update Time | Status | Author | Description |
|---------|-------------|--------|--------|-------------|
|v2026-02-12 18:51:00|2026-02-12 18:51:00|auto|@Administrator|Created by smart-doc|



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
  "code": "12367",
  "msg": "mfd38r",
  "success": true,
  "data": [
    {
      "brandId": "124",
      "name": "giovanna.kshlerin",
      "logoPath": "gxupnv",
      "description": "5zdq1j"
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
  "code": "12367",
  "msg": "vvx4h7",
  "success": true,
  "data": {
    "brandId": "124",
    "name": "giovanna.kshlerin",
    "logoPath": "44m9l0",
    "description": "m8x0fu"
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
  "name": "giovanna.kshlerin",
  "typeCode": "12367",
  "colorCode": "12367",
  "seasonCode": "12367",
  "brandId": "124",
  "size": "0n4d8z",
  "purchaseDate": "2026-02-12",
  "price": 998,
  "description": "t0tqyb",
  "currentLocationId": "124",
  "images": [
    {
      "path": "nreopx",
      "imageOrder": 2,
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
  "code": "12367",
  "msg": "g592gk",
  "success": true,
  "data": "614dpk"
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
  "clothingId": "124",
  "name": "giovanna.kshlerin",
  "typeCode": "12367",
  "colorCode": "12367",
  "seasonCode": "12367",
  "brandId": "124",
  "size": "z0plc7",
  "purchaseDate": "2026-02-12",
  "price": 723,
  "description": "pkmb7s",
  "currentLocationId": "124",
  "images": [
    {
      "path": "sc9xng",
      "imageOrder": 855,
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
  "code": "12367",
  "msg": "b6burt",
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
  "code": "12367",
  "msg": "uzwb66",
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
  "code": "12367",
  "msg": "97tzxg",
  "success": true,
  "data": [
    {
      "clothingId": "124",
      "userId": "124",
      "name": "giovanna.kshlerin",
      "typeCode": "12367",
      "colorCode": "12367",
      "seasonCode": "12367",
      "brandId": "124",
      "brandName": "giovanna.kshlerin",
      "size": "k1lr4v",
      "purchaseDate": "2026-02-12",
      "price": 349,
      "description": "aviscs",
      "currentLocationId": "124",
      "currentLocationName": "giovanna.kshlerin",
      "wearCount": 293,
      "lastWornDate": "2026-02-12",
      "images": [
        {
          "imageId": "124",
          "path": "taldjj",
          "imageOrder": 376,
          "isMain": true,
          "width": 483,
          "height": 623,
          "fileSize": 187,
          "imageTypeEnums": "JPEG",
          "description": "hdxw0n"
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
  "code": "12367",
  "msg": "kc63pf",
  "success": true,
  "data": {
    "clothingId": "124",
    "userId": "124",
    "name": "giovanna.kshlerin",
    "typeCode": "12367",
    "colorCode": "12367",
    "seasonCode": "12367",
    "brandId": "124",
    "brandName": "giovanna.kshlerin",
    "size": "tziudi",
    "purchaseDate": "2026-02-12",
    "price": 583,
    "description": "4r87fn",
    "currentLocationId": "124",
    "currentLocationName": "giovanna.kshlerin",
    "wearCount": 313,
    "lastWornDate": "2026-02-12",
    "images": [
      {
        "imageId": "124",
        "path": "agd8g0",
        "imageOrder": 449,
        "isMain": true,
        "width": 326,
        "height": 880,
        "fileSize": 677,
        "imageTypeEnums": "JPEG",
        "description": "njztge"
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
  "code": "12367",
  "msg": "emzfed",
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
  "code": "12367",
  "msg": "2wmm36",
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
  "name": "giovanna.kshlerin",
  "description": "0mwap6",
  "address": "Apt. 419 2515 Chaya Forges， Deniceburgh， VT 17003",
  "images": [
    {
      "path": "dtwsf8",
      "imageOrder": 794,
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
  "code": "12367",
  "msg": "pgpmz4",
  "success": true,
  "data": "f3x2nw"
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
  "locationId": "124",
  "name": "giovanna.kshlerin",
  "description": "hr8o1u",
  "address": "Apt. 419 2515 Chaya Forges， Deniceburgh， VT 17003",
  "images": [
    {
      "path": "ik7ngx",
      "imageOrder": 46,
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
  "code": "12367",
  "msg": "58gdcd",
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
  "code": "12367",
  "msg": "6s79cj",
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
  "code": "12367",
  "msg": "2v570i",
  "success": true,
  "data": [
    {
      "locationId": "124",
      "userId": "124",
      "name": "giovanna.kshlerin",
      "description": "b3fzsk",
      "address": "Apt. 419 2515 Chaya Forges， Deniceburgh， VT 17003",
      "primaryImageId": "124",
      "images": [
        {
          "imageId": "124",
          "path": "mhikdl",
          "imageOrder": 907,
          "isPrimary": true,
          "width": 188,
          "height": 809,
          "fileSize": 842,
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
  "code": "12367",
  "msg": "sg0s75",
  "success": true,
  "data": {
    "locationId": "124",
    "userId": "124",
    "name": "giovanna.kshlerin",
    "description": "85khjr",
    "address": "Apt. 419 2515 Chaya Forges， Deniceburgh， VT 17003",
    "primaryImageId": "124",
    "images": [
      {
        "imageId": "124",
        "path": "0vr1i4",
        "imageOrder": 20,
        "isPrimary": true,
        "width": 78,
        "height": 205,
        "fileSize": 373,
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
  "code": "12367",
  "msg": "qj1s7z",
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
  "code": "12367",
  "msg": "2z3rzj",
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
  "name": "giovanna.kshlerin",
  "description": "8mi1d1",
  "clothingIds": [
    "3fxzkz"
  ],
  "images": [
    {
      "path": "glnr0l",
      "imageOrder": 625,
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
  "code": "12367",
  "msg": "gnh1hu",
  "success": true,
  "data": "qg4hi4"
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
  "outfitId": "124",
  "name": "giovanna.kshlerin",
  "description": "wfdmal",
  "clothingIds": [
    "mwlzyg"
  ],
  "images": [
    {
      "path": "xwug96",
      "imageOrder": 555,
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
  "code": "12367",
  "msg": "3p0eg1",
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
curl -X DELETE -H 'Authorization:Bearer ' -i http://localhost:8082/wardrobe/outfits/{outfitId}?userId=124
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
  "msg": "7krzrb",
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
  "outfitId": "124",
  "wearDate": "2026-02-12",
  "notes": "a2ur35"
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
  "msg": "8ei05c",
  "success": true
}
```

### 查询用户穿搭列表
**URL:** http://localhost:8082/wardrobe/outfits/list

**Type:** GET

**Author:** ouyucheng

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 查询用户穿搭列表

**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
|Authorization|string|false|认证令牌，格式：Bearer {token}。仅需要认证的接口使用。|-|


**Request-example:**
```bash
curl -X GET -H 'Authorization:Bearer ' -i http://localhost:8082/wardrobe/outfits/list
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
  "code": "12367",
  "msg": "apyev7",
  "success": true,
  "data": [
    {
      "outfitId": "124",
      "userId": "124",
      "name": "giovanna.kshlerin",
      "description": "o8mbqx",
      "wearCount": 459,
      "lastWornDate": "2026-02-12",
      "clothings": [
        {
          "clothingId": "124",
          "name": "giovanna.kshlerin",
          "typeCode": "12367",
          "colorCode": "12367",
          "brandName": "giovanna.kshlerin",
          "primaryImagePath": "64o5o5"
        }
      ],
      "images": [
        {
          "imageId": "124",
          "path": "6a88k6",
          "imageOrder": 116,
          "isPrimary": true,
          "width": 399,
          "height": 404,
          "fileSize": 44,
          "imageTypeEnums": "JPEG",
          "description": "wz72vu"
        }
      ],
      "wearRecords": [
        {
          "recordId": "124",
          "userId": "124",
          "outfitId": "124",
          "wearDate": "2026-02-12",
          "notes": "r8qe51"
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
  "code": "12367",
  "msg": "6i63yv",
  "success": true,
  "data": {
    "outfitId": "124",
    "userId": "124",
    "name": "giovanna.kshlerin",
    "description": "f1zi0y",
    "wearCount": 297,
    "lastWornDate": "2026-02-12",
    "clothings": [
      {
        "clothingId": "124",
        "name": "giovanna.kshlerin",
        "typeCode": "12367",
        "colorCode": "12367",
        "brandName": "giovanna.kshlerin",
        "primaryImagePath": "h903jn"
      }
    ],
    "images": [
      {
        "imageId": "124",
        "path": "sqwwbm",
        "imageOrder": 987,
        "isPrimary": true,
        "width": 9,
        "height": 936,
        "fileSize": 144,
        "imageTypeEnums": "JPEG",
        "description": "cpjkd5"
      }
    ],
    "wearRecords": [
      {
        "recordId": "124",
        "userId": "124",
        "outfitId": "124",
        "wearDate": "2026-02-12",
        "notes": "gcgwgv"
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
  "code": "12367",
  "msg": "w7wyqx",
  "success": true,
  "data": [
    {
      "recordId": "124",
      "userId": "124",
      "outfitId": "124",
      "wearDate": "2026-02-12",
      "notes": "hyrp7m"
    }
  ]
}
```


