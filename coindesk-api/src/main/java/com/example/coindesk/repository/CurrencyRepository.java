package com.example.coindesk.repository;

import com.example.coindesk.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Currency findByCode(String code);

    Currency findByChineseName(String chineseName);

}
