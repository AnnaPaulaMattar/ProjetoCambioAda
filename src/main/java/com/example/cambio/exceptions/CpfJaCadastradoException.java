package com.example.cambio.exceptions;

public class CpfJaCadastradoException extends RuntimeException {

    public CpfJaCadastradoException() {
        super("CPF já cadastrado.");
    }
}
