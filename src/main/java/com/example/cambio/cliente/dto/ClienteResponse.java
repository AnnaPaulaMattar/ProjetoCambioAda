package com.example.cambio.cliente.dto;

import com.example.cambio.cliente.domain.EstadoCivil;
import com.example.cambio.cliente.domain.Sexo;

import java.time.LocalDate;

public record ClienteResponse(
        Long id,
        String nome,
        String cpf,
        LocalDate dataNascimento,
        EstadoCivil estadoCivil,
        Sexo sexo
) {
}