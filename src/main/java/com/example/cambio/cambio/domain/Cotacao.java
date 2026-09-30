package com.example.cambio.cambio.domain;

import com.example.cambio.enums.Moedas;

import java.math.BigDecimal;

public record Cotacao(
        Moedas moeda,
        BigDecimal valor
) {
}
