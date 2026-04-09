package com.example.coindesk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllCurrencies() throws Exception {
        System.out.println("\n=== 幣別 CRUD API 端點測試 ===");
        System.out.println("測試 1: 查詢所有幣別 (GET /api/currencies)");
        
        MvcResult result = mockMvc.perform(get("/api/currencies"))
                .andExpect(status().isOk())
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        System.out.println("✓ GET /api/currencies - SUCCESS");
        System.out.println("Response: " + content);
    }

    @Test
    public void testGetCurrencyByCode() throws Exception {
        System.out.println("\n測試 2: 按代碼查詢幣別 (GET /api/currencies/code/USD)");
        
        MvcResult result = mockMvc.perform(get("/api/currencies/code/USD"))
                .andExpect(status().isOk())
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        System.out.println("✓ GET /api/currencies/code/USD - SUCCESS");
        System.out.println("Response: " + content);
    }

    @Test
    public void testCreateCurrency() throws Exception {
        System.out.println("\n測試 3: 新增幣別 (POST /api/currencies)");
        
        String currency = "{\"code\":\"CHF\",\"chineseName\":\"瑞士法郎\"}";
        MvcResult result = mockMvc.perform(post("/api/currencies")
                .contentType("application/json")
                .content(currency))
                .andExpect(status().isCreated())
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        System.out.println("✓ POST /api/currencies - SUCCESS");
        System.out.println("Request: " + currency);
        System.out.println("Response: " + content);
    }

    @Test
    public void testUpdateCurrency() throws Exception {
        System.out.println("\n測試 4: 修改幣別 (PUT /api/currencies/1)");
        
        String currency = "{\"code\":\"USD\",\"chineseName\":\"美元(已驗證)\"}";
        MvcResult result = mockMvc.perform(put("/api/currencies/1")
                .contentType("application/json")
                .content(currency))
                .andExpect(status().isOk())
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        System.out.println("✓ PUT /api/currencies/1 - SUCCESS");
        System.out.println("Request: " + currency);
        System.out.println("Response: " + content);
    }

    @Test
    public void testDeleteCurrency() throws Exception {
        System.out.println("\n測試 5: 刪除幣別 (DELETE /api/currencies/2)");
        
        MvcResult result = mockMvc.perform(delete("/api/currencies/2"))
                .andExpect(status().isOk())
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        System.out.println("✓ DELETE /api/currencies/2 - SUCCESS");
        System.out.println("Response: " + content + "\n");
    }

}

