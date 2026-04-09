package com.example.coindesk.controller;

import com.example.coindesk.dto.CoinDeskRawDTO;
import com.example.coindesk.dto.CoinDeskResponseDTO;
import com.example.coindesk.service.CoinDeskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/coindesk")
public class CoinDeskController {

    @Autowired
    private CoinDeskService coinDeskService;

    @GetMapping("/raw")
    public ResponseEntity<?> getRawData() {
        try {
            CoinDeskRawDTO rawData = coinDeskService.fetchCoinDeskData();
            return ResponseEntity.ok(rawData);
        } catch (IOException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to fetch data: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    @GetMapping("/transformed")
    public ResponseEntity<?> getTransformedData() {
        try {
            CoinDeskResponseDTO response = coinDeskService.getCoinDeskDataWithChineseNames();
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to fetch data: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

}
