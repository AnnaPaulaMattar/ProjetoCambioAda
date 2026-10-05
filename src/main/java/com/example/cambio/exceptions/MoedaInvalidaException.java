package com.example.cambio.exceptions;

import com.example.cambio.enums.Moedas;

public class MoedaInvalidaException extends RuntimeException {
    public MoedaInvalidaException(Moedas moedas) {
        super("Moeda '" + moedas.name() + "' não é suportada. Utilize USD ou EUR.");
    }
}
