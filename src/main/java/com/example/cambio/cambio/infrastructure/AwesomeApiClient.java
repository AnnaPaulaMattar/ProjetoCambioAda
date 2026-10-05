package com.example.cambio.cambio.infrastructure;

import com.example.cambio.cambio.dto.AwesomeApiResponse;
import com.example.cambio.exceptions.AwesomeAPIConexionException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;

@Component
public class AwesomeApiClient {


    private final RestClient restClient;

    public AwesomeApiClient(RestClient.Builder builder) {
        this.restClient = builder.baseUrl(
                "https://economia.awesomeapi.com.br"
        ).build();
    }

    public BigDecimal consultarUsd(){
        try {
            AwesomeApiResponse response =
                    restClient.get()
                            .uri("/json/last/USD-BRL")
                            .retrieve()
                            .body(AwesomeApiResponse.class);

            return response.getUsdbrl().getBid();
        } catch (Exception e){
            throw new AwesomeAPIConexionException(e);
        }
    }

    public BigDecimal consultarEur(){
        try {
            AwesomeApiResponse response =
                    restClient.get()
                            .uri("/json/last/EUR-BRL")
                            .retrieve()
                            .body(AwesomeApiResponse.class);

            return response.getEurbrl().getBid();
        } catch (Exception e){
            throw new AwesomeAPIConexionException(e);
        }
    }

}
