# ERD (Entity Relationship Diagram)

## 1. 개요

본 프로젝트는 상품(Product)과 주문(Order) 두 도메인으로 구성됩니다.
- 상품(Product)은 여러 주문(Order)에서 참조될 수 있습니다. (1:N 관계)
- 주문(Order)은 반드시 1개의 상품(Product)을 참조합니다. (주문 시 상품은 1개만 선택 가능)
- 주문 조회 시 상품명은 연관관계를 통해 실시간으로 조회되며, 상품명이 변경되면 변경된 이름이 반영됩니다.


## 2. 테이블 상세 정의

### 2.1 PRODUCT (상품)

| 컬럼명 | 타입 | 제약조건 | 설명 |
| --- | --- | --- | --- |
| id | BIGINT | PK, AUTO_INCREMENT | 상품 고유 식별자 |
| name | VARCHAR(100) | NOT NULL | 상품명 |
| price | INT | NOT NULL | 상품 가격 |
| stock | INT | NOT NULL, DEFAULT 0 | 재고 수량 |
| description | VARCHAR(500) | NULL | 상품 설명 |
| created_at | DATETIME | NOT NULL | 생성일시 |
| updated_at | DATETIME | NOT NULL | 수정일시 |

### 2.2 ORDER (주문)

| 컬럼명 | 타입 | 제약조건 | 설명 |
| --- | --- | --- | --- |
| id | BIGINT | PK, AUTO_INCREMENT | 주문 고유 식별자 |
| product_id | BIGINT | FK (PRODUCT.id), NOT NULL | 주문한 상품 ID |
| quantity | INT | NOT NULL, DEFAULT 1 | 주문 수량 |
| order_price | INT | NOT NULL | 주문 시점의 상품 가격 (스냅샷) |
| status | VARCHAR(20) | NOT NULL | 주문 상태 |
| created_at | DATETIME | NOT NULL | 생성일시 |
| updated_at | DATETIME | NOT NULL | 수정일시 |
