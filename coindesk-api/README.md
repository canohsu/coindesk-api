# Coindesk API Integration Project

Spring Boot Web 應用程序，整合 Coindesk API 並提供幣種管理功能。

## 需求

- **Build Tool**: Maven
- **JDK**: 1.8
- **Spring Boot**: 2.7.14
- **ORM**: Spring Data JPA
- **Database**: H2

## 功能說明

### 1. 幣別資料表 CRUD API
- `GET /api/currencies` - 查詢所有幣別
- `GET /api/currencies/{id}` - 按 ID 查詢幣別
- `GET /api/currencies/code/{code}` - 按幣別代碼查詢
- `POST /api/currencies` - 新增幣別
- `PUT /api/currencies/{id}` - 修改幣別
- `DELETE /api/currencies/{id}` - 刪除幣別

### 2. Coindesk API 呼叫
- `GET /api/coindesk/raw` - 返回原始 Coindesk 數據

### 3. 數據轉換 API
- `GET /api/coindesk/transformed` - 返回轉換後的數據（包含中文名稱和時間格式）

## 初始化數據

以下幣別會在程式啟動時自動初始化：
- USD - 美元
- GBP - 英鎊
- EUR - 歐元
- JPY - 日圓
- CNY - 人民幣

## SQL 表結構

```sql
CREATE TABLE CURRENCY (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    CODE VARCHAR(10) NOT NULL UNIQUE,
    CHINESE_NAME VARCHAR(100) NOT NULL,
    CREATE_TIME VARCHAR(50),
    UPDATE_TIME VARCHAR(50)
);
```

## 測試項目

### 1. 單元測試 - 數據轉換邏輯
- `CoinDeskTransformTest` - 測試 Coindesk 數據轉換

### 2. 幣別 CRUD API 測試
- `CurrencyServiceTest` - 測試幣別服務
- `CurrencyControllerTest` - 測試幣別 API

### 3. Coindesk API 測試
- `CoinDeskApiTest` - 測試 Coindesk API 調用

### 4. 數據轉換 API 測試
- `CoinDeskControllerTest` - 測試轉換 API

## 快速開始

### 構建項目
```bash
cd coindesk-api
mvn clean install
```

### 運行應用程序
```bash
mvn spring-boot:run
```

應用會在 `http://localhost:8080` 上運行。

### 運行測試
```bash
mvn test
```

### H2 控制台
訪問 `http://localhost:8080/h2-console` 查看數據庫內容
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (留空)

## API 調用示例

### 查詢所有幣別
```bash
curl http://localhost:8080/api/currencies
```

### 按代碼查詢幣別
```bash
curl http://localhost:8080/api/currencies/code/USD
```

### 新增幣別
```bash
curl -X POST http://localhost:8080/api/currencies \
  -H "Content-Type: application/json" \
  -d '{
    "code": "CAD",
    "chineseName": "加拿大元"
  }'
```

### 修改幣別
```bash
curl -X PUT http://localhost:8080/api/currencies/1 \
  -H "Content-Type: application/json" \
  -d '{
    "code": "USD",
    "chineseName": "美國美元"
  }'
```

### 刪除幣別
```bash
curl -X DELETE http://localhost:8080/api/currencies/1
```

### 獲取轉換後的 Coindesk 數據
```bash
curl http://localhost:8080/api/coindesk/transformed
```

## 項目結構

```
coindesk-api/
├── src/
│   ├── main/
│   │   ├── java/com/example/coindesk/
│   │   │   ├── CoinDeskApplication.java (主程序)
│   │   │   ├── controller/
│   │   │   │   ├── CurrencyController.java
│   │   │   │   └── CoinDeskController.java
│   │   │   ├── service/
│   │   │   │   ├── CurrencyService.java
│   │   │   │   └── CoinDeskService.java
│   │   │   ├── repository/
│   │   │   │   └── CurrencyRepository.java
│   │   │   ├── entity/
│   │   │   │   └── Currency.java
│   │   │   └── dto/
│   │   │       ├── CurrencyDTO.java
│   │   │       ├── CoinInfoDTO.java
│   │   │       ├── CoinDeskResponseDTO.java
│   │   │       └── CoinDeskRawDTO.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── data.sql
│   └── test/java/com/example/coindesk/
│       ├── CurrencyServiceTest.java
│       ├── CoinDeskApiTest.java
│       ├── CoinDeskTransformTest.java
│       ├── CurrencyControllerTest.java
│       └── CoinDeskControllerTest.java
└── pom.xml
```

## 開發工具

- Java IDE (IntelliJ IDEA / Eclipse)
- Maven CLI
- Postman / curl (API 測試)
- H2 Web Console

## 許可證

MIT License
