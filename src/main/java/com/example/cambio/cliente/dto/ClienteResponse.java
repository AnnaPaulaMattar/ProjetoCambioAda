package com.example.cambio.cliente.dto;

import com.example.cambio.cliente.domain.EstadoCivil;
import com.example.cambio.cliente.domain.Sexo;

import java.time.LocalDate;

public class ClienteResponse {

    private Long id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private EstadoCivil estadoCivil;
    private Sexo sexo;

    public ClienteResponse(
            Long id,
            String nome,
            String cpf,
            LocalDate dataNascimento,
            EstadoCivil estadoCivil,
            Sexo sexo) {

        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.estadoCivil = estadoCivil;
        this.sexo = sexo;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public Sexo getSexo() {
        return sexo;
    }
}
