package com.example.coindesk;

import com.example.coindesk.dto.CoinDeskResponseDTO;
import com.example.coindesk.dto.CoinInfoDTO;
import com.example.coindesk.service.CoinDeskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CoinDeskTransformTest {

    @Autowired
    private CoinDeskService coinDeskService;

    @Test
    public void testTransformCoinDeskData() throws Exception {
        CoinDeskResponseDTO response = coinDeskService.getCoinDeskDataWithChineseNames();
        
        System.out.println("\n=== 資料轉換單元測試 ===");
        System.out.println("測試: 驗證 Coindesk API 數據轉換邏輯");
        
        // 驗證回應物件不為空
        assertNotNull("Response should not be null", response);
        assertNotNull("UpdateTime should not be null", response.getUpdateTime());
        assertNotNull("Coins list should not be null", response.getCoins());
        
        // 驗證數據格式
        String updateTime = response.getUpdateTime();
        assertTrue("UpdateTime should match format yyyy/MM/dd HH:mm:ss", 
                updateTime.matches("\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}"));
        
        System.out.println("\n✓ 時間格式驗證通過");
        System.out.println("更新時間: " + updateTime);
        System.out.println("時間格式: yyyy/MM/dd HH:mm:ss");
        
        // 驗證幣種數據
        List<CoinInfoDTO> coins = response.getCoins();
        assertFalse("Coins list should not be empty", coins.isEmpty());
        assertTrue("Should have at least 2 currencies", coins.size() >= 2);
        
        System.out.println("\n✓ 幣種數據驗證通過");
        System.out.println("幣種總數: " + coins.size());
        System.out.println("幣種列表:");
        
        // 驗證每個幣種的屬性
        for (CoinInfoDTO coin : coins) {
            assertNotNull("Currency code should not be null", coin.getCurrency());
            assertNotNull("Chinese name should not be null", coin.getChineseName());
            assertTrue("Rate should be positive", coin.getRate() > 0);
            
            System.out.println(String.format(
                "  • %s (%s): 匯率 %.2f", 
                coin.getCurrency(), 
                coin.getChineseName(), 
                coin.getRate()
            ));
        }
        
        System.out.println("\n✓ 所有幣種數據驗證通過");
        System.out.println("測試結果: SUCCESS\n");
    }

}

