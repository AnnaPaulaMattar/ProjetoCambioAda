package com.example.cambio.cambio.application;

import com.example.cambio.cambio.domain.Cotacao;
import com.example.cambio.cambio.dto.CotacaoResponse;
import com.example.cambio.cambio.infrastructure.AwesomeApiClient;
import com.example.cambio.cambio.mapper.CotacaoMapper;
import com.example.cambio.enums.Moedas;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class CambioServiceTest {

    @Mock
    private AwesomeApiClient client;

    @Mock
    private CotacaoMapper mapper;

    @InjectMocks
    private CambioService service;

    @Test
    void deveConsultarCotacaoDolar (){
        // coloque a cotacao atual do dolar via https://economia.awesomeapi.com.br/json/last/USD-BRL

        BigDecimal valoEsperado = new BigDecimal("5.2146");

        CotacaoResponse responseEsperado = new CotacaoResponse(
                Moedas.USD,
                valoEsperado
        );

        when(client.consultarUsd())
                .thenReturn(valoEsperado);

        when(mapper.toCotacaoResponse(
                new Cotacao(Moedas.USD, valoEsperado)
        )).thenReturn(responseEsperado);

        CotacaoResponse resultado = service.cotacao(Moedas.USD);

        assertEquals(responseEsperado, resultado);

        verify(client).consultarUsd();

        verify(mapper).toCotacaoResponse(
                new Cotacao(Moedas.USD, valoEsperado)
        );

        verify(client, never()).consultarEur();
    }

    @Test
    void deveConsultarCotacaoEuro (){
        // coloque a cotacao atual do euro via https://economia.awesomeapi.com.br/json/last/EUR-BRL
        BigDecimal valorEsperado = new BigDecimal("5.861");

        CotacaoResponse responseEsperado = new CotacaoResponse(
                Moedas.USD,
                valorEsperado
        );

        when(client.consultarEur())
                .thenReturn(valorEsperado);

        when(mapper.toCotacaoResponse(
                new Cotacao(Moedas.EUR, valorEsperado)
        )).thenReturn(responseEsperado);

        CotacaoResponse resultado = service.cotacao(Moedas.EUR);

        assertEquals(responseEsperado, resultado);

        verify(client).consultarEur();

        verify(mapper).toCotacaoResponse(
                new Cotacao(Moedas.EUR, valorEsperado)
        );

        verify(client, never()).consultarUsd();

    }

    @Test
    void deveLancarErroCotacaoUsd (){
        when(client.consultarUsd()).thenThrow(new RuntimeException("Erro ao consultar cotação do euro"));

        assertThrows(RuntimeException.class, () -> {
            service.cotacao(Moedas.USD);
        });

        verify(client).consultarUsd();

        verify(client, never()).consultarEur();

        verifyNoInteractions(mapper);

    }

    @Test
    void deveLancarErroCotacaoEur (){
        when(client.consultarEur()).thenThrow(new RuntimeException("Erro ao consultar cotação do euro"));

        assertThrows(RuntimeException.class, () -> {
           service.cotacao(Moedas.EUR);
        });

        verify(client).consultarEur();

        verify(client, never()).consultarUsd();

        verifyNoInteractions(mapper);

    }
}
