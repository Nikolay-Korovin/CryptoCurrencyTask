package com.idf.korovin.cryptocurrencytask.automatic;

import com.idf.korovin.cryptocurrencytask.entity.CryptoCurrency;
import com.idf.korovin.cryptocurrencytask.entity.CryptoCurrencyRoster;
import com.idf.korovin.cryptocurrencytask.repository.CryptoCurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@EnableScheduling
@Service
public class AutomaticCurrencySaver {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CryptoCurrencyRepository cryptoCurrencyRepository;

    private final String currencyResourceUrl = "https://api.coinlore.net/api/ticker/?id=";

    @Scheduled(fixedRate = 60000)
    private void saveCurrencyPrice() {
        CryptoCurrencyRoster.stream()
                .forEach(currencyRoster -> {
                    CryptoCurrency[] cryptoCurrencies = restTemplate
                            .getForObject(currencyResourceUrl + currencyRoster.getId(), CryptoCurrency[].class);
                    if (cryptoCurrencies != null) {
                        cryptoCurrencyRepository.saveAll(Arrays.asList(cryptoCurrencies));
                    }
                });
    }
}
