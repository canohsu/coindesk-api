package com.example.coindesk;

import com.example.coindesk.dto.CurrencyDTO;
import com.example.coindesk.service.CurrencyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurrencyServiceTest {

    @Autowired
    private CurrencyService currencyService;

    @Test
    public void testFindAllCurrencies() {
        System.out.println("\n=== 幣別數據庫操作測試 ===");
        System.out.println("測試 1: 查詢所有幣別");
        
        List<CurrencyDTO> currencies = currencyService.findAll();
        assertNotNull(currencies);
        assertFalse(currencies.isEmpty());
        
        System.out.println("✓ 查詢成功");
        System.out.println("總幣別數: " + currencies.size());
        System.out.println("幣別列表:");
        currencies.forEach(c -> {
            System.out.println(String.format("  • ID: %d, 代碼: %s, 中文名: %s", c.getId(), c.getCode(), c.getChineseName()));
        });
    }

    @Test
    public void testFindCurrencyByCode() {
        System.out.println("\n測試 2: 按代碼查詢幣別");
        
        CurrencyDTO currency = currencyService.findByCode("USD");
        assertNotNull(currency);
        assertEquals("USD", currency.getCode());
        assertEquals("美元", currency.getChineseName());
        
        System.out.println("✓ 查詢成功");
        System.out.println(String.format("  代碼: %s, 中文名: %s, 建立時間: %s", 
            currency.getCode(), currency.getChineseName(), currency.getCreateTime()));
    }

    @Test
    public void testCreateCurrency() {
        System.out.println("\n測試 3: 新增幣別");
        
        CurrencyDTO newCurrency = new CurrencyDTO();
        newCurrency.setCode("CAD");
        newCurrency.setChineseName("加拿大元");

        CurrencyDTO created = currencyService.create(newCurrency);
        assertNotNull(created);
        assertNotNull(created.getId());
        assertEquals("CAD", created.getCode());
        assertEquals("加拿大元", created.getChineseName());
        assertNotNull(created.getCreateTime());
        
        System.out.println("✓ 新增成功");
        System.out.println(String.format("  ID: %d, 代碼: %s, 中文名: %s, 建立時間: %s", 
            created.getId(), created.getCode(), created.getChineseName(), created.getCreateTime()));
    }

    @Test
    public void testUpdateCurrency() {
        System.out.println("\n測試 4: 修改幣別");
        
        // First find a currency
        CurrencyDTO currency = currencyService.findByCode("EUR");
        assertNotNull(currency);
        System.out.println("找到原始數據: " + currency.getCode() + " - " + currency.getChineseName());

        // Update it
        currency.setChineseName("歐盟元(已更新)");
        CurrencyDTO updated = currencyService.update(currency.getId(), currency);
        assertNotNull(updated);
        assertEquals("歐盟元(已更新)", updated.getChineseName());
        
        System.out.println("✓ 修改成功");
        System.out.println(String.format("  ID: %d, 代碼: %s, 新名稱: %s, 更新時間: %s", 
            updated.getId(), updated.getCode(), updated.getChineseName(), updated.getUpdateTime()));
    }

    @Test
    public void testDeleteCurrency() {
        System.out.println("\n測試 5: 刪除幣別");
        
        // First create a currency to delete
        CurrencyDTO newCurrency = new CurrencyDTO();
        newCurrency.setCode("AUD");
        newCurrency.setChineseName("澳洲元");
        CurrencyDTO created = currencyService.create(newCurrency);
        Long id = created.getId();
        System.out.println("建立待刪除幣別: " + created.getCode() + " (ID: " + id + ")");

        // Delete it
        boolean deleted = currencyService.delete(id);
        assertTrue(deleted);

        // Verify it's deleted
        CurrencyDTO found = currencyService.findById(id);
        assertNull(found);
        
        System.out.println("✓ 刪除成功");
        System.out.println("  ID " + id + " 的幣別已刪除\n");
    }

}

