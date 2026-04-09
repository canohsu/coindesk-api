@echo off
REM =========================================================================
REM Coindesk API Integration Project - 構建和測試腳本 (Windows)
REM =========================================================================
REM 該腳本提供便利的命令來構建、測試和運行項目
REM 使用方法: build.bat [命令]

setlocal enabledelayedexpansion

set PROJECT_DIR=%~dp0
set PROJECT_NAME=coindesk-api

echo.
echo =========================================================================
echo Coindesk API Integration Project - Windows 構建腳本
echo =========================================================================
echo.

REM 檢查是否提供了命令
if "%1"=="" (
    call :show_help
    goto :eof
)

REM 日誌分隔符
set SEPARATOR==========================================================

REM =========================================================================
REM 功能子程序
REM =========================================================================

REM 檢查 Java
:check_java
echo %SEPARATOR%
echo 檢查 Java 環境
echo %SEPARATOR%

java -version >nul 2>&1
if errorlevel 1 (
    echo [ERROR] Java 未安裝
    exit /b 1
) else (
    echo [OK] Java 環境就緒
    java -version
)
echo.
goto :eof

REM 檢查 Maven
:check_maven
echo %SEPARATOR%
echo 檢查 Maven
echo %SEPARATOR%

where mvn >nul 2>&1
if errorlevel 1 (
    echo [WARNING] Maven 未在 PATH 中找到
    if exist "%PROJECT_DIR%mvnw.cmd" (
        echo [OK] 找到 Maven Wrapper
        set MVN_CMD=mvnw.cmd
    ) else (
        echo [ERROR] Maven 未安裝且未找到 Maven Wrapper
        exit /b 1
    )
) else (
    echo [OK] Maven 環境就緒
    mvn -version | findstr "Apache"
    set MVN_CMD=mvn
)
echo.
goto :eof

REM 清理項目
:clean_project
echo %SEPARATOR%
echo 清理項目
echo %SEPARATOR%

cd /d "%PROJECT_DIR%"
call %MVN_CMD% clean
if errorlevel 1 (
    echo [ERROR] 項目清理失敗
    exit /b 1
)
echo [OK] 項目清理完成
echo.
goto :eof

REM 編譯項目
:compile_project
echo %SEPARATOR%
echo 編譯項目
echo %SEPARATOR%

cd /d "%PROJECT_DIR%"
call %MVN_CMD% clean compile
if errorlevel 1 (
    echo [ERROR] 項目編譯失敗
    exit /b 1
)
echo [OK] 項目編譯成功
echo.
goto :eof

REM 運行測試
:run_tests
echo %SEPARATOR%
echo 運行所有測試
echo %SEPARATOR%

cd /d "%PROJECT_DIR%"
call %MVN_CMD% test
if errorlevel 1 (
    echo [ERROR] 某些測試失敗
    exit /b 1
)
echo [OK] 所有測試通過
echo.
goto :eof

REM 運行特定測試
:run_specific_test
set TEST_CLASS=%1
echo %SEPARATOR%
echo 運行特定測試: %TEST_CLASS%
echo %SEPARATOR%

cd /d "%PROJECT_DIR%"
call %MVN_CMD% test -Dtest=%TEST_CLASS%
echo.
goto :eof

REM 打包項目
:package_project
echo %SEPARATOR%
echo 打包項目
echo %SEPARATOR%

cd /d "%PROJECT_DIR%"
call %MVN_CMD% clean package -DskipTests
if errorlevel 1 (
    echo [ERROR] 項目打包失敗
    exit /b 1
)
echo [OK] 項目打包成功
echo.
goto :eof

REM 運行應用程序
:run_app
echo %SEPARATOR%
echo 啟動應用程序
echo %SEPARATOR%

cd /d "%PROJECT_DIR%"
echo [WARNING] 應用啟動中... 按 Ctrl+C 停止
echo.
call %MVN_CMD% spring-boot:run
goto :eof

REM 顯示幫助信息
:show_help
echo.
echo Coindesk API Integration Project - 構建腳本
echo %SEPARATOR%
echo.
echo 使用方法: build.bat [命令]
echo.
echo 可用命令:
echo   check       - 檢查 Java 和 Maven 環境
echo   clean       - 清理編譯輸出
echo   compile     - 編譯項目
echo   test        - 運行所有測試
echo   test-transform   - 運行數據轉換測試
echo   test-api         - 運行 Coindesk API 測試
echo   test-service     - 運行 Currency Service 測試
echo   test-controller  - 運行 Currency Controller 測試
echo   test-coindesk    - 運行 Coindesk 控制器測試
echo   package     - 打包應用程序 (生成 JAR)
echo   run         - 運行應用程序
echo   help        - 顯示此幫助信息
echo.
echo 完整工作流示例:
echo   1. build.bat check       - 檢查環境
echo   2. build.bat compile     - 編譯代碼
echo   3. build.bat test        - 運行測試
echo   4. build.bat package     - 打包應用
echo   5. build.bat run         - 運行應用
echo.
goto :eof

REM =========================================================================
REM 處理命令
REM =========================================================================

if /i "%1"=="check" (
    call :check_java
    call :check_maven
    echo [OK] 環境檢查完成
    goto :eof
)

if /i "%1"=="clean" (
    call :check_maven
    call :clean_project
    goto :eof
)

if /i "%1"=="compile" (
    call :check_java
    call :check_maven
    call :compile_project
    goto :eof
)

if /i "%1"=="test" (
    call :check_java
    call :check_maven
    call :run_tests
    goto :eof
)

if /i "%1"=="test-transform" (
    call :check_java
    call :check_maven
    call :run_specific_test "CoinDeskTransformTest"
    goto :eof
)

if /i "%1"=="test-api" (
    call :check_java
    call :check_maven
    call :run_specific_test "CoinDeskApiTest"
    goto :eof
)

if /i "%1"=="test-service" (
    call :check_java
    call :check_maven
    call :run_specific_test "CurrencyServiceTest"
    goto :eof
)

if /i "%1"=="test-controller" (
    call :check_java
    call :check_maven
    call :run_specific_test "CurrencyControllerTest"
    goto :eof
)

if /i "%1"=="test-coindesk" (
    call :check_java
    call :check_maven
    call :run_specific_test "CoinDeskControllerTest"
    goto :eof
)

if /i "%1"=="package" (
    call :check_java
    call :check_maven
    call :package_project
    goto :eof
)

if /i "%1"=="run" (
    call :check_java
    call :check_maven
    call :run_app
    goto :eof
)

if /i "%1"=="help" (
    call :show_help
    goto :eof
)

echo [ERROR] 未知命令: %1
call :show_help
exit /b 1

endlocal
