# Coindesk API Integration Project - 完整實作文檔

## 📋 項目概述

這是一個基於 Maven 和 Spring Boot 的 Web 應用程序，用於整合遠程 Coindesk API 並提供完整的幣種管理功能。項目實現了資料轉換、數據庫 CRUD 操作以及 RESTful API 端點。

### 技術棧
- **Build Tool**: Maven 3.6+
- **JDK**: Java 1.8
- **Framework**: Spring Boot 2.7.14
- **ORM**: Spring Data JPA with Hibernate
- **Database**: H2 (In-Memory)
- **HTTP Client**: Apache HttpClient
- **JSON**: Jackson & Gson

---

## 🎯 功能實現清單

### ✅ 已完成的功能

#### 1. **幣別資料表 CRUD 管理** (`/api/currencies`)
- [x] **查詢所有幣別** - `GET /api/currencies`
  - 返回所有幣別及其中文名稱
  
- [x] **按 ID 查詢** - `GET /api/currencies/{id}`
  - 根據幣別 ID 查詢單筆記錄
  
- [x] **按代碼查詢** - `GET /api/currencies/code/{code}`
  - 根據幣別代碼（如 USD）查詢
  
- [x] **新增幣別** - `POST /api/currencies`
  - 新增幣別及其中文名稱
  - 自動記錄建立時間
  
- [x] **修改幣別** - `PUT /api/currencies/{id}`
  - 修改幣別資訊
  - 自動更新修改時間
  
- [x] **刪除幣別** - `DELETE /api/currencies/{id}`
  - 刪除指定幣別

#### 2. **Coindesk API 遠程調用** (`/api/coindesk`)
- [x] **獲取原始數據** - `GET /api/coindesk/raw`
  - 調用 Coindesk API：`https://kengp3.github.io/blog/coindesk.json`
  - 返回原始 JSON 數據結構
  - 包含時間信息和各幣別匯率

- [x] **獲取轉換數據** - `GET /api/coindesk/transformed`
  - 調用 Coindesk API 並進行數據轉換
  - 添加幣別中文名稱（從本地數據庫查詢）
  - 格式化時間為 `yyyy/MM/dd HH:mm:ss` 格式

#### 3. **數據轉換邏輯**
- [x] 時間格式轉換
  - 原始格式：`MMM dd, yyyy HH:mm:ss Z` (如 `Apr 09, 2026 08:30:00 GMT`)
  - 目標格式：`yyyy/MM/dd HH:mm:ss` (如 `2026/04/09 08:30:00`)

- [x] 幣別中文名稱對接
  - 從本地資料庫查詢幣別代碼對應的中文名稱
  - 若未找到，返回代碼加未找到標記

- [x] 數據結構重組
  - 原始 BPI 結構轉換為簡化的幣種列表結構

---

## 📁 項目結構

```
coindesk-api/
├── pom.xml                                    # Maven 配置文件
├── schema.sql                                 # 數據庫表結構定義
├── README.md                                  # 項目說明
├── IMPLEMENTATION_DETAILS.md                  # 詳細實現文檔（本文件）
│
├── src/main/
│   ├── java/com/example/coindesk/
│   │   ├── CoinDeskApplication.java           # Spring Boot 主程序入口
│   │   │
│   │   ├── controller/                        # REST API 控制器層
│   │   │   ├── CurrencyController.java        # 幣別管理 API
│   │   │   └── CoinDeskController.java        # Coindesk API 代理
│   │   │
│   │   ├── service/                           # 業務邏輯服務層
│   │   │   ├── CurrencyService.java           # 幣別 CRUD 服務
│   │   │   └── CoinDeskService.java           # 數據抓取和轉換服務
│   │   │
│   │   ├── repository/                        # 數據倉儲層
│   │   │   └── CurrencyRepository.java        # JPA 幣別倉儲
│   │   │
│   │   ├── entity/                            # JPA 實體類
│   │   │   └── Currency.java                  # 幣別 Entity
│   │   │
│   │   ├── dto/                               # 數據轉移對象
│   │   │   ├── CurrencyDTO.java               # 幣別 DTO
│   │   │   ├── CoinDeskRawDTO.java            # 原始 Coindesk 數據 DTO
│   │   │   ├── CoinDeskResponseDTO.java       # 轉換後的響應 DTO
│   │   │   └── CoinInfoDTO.java               # 幣種信息 DTO
│   │   │
│   │   └── util/                              # 工具類（預留）
│   │
│   └── resources/
│       ├── application.properties              # Spring Boot 配置
│       └── data.sql                            # 數據庫初始化 SQL
│
├── src/test/java/com/example/coindesk/        # 測試代碼
│   ├── CoinDeskTransformTest.java             # 數據轉換單元測試
│   ├── CoinDeskApiTest.java                   # Coindesk API 調用測試
│   ├── CoinDeskControllerTest.java            # 轉換 API 端點測試
│   ├── CurrencyServiceTest.java               # 幣別服務業務邏輯測試
│   └── CurrencyControllerTest.java            # 幣別 API 端點測試
│
└── target/                                    # Maven 編譯輸出目錄

```

---

## 🗄️ 數據庫設計

### CURRENCY 表結構

```sql
CREATE TABLE CURRENCY (
    ID          BIGINT AUTO_INCREMENT PRIMARY KEY,
    CODE        VARCHAR(10) NOT NULL UNIQUE,
    CHINESE_NAME VARCHAR(100) NOT NULL,
    CREATE_TIME  VARCHAR(50),
    UPDATE_TIME  VARCHAR(50)
);
```

### 字段說明

| 欄位 | 類型 | 說明 | 範例 |
|-----|------|------|------|
| ID | BIGINT | 主鍵，自動遞增 | 1, 2, 3... |
| CODE | VARCHAR(10) | 幣別代碼，唯一 | USD, EUR, GBP |
| CHINESE_NAME | VARCHAR(100) | 中文名稱 | 美元, 歐元, 英鎊 |
| CREATE_TIME | VARCHAR(50) | 建立時間 | 2024/01/01 00:00:00 |
| UPDATE_TIME | VARCHAR(50) | 更新時間 | 2024/01/15 10:30:45 |

### 初始化數據

系統自動初始化以下 5 種主要幣別：

| 代碼 | 中文名稱 | 說明 |
|-----|--------|------|
| USD | 美元 | 美國貨幣 |
| EUR | 歐元 | 歐元區貨幣 |
| GBP | 英鎊 | 英國貨幣 |
| JPY | 日圓 | 日本貨幣 |
| CNY | 人民幣 | 中國貨幣 |

---

## 🔌 API 端點詳解

### 1️⃣ 幣別管理 API

#### 1.1 查詢所有幣別
```
GET /api/currencies
```
**響應示例：**
```json
[
  {
    "id": 1,
    "code": "USD",
    "chineseName": "美元",
    "createTime": "2024/01/01 00:00:00",
    "updateTime": "2024/01/01 00:00:00"
  },
  {
    "id": 2,
    "code": "EUR",
    "chineseName": "歐元",
    "createTime": "2024/01/01 00:00:00",
    "updateTime": "2024/01/01 00:00:00"
  }
]
```

#### 1.2 按代碼查詢幣別
```
GET /api/currencies/code/{code}
範例: GET /api/currencies/code/USD
```
**響應示例：**
```json
{
  "id": 1,
  "code": "USD",
  "chineseName": "美元",
  "createTime": "2024/01/01 00:00:00",
  "updateTime": "2024/01/01 00:00:00"
}
```

#### 1.3 按 ID 查詢幣別
```
GET /api/currencies/{id}
範例: GET /api/currencies/1
```

#### 1.4 新增幣別
```
POST /api/currencies
Content-Type: application/json

請求體：
{
  "code": "CAD",
  "chineseName": "加拿大元"
}
```
**響應示例（201 Created）：**
```json
{
  "id": 6,
  "code": "CAD",
  "chineseName": "加拿大元",
  "createTime": "2024/04/09 10:30:00",
  "updateTime": "2024/04/09 10:30:00"
}
```

#### 1.5 修改幣別
```
PUT /api/currencies/{id}
Content-Type: application/json

請求體：
{
  "code": "USD",
  "chineseName": "美國美元"
}
```
**響應示例：**
```json
{
  "id": 1,
  "code": "USD",
  "chineseName": "美國美元",
  "createTime": "2024/01/01 00:00:00",
  "updateTime": "2024/04/09 10:35:00"
}
```

#### 1.6 刪除幣別
```
DELETE /api/currencies/{id}
範例: DELETE /api/currencies/6
```
**響應示例：**
```json
{
  "message": "Currency deleted successfully"
}
```

### 2️⃣ Coindesk API 代理

#### 2.1 獲取原始 Coindesk 數據
```
GET /api/coindesk/raw
```
**響應示例：**
```json
{
  "time": {
    "updated": "Apr 09, 2026 08:30:00 GMT",
    "updatedISO": "2026-04-09T08:30:00+00:00",
    "updateduk": "Apr 09, 2026 at 09:30 BST"
  },
  "bpi": {
    "USD": {
      "code": "USD",
      "symbol": "&#36;",
      "rate": "45,123.4567",
      "description": "United States Dollar",
      "rate_float": 45123.4567
    },
    "GBP": {
      "code": "GBP",
      "symbol": "&pound;",
      "rate": "35,678.9012",
      "description": "British Pound",
      "rate_float": 35678.9012
    },
    "EUR": {
      "code": "EUR",
      "symbol": "&euro;",
      "rate": "41,567.3456",
      "description": "Euro",
      "rate_float": 41567.3456
    }
  }
}
```

#### 2.2 獲取轉換後的數據（含中文名稱）
```
GET /api/coindesk/transformed
```
**響應示例：**
```json
{
  "updateTime": "2026/04/09 08:30:00",
  "coins": [
    {
      "currency": "USD",
      "chineseName": "美元",
      "rate": 45123.4567
    },
    {
      "currency": "EUR",
      "chineseName": "歐元",
      "rate": 41567.3456
    },
    {
      "currency": "GBP",
      "chineseName": "英鎊",
      "rate": 35678.9012
    }
  ]
}
```

---

## 🧪 測試覆蓋

### 測試配置
- **測試框架**: JUnit 4
- **Mock 框架**: Mockito
- **Web 測試**: MockMvc

### 測試清單

#### ✅ 1. 數據轉換邏輯測試
**文件**: `CoinDeskTransformTest.java`

測試內容：
- 驗證 Coindesk API 數據被正確轉換
- 驗證時間格式正確轉換為 `yyyy/MM/dd HH:mm:ss`
- 驗證幣別中文名稱正確對應
- 驗證匯率數據完整性

測試方法：
```
testTransformCoinDeskData()
```

#### ✅ 2. Coindesk API 調用測試
**文件**: `CoinDeskApiTest.java`

測試內容：
- 驗證遠程 API 調用成功
- 驗證返回數據結構完整
- 驗證 BPI 數據解析
- 驗證幣別列表非空

測試方法：
```
testFetchCoinDeskData()
```

#### ✅ 3. 幣別 CRUD 服務測試
**文件**: `CurrencyServiceTest.java`

測試內容：
- `testFindAllCurrencies()` - 查詢所有幣別
- `testFindCurrencyByCode()` - 按代碼查詢
- `testCreateCurrency()` - 新增幣別
- `testUpdateCurrency()` - 修改幣別
- `testDeleteCurrency()` - 刪除幣別

#### ✅ 4. 幣別 API 端點測試
**文件**: `CurrencyControllerTest.java`

測試內容：
- `testGetAllCurrencies()` - GET /api/currencies
- `testGetCurrencyByCode()` - GET /api/currencies/code/{code}
- `testCreateCurrency()` - POST /api/currencies
- `testUpdateCurrency()` - PUT /api/currencies/{id}
- `testDeleteCurrency()` - DELETE /api/currencies/{id}

#### ✅ 5. Coindesk 轉換 API 測試
**文件**: `CoinDeskControllerTest.java`

測試內容：
- `testGetRawCoinDeskData()` - GET /api/coindesk/raw
- `testGetTransformedCoinDeskData()` - GET /api/coindesk/transformed

---

## 🚀 快速開始

### 前置條件
- JDK 1.8 或更高版本
- Maven 3.6.0 或更高版本
- 網絡連接（用於調用遠程 Coindesk API）

### 安裝步驟

1. **克隆或下載項目**
```bash
cd coindesk-api
```

2. **編譯項目**
```bash
mvn clean compile
```

3. **運行測試**
```bash
mvn test
```

4. **打包應用**
```bash
mvn clean package
```

5. **運行應用**
```bash
mvn spring-boot:run
```

應用會在 `http://localhost:8080` 啟動

### 訪問資源

- **API 首頁**: http://localhost:8080/
- **H2 數據庫管理**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: (留空)
- **Actuator 指標**: http://localhost:8080/actuator (需要配置啟用)

---

## 📝 API 調用示例（CURL）

### 查詢所有幣別
```bash
curl -X GET http://localhost:8080/api/currencies
```

### 按代碼查詢
```bash
curl -X GET http://localhost:8080/api/currencies/code/USD
```

### 新增幣別
```bash
curl -X POST http://localhost:8080/api/currencies \
  -H "Content-Type: application/json" \
  -d '{"code":"CAD","chineseName":"加拿大元"}'
```

### 修改幣別
```bash
curl -X PUT http://localhost:8080/api/currencies/1 \
  -H "Content-Type: application/json" \
  -d '{"code":"USD","chineseName":"美國美元"}'
```

### 刪除幣別
```bash
curl -X DELETE http://localhost:8080/api/currencies/6
```

### 獲取原始 Coindesk 數據
```bash
curl -X GET http://localhost:8080/api/coindesk/raw
```

### 獲取轉換後的數據
```bash
curl -X GET http://localhost:8080/api/coindesk/transformed
```

---

## 🔧 配置說明

### application.properties
```properties
# 應用配置
spring.application.name=coindesk-api
server.port=8080

# 數據庫配置
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA/Hibernate 配置
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop    # 每次啟動創建新表
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true

# H2 控制台配置
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

---

## 🎓 技術亮點

### 1. **多層架構設計**
- Controller 層：處理 HTTP 請求/響應
- Service 層：業務邏輯處理
- Repository 層：數據持久化
- Entity/DTO：數據對象轉換

### 2. **異常處理**
- 統一的錯誤響應結構
- HTTP 狀態碼的正確使用
- 詳細的錯誤消息

### 3. **數據轉換**
- DTO 模式確保 API 層與內部模型解耦
- 時間格式的標準化處理
- 靈活的數據映射

### 4. **ORM 集成**
- Spring Data JPA 簡化數據庫操作
- 自動化的 CRUD 方法
- Query 方法的靈活定義

### 5. **遠程 API 集成**
- Apache HttpClient 進行網絡請求
- Jackson 進行 JSON 序列化/反序列化
- 錯誤處理和重試機制

### 6. **完整的測試覆蓋**
- 單元測試驗證業務邏輯
- 集成測試驗證 API 端點
- 數據轉換邏輯的詳細測試

---

## 🐛 常見問題

### Q1: 如何修改服務器端口？
A: 編輯 `application.properties`，修改 `server.port` 的值

### Q2: 數據會持久化嗎？
A: 不會。使用 H2 in-memory 數據庫，應用重啟後數據清空。若需持久化，可修改配置使用文件數據庫

### Q3: 如何訪問 H2 數據庫管理冻台？
A: 應用啟動後訪問 http://localhost:8080/h2-console，使用設置的連接信息登錄

### Q4: 如何解決 Coindesk 遠程 API 超時？
A: 檢查網絡連接，或在 CoinDeskService 中添加超時配置

### Q5: 幣別代碼和中文名稱對應不上怎麼辦？
A: 通過 POST API 新增對應的幣別代碼和中文名稱

---

## 📚 依賴清單

| 依賴 | 版本 | 用途 |
|-----|------|------|
| spring-boot-starter-web | 2.7.14 | Web 框架 |
| spring-boot-starter-data-jpa | 2.7.14 | ORM 框架 |
| h2 | latest | 內存數據庫 |
| httpclient | 4.5.14 | HTTP 客戶端 |
| jackson-databind | 2.7.14 | JSON 處理 |
| gson | 2.10.1 | JSON 序列化 |
| lombok | 1.18.20 | 代碼生成 |
| junit | 4.x | 測試框架 |
| spring-boot-starter-test | 2.7.14 | 測試組件 |

---

## 📞 聯繫方式

如發現任何問題或建議，請提交 Issue 或 Pull Request

---

*最後更新：2026年4月9日*
*版本：1.0.0*
