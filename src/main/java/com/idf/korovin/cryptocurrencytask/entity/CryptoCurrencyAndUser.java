package com.idf.korovin.cryptocurrencytask.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CryptoCurrencyAndUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private User user;




    //@ElementCollection(fetch = FetchType.EAGER)
    //private Map<String, Double> savedCurrencies = new HashMap<>();
}