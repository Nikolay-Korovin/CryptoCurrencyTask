package com.idf.korovin.cryptocurrencytask;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CryptoCurrencyTaskApplication {

    HttpClient httpClient = HttpClientBuilder.create().build();
    ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(requestFactory);
    }

    public static void main(String[] args) {
        SpringApplication.run(CryptoCurrencyTaskApplication.class, args);
    }

}
