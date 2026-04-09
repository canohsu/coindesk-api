package com.example.coindesk;

import com.example.coindesk.dto.CoinDeskRawDTO;
import com.example.coindesk.service.CoinDeskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CoinDeskApiTest {

    @Autowired
    private CoinDeskService coinDeskService;

    @Test
    public void testFetchCoinDeskData() throws Exception {
        System.out.println("\n=== Coindesk API 遠程調用測試 ===");
        System.out.println("測試: 驗證遠程 API 調用和原始數據解析");
        
        CoinDeskRawDTO rawData = coinDeskService.fetchCoinDeskData();
        assertNotNull("Raw data should not be null", rawData);
        assertNotNull("Time object should not be null", rawData.getTime());
        assertNotNull("BPI object should not be null", rawData.getBpi());
        
        Map<String, Object> timeMap = rawData.getTime();
        assertNotNull("Updated time should exist", timeMap.get("updated"));
        String updatedTime = timeMap.get("updated").toString();
        
        System.out.println("\n✓ API 連接成功");
        System.out.println("更新時間: " + updatedTime);
        
        // 驗證 BPI 數據結構
        Map<String, Map<String, Object>> bpi = rawData.getBpi();
        assertFalse("BPI should not be empty", bpi.isEmpty());
        assertTrue("Should have at least 2 currencies", bpi.size() >= 2);
        
        System.out.println("\n✓ 數據結構驗證通過");
        System.out.println("幣別數量: " + bpi.size());
        System.out.println("幣別列表:");
        
        // 顯示所有幣別和匯率
        for (Map.Entry<String, Map<String, Object>> entry : bpi.entrySet()) {
            String currency = entry.getKey();
            Map<String, Object> currencyData = entry.getValue();
            
            Object rateObj = currencyData.get("rate_float");
            double rate = rateObj != null ? ((Number) rateObj).doubleValue() : 0;
            
            System.out.println(String.format("  • %s: 匯率 %.2f", currency, rate));
        }
        
        System.out.println("\n✓ 所有數據驗證通過");
        System.out.println("測試結果: SUCCESS\n");
    }

}

