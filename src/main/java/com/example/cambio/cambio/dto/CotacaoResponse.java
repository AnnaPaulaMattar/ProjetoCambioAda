package com.example.cambio.cambio.dto;

import com.example.cambio.enums.Moedas;

import java.math.BigDecimal;

public record CotacaoResponse (
    Moedas moeda,
    BigDecimal valor
){
}
