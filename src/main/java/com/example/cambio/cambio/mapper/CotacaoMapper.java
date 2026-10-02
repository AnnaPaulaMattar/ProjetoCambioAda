package com.example.cambio.cambio.mapper;

import com.example.cambio.cambio.domain.Cotacao;
import com.example.cambio.cambio.dto.CotacaoResponse;
import org.springframework.stereotype.Component;

@Component
public class CotacaoMapper {

    public CotacaoResponse toCotacaoResponse (Cotacao cotacao){
        return new CotacaoResponse(
                cotacao.moeda(),
                cotacao.valor()
        );
    }
}
