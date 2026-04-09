-- ===================================================================
-- Coindesk API Integration Project - Database Schema
-- ===================================================================
-- 幣別資料表結構定義

-- 表名：CURRENCY
-- 用途：儲存幣別代碼與對應的中文名稱映射
-- 資料庫：H2 (In-Memory)
-- 建立日期：2024/01/01

-- ===================================================================
-- CREATE TABLE 語法
-- ===================================================================

CREATE TABLE CURRENCY (
    -- 主鍵：自動遞增的長整數
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主鍵，自動遞增',
    
    -- 幣別代碼：如 USD, EUR, GBP 等
    CODE VARCHAR(10) NOT NULL UNIQUE COMMENT '幣別代碼，如 USD、EUR、GBP 等',
    
    -- 中文名稱：對應的中文名稱
    CHINESE_NAME VARCHAR(100) NOT NULL COMMENT '幣別中文名稱',
    
    -- 建立時間：資料建立的時間戳
    CREATE_TIME VARCHAR(50) COMMENT '建立時間，格式：yyyy/MM/dd HH:mm:ss',
    
    -- 更新時間：資料最後更新的時間戳
    UPDATE_TIME VARCHAR(50) COMMENT '更新時間，格式：yyyy/MM/dd HH:mm:ss'
);

-- ===================================================================
-- 初始化資料
-- ===================================================================

-- USD - 美元
INSERT INTO CURRENCY (CODE, CHINESE_NAME, CREATE_TIME, UPDATE_TIME) 
VALUES ('USD', '美元', '2024/01/01 00:00:00', '2024/01/01 00:00:00');

-- GBP - 英鎊
INSERT INTO CURRENCY (CODE, CHINESE_NAME, CREATE_TIME, UPDATE_TIME) 
VALUES ('GBP', '英鎊', '2024/01/01 00:00:00', '2024/01/01 00:00:00');

-- EUR - 歐元
INSERT INTO CURRENCY (CODE, CHINESE_NAME, CREATE_TIME, UPDATE_TIME) 
VALUES ('EUR', '歐元', '2024/01/01 00:00:00', '2024/01/01 00:00:00');

-- JPY - 日圓
INSERT INTO CURRENCY (CODE, CHINESE_NAME, CREATE_TIME, UPDATE_TIME) 
VALUES ('JPY', '日圓', '2024/01/01 00:00:00', '2024/01/01 00:00:00');

-- CNY - 人民幣
INSERT INTO CURRENCY (CODE, CHINESE_NAME, CREATE_TIME, UPDATE_TIME) 
VALUES ('CNY', '人民幣', '2024/01/01 00:00:00', '2024/01/01 00:00:00');

-- ===================================================================
-- 常用查詢示例
-- ===================================================================

-- 查詢所有幣別
-- SELECT * FROM CURRENCY;

-- 按代碼查詢
-- SELECT * FROM CURRENCY WHERE CODE = 'USD';

-- 按中文名稱查詢
-- SELECT * FROM CURRENCY WHERE CHINESE_NAME = '美元';

-- 查詢幣別總數
-- SELECT COUNT(*) as total FROM CURRENCY;

-- 按代碼排序查詢
-- SELECT * FROM CURRENCY ORDER BY CODE;

-- ===================================================================
-- 常用操作示例
-- ===================================================================

-- 新增幣別
-- INSERT INTO CURRENCY (CODE, CHINESE_NAME, CREATE_TIME, UPDATE_TIME) 
-- VALUES ('CAD', '加拿大元', NOW(), NOW());

-- 修改幣別的中文名稱
-- UPDATE CURRENCY SET CHINESE_NAME = '美國美元', UPDATE_TIME = NOW() 
-- WHERE CODE = 'USD';

-- 刪除幣別
-- DELETE FROM CURRENCY WHERE CODE = 'CAD';

-- ===================================================================
-- 備註
-- ===================================================================
-- 1. 本表採用 H2 In-Memory 資料庫，應用重啟後數據會清空
-- 2. 時間格式統一為 'yyyy/MM/dd HH:mm:ss'
-- 3. CODE 字段設為 UNIQUE，確保不會有重複的幣別代碼
-- 4. 實際應用中可考慮使用 generated_time 或 timestamp 自動記錄時間
-- ===================================================================
