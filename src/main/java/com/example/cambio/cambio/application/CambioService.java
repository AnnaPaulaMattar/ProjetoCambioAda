package com.example.cambio.cambio.application;

import com.example.cambio.cambio.domain.Cotacao;
import com.example.cambio.cambio.dto.CotacaoResponse;
import com.example.cambio.cambio.infrastructure.AwesomeApiClient;
import com.example.cambio.cambio.mapper.CotacaoMapper;
import com.example.cambio.enums.Moedas;
import com.example.cambio.exceptions.AwesomeAPIConexionException;
import com.example.cambio.exceptions.MoedaInvalidaException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CambioService {


    private final AwesomeApiClient client;
    private final CotacaoMapper mapper;

    public CotacaoResponse cotacao(Moedas moeda){

        try {
            BigDecimal valor = BigDecimal.valueOf(0.0);
            if (moeda.equals(Moedas.USD)) {
                valor = client.consultarUsd();
            } else if (moeda.equals(Moedas.EUR)) {
                valor = client.consultarEur();
            } else {
                throw new MoedaInvalidaException(moeda);
            }
            Cotacao cotacao = new Cotacao(moeda, valor);

            return mapper.toCotacaoResponse(cotacao);
        } catch (RestClientException e){
            throw new AwesomeAPIConexionException(e);
        }

    }
}
