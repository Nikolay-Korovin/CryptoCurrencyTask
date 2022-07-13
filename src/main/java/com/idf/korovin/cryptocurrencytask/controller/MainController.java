package com.idf.korovin.cryptocurrencytask.controller;

import com.idf.korovin.cryptocurrencytask.dto.CryptoCurrencyDto;
import com.idf.korovin.cryptocurrencytask.entity.CryptoCurrency;
import com.idf.korovin.cryptocurrencytask.service.CryptoCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cryptoapi")
public class MainController {

    @Autowired
    private CryptoCurrencyService cryptoCurrencyService;

    @GetMapping
    public List<CryptoCurrency> getCurrencyList() {
        return cryptoCurrencyService.getCurrencyList();
    }

    @GetMapping("/{id}")
    public CryptoCurrencyDto getCryptoCurrency(@PathVariable("id") Long id){
        return cryptoCurrencyService.getCryptoCurrency(id);
    }

    @GetMapping("/notify")
    public void notify(@RequestParam("username") String username, @RequestParam("symbol") String symbol) {
        cryptoCurrencyService.registerUserAndCrypto(username, symbol);
    }

}
