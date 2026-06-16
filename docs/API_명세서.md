# API 명세서

## 공통 사항

- Base URL: `/api`
- 데이터 형식: `application/json`
- 공통 응답 포맷 예시
```json
{
  "success": true,
  "data": { },
  "error": null
}
```
- 에러 응답 예시
```json
{
  "success": false,
  "data": null,
  "error": {
    "code": "PRODUCT_NOT_FOUND",
    "message": "해당 상품을 찾을 수 없습니다."
  }
}
```

---

## 1. 상품(Product) API

### 1.1 상품 등록

- **Method**: `POST`
- **URL**: `/api/products`
- **Request Body**

| 필드 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| name | String | O | 상품명 |
| price | Integer | O | 상품 가격 |
| stock | Integer | O | 재고 수량 |
| description | String | X | 상품 설명 |

**Request 예시**
```json
{
  "name": "무선 마우스",
  "price": 25000,
  "stock": 100,
  "description": "인체공학적 무선 마우스"
}
```

**Response 예시 (201 Created)**
```json
{
  "success": true,
  "data": {
    "id": 1,
    "name": "무선 마우스",
    "price": 25000,
    "stock": 100,
    "description": "인체공학적 무선 마우스",
    "createdAt": "2026-06-15T10:00:00",
    "updatedAt": "2026-06-15T10:00:00"
  },
  "error": null
}
```

| 응답 코드 | 설명 |
| --- | --- |
| 201 | 등록 성공 |
| 400 | 요청 값 유효성 오류 (필수 값 누락, 음수 가격 등) |

---

### 1.2 상품 단건 조회

- **Method**: `GET`
- **URL**: `/api/products/{productId}`

**Response 예시 (200 OK)**
```json
{
  "success": true,
  "data": {
    "id": 1,
    "name": "무선 마우스",
    "price": 25000,
    "stock": 100,
    "description": "인체공학적 무선 마우스",
    "createdAt": "2026-06-15T10:00:00",
    "updatedAt": "2026-06-15T10:00:00"
  },
  "error": null
}
```

| 응답 코드 | 설명 |
| --- | --- |
| 200 | 조회 성공 |
| 404 | 상품을 찾을 수 없음 (PRODUCT_NOT_FOUND) |

---

### 1.3 상품 목록 조회

- **Method**: `GET`
- **URL**: `/api/products`
- **Query Parameter** (선택)

| 파라미터 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| page | Integer | X | 페이지 번호 (기본값 0) |
| size | Integer | X | 페이지 크기 (기본값 10) |

**Response 예시 (200 OK)**
```json
{
  "success": true,
  "data": {
    "content": [
      {
        "id": 1,
        "name": "무선 마우스",
        "price": 25000,
        "stock": 100,
        "description": "인체공학적 무선 마우스",
        "createdAt": "2026-06-15T10:00:00",
        "updatedAt": "2026-06-15T10:00:00"
      },
      {
        "id": 2,
        "name": "기계식 키보드",
        "price": 89000,
        "stock": 50,
        "description": "청축 기계식 키보드",
        "createdAt": "2026-06-15T10:05:00",
        "updatedAt": "2026-06-15T10:05:00"
      }
    ],
    "totalElements": 2,
    "totalPages": 1,
    "page": 0,
    "size": 10
  },
  "error": null
}
```

| 응답 코드 | 설명 |
| --- | --- |
| 200 | 조회 성공 |

---

### 1.4 상품 수정

- **Method**: `PUT` (또는 `PATCH`)
- **URL**: `/api/products/{productId}`
- **Request Body**

| 필드 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| name | String | O | 상품명 |
| price | Integer | O | 상품 가격 |
| stock | Integer | O | 재고 수량 |
| description | String | X | 상품 설명 |

**Request 예시**
```json
{
  "name": "무선 마우스 (리뉴얼)",
  "price": 27000,
  "stock": 90,
  "description": "인체공학적 무선 마우스 - 2세대"
}
```

**Response 예시 (200 OK)**
```json
{
  "success": true,
  "data": {
    "id": 1,
    "name": "무선 마우스 (리뉴얼)",
    "price": 27000,
    "stock": 90,
    "description": "인체공학적 무선 마우스 - 2세대",
    "createdAt": "2026-06-15T10:00:00",
    "updatedAt": "2026-06-15T11:00:00"
  },
  "error": null
}
```

| 응답 코드 | 설명 |
| --- | --- |
| 200 | 수정 성공 |
| 400 | 요청 값 유효성 오류 |
| 404 | 상품을 찾을 수 없음 (PRODUCT_NOT_FOUND) |

---

### 1.5 상품 삭제

- **Method**: `DELETE`
- **URL**: `/api/products/{productId}`

**Response 예시 (200 OK 또는 204 No Content)**
```json
{
  "success": true,
  "data": null,
  "error": null
}
```

| 응답 코드 | 설명 |
| --- | --- |
| 200 / 204 | 삭제 성공 |
| 404 | 상품을 찾을 수 없음 (PRODUCT_NOT_FOUND) |
| 409 | 해당 상품으로 생성된 주문이 존재하여 삭제 불가 (정책에 따라 선택 적용) |

---

## 2. 주문(Order) API

### 2.1 주문 생성

- **Method**: `POST`
- **URL**: `/api/orders`
- **Request Body**

| 필드 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| productId | Long | O | 주문할 상품 ID (1개만 선택 가능) |
| quantity | Integer | O | 주문 수량 |

**Request 예시**
```json
{
  "productId": 1,
  "quantity": 2
}
```

**Response 예시 (201 Created)**
```json
{
  "success": true,
  "data": {
    "id": 1001,
    "productId": 1,
    "productName": "무선 마우스",
    "quantity": 2,
    "orderPrice": 25000,
    "totalPrice": 50000,
    "status": "CREATED",
    "createdAt": "2026-06-15T12:00:00",
    "updatedAt": "2026-06-15T12:00:00"
  },
  "error": null
}
```

| 응답 코드 | 설명 |
| --- | --- |
| 201 | 주문 생성 성공 |
| 400 | 요청 값 유효성 오류 (productId 누락, quantity 0 이하 등) |
| 404 | 주문하려는 상품을 찾을 수 없음 (PRODUCT_NOT_FOUND) |
| 409 | 재고 부족 (OUT_OF_STOCK) |

---

### 2.2 주문 단건 조회

- **Method**: `GET`
- **URL**: `/api/orders/{orderId}`

**Response 예시 (200 OK)**
```json
{
  "success": true,
  "data": {
    "id": 1001,
    "productId": 1,
    "productName": "무선 마우스 (리뉴얼)",
    "quantity": 2,
    "orderPrice": 25000,
    "totalPrice": 50000,
    "status": "CREATED",
    "createdAt": "2026-06-15T12:00:00",
    "updatedAt": "2026-06-15T12:00:00"
  },
  "error": null
}
```

> 참고: `productName`은 주문 시점이 아닌 **조회 시점**의 상품명을 반영합니다. 즉, 상품명이 변경되면 이후 조회 결과의 `productName`도 변경된 이름으로 표시됩니다. 반면 `orderPrice`는 주문 생성 시점의 가격이 스냅샷으로 저장되어 변하지 않습니다.

| 응답 코드 | 설명 |
| --- | --- |
| 200 | 조회 성공 |
| 404 | 주문을 찾을 수 없음 (ORDER_NOT_FOUND) |

---

## 3. 공통 에러 코드

| 코드 | HTTP Status | 설명 |
| --- | --- | --- |
| PRODUCT_NOT_FOUND | 404 | 요청한 상품이 존재하지 않음 |
| ORDER_NOT_FOUND | 404 | 요청한 주문이 존재하지 않음 |
| OUT_OF_STOCK | 409 | 상품 재고 부족 |
| INVALID_REQUEST | 400 | 요청 값 유효성 검증 실패 |