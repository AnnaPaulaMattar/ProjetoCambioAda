package com.example.cambio.compra.dto;

import com.example.cambio.enums.Moedas;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class RegistrarCompraRequest {

    @NotBlank(message = "CPF é obrigatório")
    private String cpf;

    @NotNull(message = "Moeda é obrigatória")
    private Moedas moeda;

    @NotNull(message = "Quantidade em moeda é obrigatória")
    @Positive(message = "Quantidade deve ser maior que zero")
    private BigDecimal quantidadeEmMoeda;

    @NotBlank(message = "Agência de retirada é obrigatória")
    private String agenciaRetirada;

    // Construtores
    public RegistrarCompraRequest() {
    }

    public RegistrarCompraRequest(
            String cpf,
            Moedas moeda,
            BigDecimal quantidadeEmMoeda,
            String agenciaRetirada) {

        this.cpf = cpf;
        this.moeda = moeda;
        this.quantidadeEmMoeda = quantidadeEmMoeda;
        this.agenciaRetirada = agenciaRetirada;
    }

    // Getters e Setters
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Moedas getMoeda() {
        return moeda;
    }

    public void setMoeda(Moedas moeda) {
        this.moeda = moeda;
    }

    public BigDecimal getQuantidadeEmMoeda() {
        return quantidadeEmMoeda;
    }

    public void setQuantidadeEmMoeda(BigDecimal quantidadeEmMoeda) {
        this.quantidadeEmMoeda = quantidadeEmMoeda;
    }

    public String getAgenciaRetirada() {
        return agenciaRetirada;
    }

    public void setAgenciaRetirada(String agenciaRetirada) {
        this.agenciaRetirada = agenciaRetirada;
    }
}
