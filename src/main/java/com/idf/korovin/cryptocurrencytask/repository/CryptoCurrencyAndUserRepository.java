package com.idf.korovin.cryptocurrencytask.repository;

import com.idf.korovin.cryptocurrencytask.entity.CryptoCurrencyAndUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoCurrencyAndUserRepository extends JpaRepository<CryptoCurrencyAndUser, Double> {

}
