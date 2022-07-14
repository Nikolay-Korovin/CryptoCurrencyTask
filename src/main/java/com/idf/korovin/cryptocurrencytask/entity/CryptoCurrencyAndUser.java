package com.idf.korovin.cryptocurrencytask.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CryptoCurrencyAndUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @Column(name = "crypto_currency_id")
    private Long cryptoCurrencyId;

    @Column(name = "crypto_currency_price")
    private Double cryptoCurrencyPrice;
}