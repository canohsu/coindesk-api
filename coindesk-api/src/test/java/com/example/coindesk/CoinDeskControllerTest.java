package com.example.coindesk;

import com.example.coindesk.dto.CoinDeskResponseDTO;
import com.example.coindesk.service.CoinDeskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CoinDeskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CoinDeskService coinDeskService;

    @Test
    public void testGetRawCoinDeskData() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/coindesk/raw"))
                .andExpect(status().isOk())
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        System.out.println("=== GET /api/coindesk/raw ===");
        System.out.println("Response: " + content);
        System.out.println("Status: SUCCESS\n");
    }

    @Test
    public void testGetTransformedCoinDeskData() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/coindesk/transformed"))
                .andExpect(status().isOk())
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        System.out.println("=== GET /api/coindesk/transformed ===");
        System.out.println("Response: " + content);
        System.out.println("Status: SUCCESS\n");
    }

}

