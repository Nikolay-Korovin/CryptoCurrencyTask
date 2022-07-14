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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EnableScheduling
@Service
@AllArgsConstructor
public class AutomaticCurrencySaverService {

    private final RestTemplate restTemplate;

    private final CryptoCurrencyRepository cryptoCurrencyRepository;

    private final PriceChangeNotifierService priceChangeNotifierService;

    private final String currencyResourceUrl = "https://api.coinlore.net/api/ticker/?id=";

    @Scheduled(fixedRate = 60000)
    private void saveCurrencyPriceTry() {
        try {
            saveCurrencyPrice();
        } catch (HttpServerErrorException e) {
            saveCurrencyPriceTry();
        }
    }

    private void saveCurrencyPrice() {
        CryptoCurrency[] cryptoCurrencies;
        List<Long> cryptoIds = listOfCryptoIds();

        cryptoCurrencies = restTemplate.getForObject(currencyResourceUrl + cryptoIds.get(0) + "," + cryptoIds.get(1) +
                "," + cryptoIds.get(2), CryptoCurrency[].class);

        if (cryptoCurrencies != null) {
            cryptoCurrencyRepository.saveAll(Arrays.asList(cryptoCurrencies));
        }
        priceChangeNotifierService.notifyWhenPriceHasChanged();
    }

    private static List<Long> listOfCryptoIds() {
        List<Long> cryptoIds = new ArrayList<>();
        CryptoCurrencyRoster.stream()
                .forEach(currencyRoster -> cryptoIds.add(currencyRoster.getId()));
        return cryptoIds;
    }

}
