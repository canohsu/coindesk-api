package com.example.coindesk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoinDeskResponseDTO {

    private String updateTime;
    private List<CoinInfoDTO> coins;

}
