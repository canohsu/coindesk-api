package com.example.coindesk.service;

import com.example.coindesk.dto.CoinDeskRawDTO;
import com.example.coindesk.dto.CoinDeskResponseDTO;
import com.example.coindesk.dto.CoinInfoDTO;
import com.example.coindesk.dto.CurrencyDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CoinDeskService {

    private static final String COINDESK_API_URL = "https://kengp3.github.io/blog/coindesk.json";

    @Autowired
    private CurrencyService currencyService;

    public CoinDeskRawDTO fetchCoinDeskData() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(COINDESK_API_URL);
        
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            String jsonResponse = EntityUtils.toString(response.getEntity());
            
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonResponse, CoinDeskRawDTO.class);
        } finally {
            httpClient.close();
        }
    }

    public CoinDeskResponseDTO getCoinDeskDataWithChineseNames() throws IOException {
        CoinDeskRawDTO rawData = fetchCoinDeskData();
        
        // 提取時間並格式化
        String updateTime = extractUpdateTime(rawData);
        
        // 轉換幣種數據
        List<CoinInfoDTO> coins = new ArrayList<>();
        if (rawData.getBpi() != null) {
            for (Map.Entry<String, Map<String, Object>> entry : rawData.getBpi().entrySet()) {
                String currency = entry.getKey();
                Map<String, Object> currencyData = entry.getValue();
                
                CoinInfoDTO coinInfo = new CoinInfoDTO();
                coinInfo.setCurrency(currency);
                
                // 從資料庫查詢中文名稱
                String chineseName = getChineseName(currency);
                coinInfo.setChineseName(chineseName);
                
                // 提取匯率
                if (currencyData.get("rate_float") != null) {
                    coinInfo.setRate(((Number) currencyData.get("rate_float")).doubleValue());
                }
                
                coins.add(coinInfo);
            }
        }
        
        CoinDeskResponseDTO response = new CoinDeskResponseDTO();
        response.setUpdateTime(updateTime);
        response.setCoins(coins);
        
        return response;
    }

    private String extractUpdateTime(CoinDeskRawDTO rawData) {
        if (rawData.getTime() != null && rawData.getTime().get("updated") != null) {
            String updated = rawData.getTime().get("updated").toString();
            try {
                // 解析原時間格式並轉換為所需格式
                SimpleDateFormat inputFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss Z");
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                return outputFormat.format(inputFormat.parse(updated));
            } catch (Exception e) {
                return updated;
            }
        }
        return "";
    }

    private String getChineseName(String code) {
        // 先從資料庫查詢
        CurrencyDTO currencyDTO = currencyService.findByCode(code);
        if (currencyDTO != null) {
            return currencyDTO.getChineseName();
        }
        // 如果未找到，返回幣別代碼
        return code + "(未找到)";
    }

}
