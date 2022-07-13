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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.*;

@Service
@AllArgsConstructor  //переделать так же везде
public class CryptoCurrencyService {


    private final RestTemplate restTemplate;//переделать так же везде


    private final CryptoCurrencyRepository cryptoCurrencyRepository;//переделать так же везде


    private final UserRepository userRepository;//переделать так же везде


    private final CryptoCurrencyAndUserRepository cryptoCurrencyAndUserRepository;//переделать так же везде

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
        if (user.isPresent()){
            CryptoCurrency currency = cryptoCurrencyRepository.findBySymbol(symbol.toUpperCase(Locale.ROOT));
//            CryptoCurrencyAndUser cryptoCurrencyAndUser = new CryptoCurrencyAndUser();
//            cryptoCurrencyAndUser.setUser(user.get());
//            cryptoCurrencyAndUser.getSavedCurrencies().put(currency.getSymbol(),currency.getPrice_usd());
//            cryptoCurrencyAndUserRepository.save(cryptoCurrencyAndUser);

        }
    }


}
