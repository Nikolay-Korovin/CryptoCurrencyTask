package com.idf.korovin.cryptocurrencytask.service;

import com.idf.korovin.cryptocurrencytask.entity.CryptoCurrency;
import com.idf.korovin.cryptocurrencytask.entity.CryptoCurrencyAndUser;
import com.idf.korovin.cryptocurrencytask.repository.CryptoCurrencyAndUserRepository;
import com.idf.korovin.cryptocurrencytask.repository.CryptoCurrencyRepository;
import com.idf.korovin.cryptocurrencytask.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class PriceChangeNotifierService {

    private final CryptoCurrencyRepository cryptoCurrencyRepository;

    private final CryptoCurrencyAndUserRepository cryptoCurrencyAndUserRepository;

    public void notifyWhenPriceHasChanged() {
        Map<String, CryptoCurrency> availableCryptoCurrencies = getCryptoCurrencies();
        List<CryptoCurrencyAndUser> cryptoCurrencyAndUsers = cryptoCurrencyAndUserRepository.findAll();

        for (CryptoCurrencyAndUser cryptoCurrencyAndUser : cryptoCurrencyAndUsers) {
            double savedPrice = cryptoCurrencyAndUser.getCryptoCurrencyPrice();
            double actualPrice = availableCryptoCurrencies.get(cryptoCurrencyAndUser.getCryptoCurrencySymbol().toUpperCase()).getPrice_usd();
            CryptoCurrency cryptoCurrency = availableCryptoCurrencies.get(cryptoCurrencyAndUser.getCryptoCurrencySymbol().toUpperCase());

            if (priceHasChanged(savedPrice, actualPrice)) {
                logEvent(cryptoCurrency, savedPrice, cryptoCurrencyAndUser.getUser().getUsername());
            }
        }
    }

    private Map<String, CryptoCurrency> getCryptoCurrencies() {
        List<CryptoCurrency> cryptoCurrencyList = cryptoCurrencyRepository.findAll();
        Map<String, CryptoCurrency> savedCurrencies = new HashMap<>();
        cryptoCurrencyList
                .forEach(cryptoCurrency -> savedCurrencies.put(cryptoCurrency.getSymbol(), cryptoCurrency));

        return savedCurrencies;
    }

    private boolean priceHasChanged(Double savedPrice, Double actualPrice) {
        final double onePercentOfSavedPrice = savedPrice / 100;
        double positivePriceChange = savedPrice + onePercentOfSavedPrice;
        double negativePriceChange = savedPrice - onePercentOfSavedPrice;

        return (actualPrice > positivePriceChange) || (actualPrice < negativePriceChange);
    }

    private void logEvent(CryptoCurrency cryptoCurrency, Double savedCurrencyPrice, String username) {
        double actualCurrencyPrice = cryptoCurrency.getPrice_usd();
        String percentDifference = new DecimalFormat("#0.00")
                .format(((actualCurrencyPrice - savedCurrencyPrice) * 100) / savedCurrencyPrice);
        log.warn(username + ": price of " + cryptoCurrency.getSymbol() + " has changed by " + percentDifference + "%");
    }
}
