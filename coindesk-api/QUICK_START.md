# 📖 Coindesk API 项目 - 快速部署和运行指南

## 📌 目录

1. [系统要求](#系统要求)
2. [环境配置](#环境配置)
3. [快速开始](#快速开始)
4. [使用构建脚本](#使用构建脚本)
5. [运行和测试](#运行和测试)
6. [API 调用示例](#api-调用示例)
7. [故障排除](#故障排除)

---

## 💻 系统要求

### 最低配置
- **操作系统**: Windows 7+, macOS 10.12+, Linux (Ubuntu 16.04+)
- **内存**: 2GB RAM 最低，4GB+ 推荐
- **磁盘空间**: 500MB (包含依赖下载)

### 必需软件
- **Java**: JDK 1.8 或更高版本
- **Maven**: 3.6.0 或更高版本（可选，有 Maven Wrapper）
- **Git**: 用于版本控制（可选）

### 网络要求
- 互联网连接用于下载 Maven 依赖
- 互联网连接用于调用 Coindesk 远程 API

---

## 🔧 环境配置

### Windows 系统

#### 1. 安装 Java

**方法 A: 使用官方安装程序**
1. 下载 JDK: https://www.oracle.com/java/technologies/javase/jdk8-archive-downloads.html
2. 运行安装程序
3. 按照安装向导完成安装

**方法 B: 使用 Chocolatey (如已安装)**
```powershell
choco install openjdk8
```

**验证安装**:
```cmd
java -version
javac -version
```

#### 2. 安装 Maven

**方法 A: 下载赫手动安装**
1. 下载 Maven: https://maven.apache.org/download.cgi
2. 解压到本地，例如 `C:\maven\apache-maven-3.8.1`
3. 添加 `C:\maven\apache-maven-3.8.1\bin` 到 Path 环境变量
4. 重启命令行

**方法 B: 使用 Chocolatey**
```powershell
choco install maven
```

**验证安装**:
```cmd
mvn -version
```

### macOS 系统

#### 1. 安装 Java

**使用 Homebrew**:
```bash
brew install openjdk@8
```

或下载官方 JDK: https://www.oracle.com/java/technologies/javase/jdk8-archive-downloads.html

**验证安装**:
```bash
java -version
```

#### 2. 安装 Maven

**使用 Homebrew**:
```bash
brew install maven
```

**验证安装**:
```bash
mvn -version
```

### Linux 系统 (Ubuntu/Debian)

#### 1. 安装 Java

```bash
sudo apt-get update
sudo apt-get install openjdk-8-jdk
```

**验证安装**:
```bash
java -version
```

#### 2. 安装 Maven

```bash
sudo apt-get install maven
```

**验证安装**:
```bash
mvn -version
```

---

## 🚀 快速开始

### 方法 1: 使用构建脚本 (推荐)

#### Windows:
```cmd
cd coindesk-api
build.bat check      # 检查环境
build.bat compile    # 编译项目
build.bat test       # 运行测试
build.bat run        # 启动应用
```

#### macOS / Linux:
```bash
cd coindesk-api
bash build.sh check      # 检查环境
bash build.sh compile    # 编译项目
bash build.sh test       # 运行测试
bash build.sh run        # 启动应用
```

### 方法 2: 手动命令

#### 1. 进入项目目录
```bash
cd coindesk-api
```

#### 2. 编译项目
```bash
mvn clean compile
```

#### 3. 运行测试
```bash
mvn test
```

#### 4. 启动应用
```bash
mvn spring-boot:run
```

#### 验证应用启动
访问: http://localhost:8080/api/currencies

预期看到 JSON 响应

---

## 📜 使用构建脚本

### Windows 脚本 - build.bat

```cmd
build.bat help              # 显示帮助信息
build.bat check             # 检查 Java 和 Maven
build.bat clean             # 清理编译输出
build.bat compile           # 编译项目
build.bat test              # 运行所有测试
build.bat test-transform    # 运行数据转换测试
build.bat test-api          # 运行 Coindesk API 测试
build.bat test-service      # 运行 Service 测试
build.bat test-controller   # 运行 Controller 测试
build.bat package           # 打包应用 (生成 JAR)
build.bat run               # 运行应用
```

### Unix/Linux/macOS 脚本 - build.sh

```bash
bash build.sh help              # 显示帮助信息
bash build.sh check             # 检查 Java 和 Maven
bash build.sh clean             # 清理编译输出
bash build.sh compile           # 编译项目
bash build.sh test              # 运行所有测试
bash build.sh test-transform    # 运行数据转换测试
bash build.sh test-api          # 运行 Coindesk API 测试
bash build.sh test-service      # 运行 Service 测试
bash build.sh test-controller   # 运行 Controller 测试
bash build.sh package           # 打包应用 (生成 JAR)
bash build.sh run               # 运行应用
```

### 完整工作流示例

#### 首次运行
```bash
# 1. 检查环境
bash build.sh check

# 2. 清理并编译
bash build.sh clean
bash build.sh compile

# 3. 运行完整测试套
bash build.sh test

# 4. 打包应用
bash build.sh package

# 5. 启动应用
bash build.sh run
```

#### 仅测试特定功能
```bash
# 仅测试数据转换逻辑
bash build.sh test-transform

# 仅测试 API 调用
bash build.sh test-api

# 仅测试 Service 层
bash build.sh test-service
```

---

## 🧪 运行和测试

### 启动应用

```bash
mvn spring-boot:run
```

**预期输出**:
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_|\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.7.14)

2026-04-09 10:30:00.123  INFO 12345 --- [           main] c.e.c.CoinDeskApplication               : Starting CoinDeskApplication
...
2026-04-09 10:30:02.456  INFO 12345 --- [           main] c.e.c.CoinDeskApplication               : Started CoinDeskApplication in 2.123 seconds
```

### 访问应用

| 功能 | URL |
|-----|-----|
| 查询所有幣別 | http://localhost:8080/api/currencies |
| 查询 USD 幣別 | http://localhost:8080/api/currencies/code/USD |
| Coindesk 原始數據 | http://localhost:8080/api/coindesk/raw |
| Coindesk 轉換數據 | http://localhost:8080/api/coindesk/transformed |
| H2 數據庫管理 | http://localhost:8080/h2-console |

### 数据库访问

**H2 控制台登录**:
- URL: http://localhost:8080/h2-console
- Driver: `org.h2.Driver`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (留空)

### 运行测试

#### 运行所有测试
```bash
mvn test
```

#### 运行特定测试类
```bash
mvn test -Dtest=CurrencyServiceTest
mvn test -Dtest=CoinDeskApiTest
mvn test -Dtest=CoinDeskTransformTest
```

#### 运行特定测试方法
```bash
mvn test -Dtest=CurrencyServiceTest#testCreateCurrency
```

#### 生成测试覆盖率报告
```bash
mvn test jacoco:report
# 报告位置: target/site/jacoco/index.html
```

---

## 📝 API 调用示例

### 使用 curl

#### 1. 查询所有幣別
```bash
curl -X GET "http://localhost:8080/api/currencies" \
  -H "Content-Type: application/json"
```

#### 2. 按代碼查詢幣別
```bash
curl -X GET "http://localhost:8080/api/currencies/code/USD" \
  -H "Content-Type: application/json"
```

#### 3. 新增幣別
```bash
curl -X POST "http://localhost:8080/api/currencies" \
  -H "Content-Type: application/json" \
  -d '{
    "code": "CAD",
    "chineseName": "加拿大元"
  }'
```

#### 4. 修改幣別
```bash
curl -X PUT "http://localhost:8080/api/currencies/1" \
  -H "Content-Type: application/json" \
  -d '{
    "code": "USD",
    "chineseName": "美國美元(已驗證)"
  }'
```

#### 5. 刪除幣別
```bash
curl -X DELETE "http://localhost:8080/api/currencies/6"
```

#### 6. 獲取轉換後的 Coindesk 數據
```bash
curl -X GET "http://localhost:8080/api/coindesk/transformed" \
  -H "Content-Type: application/json"
```

### 使用 PostMan

1. 导入 API 集合 (如有提供)
2. 设置 base URL: `http://localhost:8080`
3. 选择对应的请求
4. 修改请求体 (如需要)
5. 发送请求

---

## 🐛 故障排除

### Q1: "Java 未找到" 错误

**解决方案**:
```bash
# 检查 Java 安装
java -version

# 如果未返回版本信息，安装 JDK 1.8
# Windows: 下载并安装 JDK
# macOS: brew install openjdk@8
# Linux: sudo apt-get install openjdk-8-jdk
```

### Q2: "Maven 未找到" 错误

**解决方案**:
```bash
# 检查 Maven 安装
mvn -version

# 如果使用 Maven Wrapper (推荐在 IDE 中)
./mvnw clean install  # macOS/Linux
mvnw.cmd clean install  # Windows
```

### Q3: 端口 8080 已被占用

**解决方案**:
```bash
# 编辑 src/main/resources/application.properties
server.port=8081  # 改为其他端口

# 或使用命令行覆盖:
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"
```

### Q4: Coindesk API 连接超时

**解决方案**:
- 检查互联网连接
- 检查防火墙设置
- 尝试访问 https://kengp3.github.io/blog/coindesk.json

### Q5: 数据库连接错误

**解决方案**:
```bash
# H2 in-memory 数据库应自动创建
# 如出现错误，尝试:
1. 清理项目: mvn clean
2. 重新编译: mvn compile
3. 重启应用
```

### Q6: 测试失败

**解决方案**:
```bash
# 查看详细输出
mvn test -X

# 运行单个测试
mvn test -Dtest=TestClassName

# 清理缓存后重试
mvn clean test
```

---

## 📦 打包和部署

### 生成可执行 JAR

```bash
mvn clean package
```

**输出**: `target/coindesk-api-1.0.0.jar`

### 运行 JAR 文件

```bash
java -jar target/coindesk-api-1.0.0.jar
```

### 修改 JAR 启动端口

```bash
java -jar target/coindesk-api-1.0.0.jar --server.port=8081
```

---

## 📚 相关文档

- [完整实现文档](IMPLEMENTATION_DETAILS.md)
- [API 文档](README.md)
- [数据库架构](schema.sql)

---

## 🔗 有用资源

- [Maven 官方文档](https://maven.apache.org/guides/)
- [Spring Boot 官方文档](https://spring.io/projects/spring-boot)
- [H2 数据库文档](http://h2database.com/)
- [Coindesk API](https://kengp3.github.io/blog/coindesk.json)

---

## 🆘 获取帮助

如遇到问题，请:
1. 查看故障排除部分
2. 查看项目日志 (test output)
3. 检查相关文档
4. 提交 Issue (GitHub)

---

*最后更新: 2026年4月9日*
*版本: 1.0.0*
