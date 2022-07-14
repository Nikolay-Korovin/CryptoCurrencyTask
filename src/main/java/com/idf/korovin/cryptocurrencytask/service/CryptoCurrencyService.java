package com.idf.korovin.cryptocurrencytask.service;

import com.idf.korovin.cryptocurrencytask.dto.CryptoCurrencyDto;
import com.idf.korovin.cryptocurrencytask.entity.CryptoCurrency;
import com.idf.korovin.cryptocurrencytask.entity.CryptoCurrencyAndUser;
import com.idf.korovin.cryptocurrencytask.entity.CryptoCurrencyRoster;
import com.idf.korovin.cryptocurrencytask.entity.User;
import com.idf.korovin.cryptocurrencytask.repository.CryptoCurrencyAndUserRepository;
import com.idf.korovin.cryptocurrencytask.repository.CryptoCurrencyRepository;
import com.idf.korovin.cryptocurrencytask.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.*;

@Service
@AllArgsConstructor
public class CryptoCurrencyService {

    private final RestTemplate restTemplate;

    private final CryptoCurrencyRepository cryptoCurrencyRepository;

    private final UserRepository userRepository;

    private final CryptoCurrencyAndUserRepository cryptoCurrencyAndUserRepository;

    private final String currencyApiUrl = "https://api.coinlore.net/api/ticker/?id=";

    public List<CryptoCurrency> getCurrencyList() {
        List<CryptoCurrency> cryptoCurrencyList = new ArrayList<>();
        for (CryptoCurrencyRoster cryptoCurrencyRoster : CryptoCurrencyRoster.values()) {
            CryptoCurrency[] arrayOfCryptoCurrency = restTemplate.getForObject(currencyApiUrl + cryptoCurrencyRoster.getId(), CryptoCurrency[].class);
            if (arrayOfCryptoCurrency != null) {

                cryptoCurrencyList.addAll(Arrays.asList(arrayOfCryptoCurrency));
            }
        }
        return cryptoCurrencyList;
    }

    public CryptoCurrencyDto getCryptoCurrency(Long id) {
        return cryptoCurrencyRepository.findById(id)
                .map(CryptoCurrencyDto::convertToDtoWithNameAndPrice)
                .orElse(null);

    }

    @Transactional
    public void registerUserAndCrypto(String username, String symbol) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            CryptoCurrencyAndUser cryptoCurrencyAndUser = new CryptoCurrencyAndUser();
            cryptoCurrencyAndUser.setUser(createUser(username));
            CryptoCurrency currency = cryptoCurrencyRepository.findBySymbol(symbol.toUpperCase(Locale.ROOT));
            cryptoCurrencyAndUser.setCryptoCurrencyPrice(currency.getPrice_usd());
            cryptoCurrencyAndUser.setCryptoCurrencyId(currency.getId());
            cryptoCurrencyAndUser.setCryptoCurrencySymbol(symbol);
            cryptoCurrencyAndUserRepository.save(cryptoCurrencyAndUser);
        }
    }


    public User createUser(String username) {
        return userRepository.save(new User(username));
    }
}
