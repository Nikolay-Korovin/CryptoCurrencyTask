package com.idf.korovin.cryptocurrencytask.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = "crypto_currency")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties
public class CryptoCurrency {

    @Id
    private long id;
    @Column(name = "symbol")
    private String symbol;
    @Column(name = "name")
    private String name;
    @Column(name = "nameid")
    private String nameid;
    @Column(name = "rank")
    private int rank;
    @Column(name = "price_usd")
    private double price_usd;

}
