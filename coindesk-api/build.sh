#!/bin/bash

# =========================================================================
# Coindesk API Integration Project - 構建和測試腳本
# =========================================================================
# 該腳本提供便利的命令來構建、測試和運行項目
# 使用方法: bash build.sh [命令]

PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_NAME="coindesk-api"

# 顏色定義用於終端輸出
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# =========================================================================
# 功能函數
# =========================================================================

print_header() {
    echo -e "${BLUE}========================================${NC}"
    echo -e "${BLUE}$1${NC}"
    echo -e "${BLUE}========================================${NC}"
}

print_success() {
    echo -e "${GREEN}✓ $1${NC}"
}

print_error() {
    echo -e "${RED}✗ $1${NC}"
}

print_warning() {
    echo -e "${YELLOW}⚠ $1${NC}"
}

check_java() {
    print_header "檢查 Java 環境"
    
    if ! command -v java &> /dev/null; then
        print_error "Java 未安裝"
        exit 1
    fi
    
    JAVA_VERSION=$(java -version 2>&1 | grep -i version)
    print_success "Java 環境就緒"
    echo "版本信息: $JAVA_VERSION"
    echo
}

check_maven() {
    print_header "檢查 Maven"
    
    if ! command -v mvn &> /dev/null; then
        print_warning "Maven 未安裝，嘗試使用項目的 Maven Wrapper"
        if [ -f "$PROJECT_DIR/mvnw" ]; then
            print_success "找到 Maven Wrapper"
            MVN_CMD="$PROJECT_DIR/mvnw"
        else
            print_error "Maven 未安裝且未找到 Maven Wrapper"
            exit 1
        fi
    else
        MVN_CMD="mvn"
        MAVEN_VERSION=$(mvn -version 2>&1 | head -1)
        print_success "Maven 環境就緒"
        echo "$MAVEN_VERSION"
    fi
    echo
}

clean_project() {
    print_header "清理項目"
    cd "$PROJECT_DIR"
    $MVN_CMD clean
    print_success "項目清理完成"
    echo
}

compile_project() {
    print_header "編譯項目"
    cd "$PROJECT_DIR"
    $MVN_CMD clean compile
    if [ $? -eq 0 ]; then
        print_success "項目編譯成功"
    else
        print_error "項目編譯失敗"
        exit 1
    fi
    echo
}

run_tests() {
    print_header "運行測試"
    cd "$PROJECT_DIR"
    $MVN_CMD test
    if [ $? -eq 0 ]; then
        print_success "所有測試通過"
    else
        print_error "某些測試失敗"
        exit 1
    fi
    echo
}

run_specific_test() {
    local test_class=$1
    print_header "運行特定測試: $test_class"
    cd "$PROJECT_DIR"
    $MVN_CMD test -Dtest=$test_class
    echo
}

package_project() {
    print_header "打包項目"
    cd "$PROJECT_DIR"
    $MVN_CMD clean package -DskipTests
    if [ $? -eq 0 ]; then
        print_success "項目打包成功"
        JAR_FILE=$(find "$PROJECT_DIR/target" -name "*.jar" | grep -v sources)
        if [ -f "$JAR_FILE" ]; then
            echo "JAR 文件: $JAR_FILE"
        fi
    else
        print_error "項目打包失敗"
        exit 1
    fi
    echo
}

run_app() {
    print_header "啟動應用程序"
    cd "$PROJECT_DIR"
    print_warning "應用啟動中... 按 Ctrl+C 停止"
    echo
    $MVN_CMD spring-boot:run
}

view_help() {
    echo
    echo -e "${BLUE}Coindesk API Integration Project - 構建腳本${NC}"
    echo "==========================================================="
    echo
    echo "使用方法: bash build.sh [命令]"
    echo
    echo "可用命令:"
    echo "  check       - 檢查 Java 和 Maven 環境"
    echo "  clean       - 清理編譯輸出"
    echo "  compile     - 編譯項目"
    echo "  test        - 運行所有測試"
    echo "  test-transform   - 運行數據轉換測試"
    echo "  test-api         - 運行 Coindesk API 測試"
    echo "  test-service     - 運行 Currency Service 測試"
    echo "  test-controller  - 運行 Currency Controller 測試"
    echo "  test-coindesk    - 運行 Coindesk 控制器測試"
    echo "  package     - 打包應用程序 (生成 JAR)"
    echo "  run         - 運行應用程序"
    echo "  help        - 顯示此幫助信息"
    echo
    echo "完整工作流示例:"
    echo "  1. bash build.sh check        # 檢查環境"
    echo "  2. bash build.sh compile      # 編譯代碼"
    echo "  3. bash build.sh test         # 運行測試"
    echo "  4. bash build.sh package      # 打包應用"
    echo "  5. bash build.sh run          # 運行應用"
    echo
}

# =========================================================================
# 主程序
# =========================================================================

# 如果沒有參數，顯示幫助
if [ $# -eq 0 ]; then
    view_help
    exit 0
fi

# 處理命令
case "$1" in
    check)
        check_java
        check_maven
        print_success "環境檢查完成"
        ;;
    clean)
        check_maven
        clean_project
        ;;
    compile)
        check_java
        check_maven
        compile_project
        ;;
    test)
        check_java
        check_maven
        run_tests
        ;;
    test-transform)
        check_java
        check_maven
        run_specific_test "CoinDeskTransformTest"
        ;;
    test-api)
        check_java
        check_maven
        run_specific_test "CoinDeskApiTest"
        ;;
    test-service)
        check_java
        check_maven
        run_specific_test "CurrencyServiceTest"
        ;;
    test-controller)
        check_java
        check_maven
        run_specific_test "CurrencyControllerTest"
        ;;
    test-coindesk)
        check_java
        check_maven
        run_specific_test "CoinDeskControllerTest"
        ;;
    package)
        check_java
        check_maven
        package_project
        ;;
    run)
        check_java
        check_maven
        run_app
        ;;
    help)
        view_help
        ;;
    *)
        print_error "未知命令: $1"
        view_help
        exit 1
        ;;
esac

exit 0
