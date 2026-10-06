package com.example.cambio.shared.exception;

import com.example.cambio.cliente.exception.ClienteNaoEncontradoException;
import com.example.cambio.cliente.exception.CpfJaCadastradoException;
import com.example.cambio.compra.exception.AgenciaInvalidaException;
import com.example.cambio.compra.exception.CompraNaoEncontradaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CpfJaCadastradoException.class)
    public ResponseEntity<ApiErrorResponse> tratarCpfJaCadastrado(
            CpfJaCadastradoException exception) {

        ApiErrorResponse erro = new ApiErrorResponse(
                HttpStatus.CONFLICT.value(),
                exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(erro);
    }

    @ExceptionHandler(ClienteNaoEncontradoException.class)
    public ResponseEntity<ApiErrorResponse> tratarClienteNaoEncontrado(
            ClienteNaoEncontradoException exception) {

        ApiErrorResponse erro = new ApiErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> tratarDadosInvalidos(
            MethodArgumentNotValidException exception) {

        String mensagem = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(erro -> erro.getDefaultMessage())
                .orElse("Dados inválidos.");

        ApiErrorResponse erro = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                mensagem
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(erro);
    }

    @ExceptionHandler(CompraNaoEncontradaException.class)
    public ResponseEntity<ApiErrorResponse> tratarCompraNaoEncontrada(
            CompraNaoEncontradaException exception) {
        ApiErrorResponse erro = new ApiErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(erro);
    }

    @ExceptionHandler(AgenciaInvalidaException.class)
    public ResponseEntity<ApiErrorResponse> tratarAgenciaInvalida(
            AgenciaInvalidaException exception) {
        ApiErrorResponse erro = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(erro);
    }
}