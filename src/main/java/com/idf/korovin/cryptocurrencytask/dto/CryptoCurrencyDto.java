package com.idf.korovin.cryptocurrencytask.dto;

import com.idf.korovin.cryptocurrencytask.entity.CryptoCurrency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CryptoCurrencyDto {

    private String name;
    private double price;

    public static CryptoCurrencyDto convertToDtoWithNameAndPrice(CryptoCurrency cryptoCurrency){
        return new CryptoCurrencyDto(cryptoCurrency.getName(),cryptoCurrency.getPrice_usd());
    }

}
