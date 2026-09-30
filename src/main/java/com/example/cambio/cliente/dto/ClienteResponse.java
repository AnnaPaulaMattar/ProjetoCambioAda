package com.example.cambio.cliente.dto;

import com.example.cambio.enums.EstadoCivil;
import com.example.cambio.enums.Sexo;

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