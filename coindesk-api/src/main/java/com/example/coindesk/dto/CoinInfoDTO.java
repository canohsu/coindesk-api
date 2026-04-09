package com.example.coindesk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoinInfoDTO {

    private String currency;
    private String chineseName;
    private double rate;

}
