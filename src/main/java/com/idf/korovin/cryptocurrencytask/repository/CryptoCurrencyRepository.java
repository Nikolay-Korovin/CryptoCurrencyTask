package com.idf.korovin.cryptocurrencytask.repository;

import com.idf.korovin.cryptocurrencytask.entity.CryptoCurrency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoCurrencyRepository extends JpaRepository<CryptoCurrency, Long> {
    CryptoCurrency findBySymbol(String symbol);
}
