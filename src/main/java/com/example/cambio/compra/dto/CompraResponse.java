package com.example.cambio.compra.dto;

import com.example.cambio.enums.Moedas;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CompraResponse {

    private Long id;
    private String cpfCliente;
    private Moedas moeda;
    private BigDecimal quantidadeEmMoeda;
    private BigDecimal taxaCotacao;
    private BigDecimal valorTotal;
    private String agenciaRetirada;
    private LocalDateTime dataCompra;

    public CompraResponse() {
    }

    public CompraResponse(
            Long id,
            String cpfCliente,
            Moedas moeda,
            BigDecimal quantidadeEmMoeda,
            BigDecimal taxaCotacao,
            BigDecimal valorTotal,
            String agenciaRetirada,
            LocalDateTime dataCompra) {

        this.id = id;
        this.cpfCliente = cpfCliente;
        this.moeda = moeda;
        this.quantidadeEmMoeda = quantidadeEmMoeda;
        this.taxaCotacao = taxaCotacao;
        this.valorTotal = valorTotal;
        this.agenciaRetirada = agenciaRetirada;
        this.dataCompra = dataCompra;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
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

    public BigDecimal getTaxaCotacao() {
        return taxaCotacao;
    }

    public void setTaxaCotacao(BigDecimal taxaCotacao) {
        this.taxaCotacao = taxaCotacao;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getAgenciaRetirada() {
        return agenciaRetirada;
    }

    public void setAgenciaRetirada(String agenciaRetirada) {
        this.agenciaRetirada = agenciaRetirada;
    }

    public LocalDateTime getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(LocalDateTime dataCompra) {
        this.dataCompra = dataCompra;
    }
}
