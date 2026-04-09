package com.example.coindesk.controller;

import com.example.coindesk.dto.CurrencyDTO;
import com.example.coindesk.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @GetMapping
    public ResponseEntity<List<CurrencyDTO>> getAll() {
        List<CurrencyDTO> currencies = currencyService.findAll();
        return ResponseEntity.ok(currencies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        CurrencyDTO currency = currencyService.findById(id);
        if (currency != null) {
            return ResponseEntity.ok(currency);
        }
        Map<String, String> error = new HashMap<>();
        error.put("error", "Currency not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<?> getByCode(@PathVariable String code) {
        CurrencyDTO currency = currencyService.findByCode(code);
        if (currency != null) {
            return ResponseEntity.ok(currency);
        }
        Map<String, String> error = new HashMap<>();
        error.put("error", "Currency not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @PostMapping
    public ResponseEntity<CurrencyDTO> create(@RequestBody CurrencyDTO currencyDTO) {
        CurrencyDTO created = currencyService.create(currencyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CurrencyDTO currencyDTO) {
        CurrencyDTO updated = currencyService.update(id, currencyDTO);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        Map<String, String> error = new HashMap<>();
        error.put("error", "Currency not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean deleted = currencyService.delete(id);
        if (deleted) {
            Map<String, String> message = new HashMap<>();
            message.put("message", "Currency deleted successfully");
            return ResponseEntity.ok(message);
        }
        Map<String, String> error = new HashMap<>();
        error.put("error", "Currency not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

}
