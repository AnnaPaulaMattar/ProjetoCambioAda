package com.example.cambio.cliente.dto;

import com.example.cambio.enums.EstadoCivil;
import com.example.cambio.enums.Sexo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record CadastrarClienteRequest(

        @NotBlank(message = "Nome é obrigatório.")
        String nome,

        @NotBlank(message = "CPF é obrigatório.")
        @Pattern(
                regexp = "\\d{11}",
                message = "CPF deve conter 11 dígitos."
        )
        @CPF(message = "CPF inválido.")
        String cpf,

        @NotNull(message = "Data de nascimento é obrigatória.")
        @Past(message = "Data de nascimento deve estar no passado.")
        LocalDate dataNascimento,

        @NotNull(message = "Estado civil é obrigatório.")
        EstadoCivil estadoCivil,

        @NotNull(message = "Sexo é obrigatório.")
        Sexo sexo

) {
}