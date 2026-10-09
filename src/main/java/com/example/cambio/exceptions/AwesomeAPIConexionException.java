package com.example.cambio.exceptions;

public class AwesomeAPIConexionException extends RuntimeException {
    public AwesomeAPIConexionException(Throwable cause) {
        super("Não foi possível obter a cotação no momento. Tente novamente. Causa: " + cause);
    }
}
