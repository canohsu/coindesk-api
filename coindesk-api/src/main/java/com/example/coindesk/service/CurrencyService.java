package com.example.coindesk.service;

import com.example.coindesk.dto.CurrencyDTO;
import com.example.coindesk.entity.Currency;
import com.example.coindesk.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    public List<CurrencyDTO> findAll() {
        List<Currency> currencies = currencyRepository.findAll();
        List<CurrencyDTO> dtoList = new ArrayList<>();
        for (Currency currency : currencies) {
            dtoList.add(convertToDTO(currency));
        }
        return dtoList;
    }

    public CurrencyDTO findById(Long id) {
        Currency currency = currencyRepository.findById(id).orElse(null);
        return currency != null ? convertToDTO(currency) : null;
    }

    public CurrencyDTO findByCode(String code) {
        Currency currency = currencyRepository.findByCode(code);
        return currency != null ? convertToDTO(currency) : null;
    }

    public CurrencyDTO create(CurrencyDTO currencyDTO) {
        Currency currency = new Currency();
        currency.setCode(currencyDTO.getCode());
        currency.setChineseName(currencyDTO.getChineseName());
        currency.setCreateTime(getCurrentTime());
        currency.setUpdateTime(getCurrentTime());
        
        Currency savedCurrency = currencyRepository.save(currency);
        return convertToDTO(savedCurrency);
    }

    public CurrencyDTO update(Long id, CurrencyDTO currencyDTO) {
        Currency currency = currencyRepository.findById(id).orElse(null);
        if (currency != null) {
            if (currencyDTO.getCode() != null) {
                currency.setCode(currencyDTO.getCode());
            }
            if (currencyDTO.getChineseName() != null) {
                currency.setChineseName(currencyDTO.getChineseName());
            }
            currency.setUpdateTime(getCurrentTime());
            
            Currency updatedCurrency = currencyRepository.save(currency);
            return convertToDTO(updatedCurrency);
        }
        return null;
    }

    public boolean delete(Long id) {
        if (currencyRepository.existsById(id)) {
            currencyRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public CurrencyDTO convertToDTO(Currency currency) {
        CurrencyDTO dto = new CurrencyDTO();
        dto.setId(currency.getId());
        dto.setCode(currency.getCode());
        dto.setChineseName(currency.getChineseName());
        dto.setCreateTime(currency.getCreateTime());
        dto.setUpdateTime(currency.getUpdateTime());
        return dto;
    }

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return sdf.format(new Date());
    }

}
