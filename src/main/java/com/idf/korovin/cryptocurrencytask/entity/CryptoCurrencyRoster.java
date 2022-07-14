package com.idf.korovin.cryptocurrencytask.entity;

import java.util.stream.Stream;

public enum CryptoCurrencyRoster {
    BTC(90),
    ETH(80),
    SOL(48543);

    private final long id;

    CryptoCurrencyRoster(long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public static Stream<CryptoCurrencyRoster> stream(){
        return Stream.of(CryptoCurrencyRoster.values());
    }
}
