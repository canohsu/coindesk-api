package com.example.coindesk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoinDeskRawDTO {

    private Map<String, Object> time;
    private Map<String, Map<String, Object>> bpi;

}
