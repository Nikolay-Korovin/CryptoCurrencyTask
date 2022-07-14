package com.idf.korovin.cryptocurrencytask.service;

import com.idf.korovin.cryptocurrencytask.entity.CryptoCurrency;
import com.idf.korovin.cryptocurrencytask.entity.CryptoCurrencyRoster;
import com.idf.korovin.cryptocurrencytask.repository.CryptoCurrencyRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@EnableScheduling
@Service
@AllArgsConstructor
public class AutomaticCurrencySaverService {

    private final RestTemplate restTemplate;

    private final CryptoCurrencyRepository cryptoCurrencyRepository;

    private final PriceChangeNotifierService priceChangeNotifierService;

    private final String currencyResourceUrl = "https://api.coinlore.net/api/ticker/?id=";

    @Scheduled(fixedRate = 60000)
    private void saveCurrencyPriceTry(){
        try {
            saveCurrencyPrice();
        }catch (HttpServerErrorException e){
            saveCurrencyPriceTry();
        }
    }

    private void saveCurrencyPrice() {
        CryptoCurrencyRoster.stream()
                .forEach(currencyRoster -> {
                        CryptoCurrency[] cryptoCurrencies = restTemplate
                                .getForObject(currencyResourceUrl + currencyRoster.getId(), CryptoCurrency[].class);
                        if (cryptoCurrencies != null) {
                            cryptoCurrencyRepository.saveAll(Arrays.asList(cryptoCurrencies));
                        }
                });
        priceChangeNotifierService.notifyWhenPriceHasChanged();
    }
}
