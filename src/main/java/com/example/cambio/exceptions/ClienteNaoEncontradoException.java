package com.example.cambio.exceptions;

public class ClienteNaoEncontradoException extends RuntimeException {

    public ClienteNaoEncontradoException(String cpf) {
        super("Cliente com CPF " + cpf + " não encontrado.");
    }
}
