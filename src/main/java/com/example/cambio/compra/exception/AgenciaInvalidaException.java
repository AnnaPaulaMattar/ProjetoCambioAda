package com.example.cambio.compra.exception;
import java.util.List;

public class AgenciaInvalidaException extends RuntimeException {
    public AgenciaInvalidaException(
            String agencia,
            List<String> agenciasValidas) {
        super("Agência " + agencia +
                " não é válida. Agências disponíveis: " +
                String.join(", ", agenciasValidas));
    }
}
