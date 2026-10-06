package com.example.cambio.compra.exception;

public class CompraNaoEncontradaException extends RuntimeException {
    public CompraNaoEncontradaException(Long id) {
        super("Compra com ID " + id + " não encontrada");
    }
}