package com.example.cambio.compra.domain;

import com.example.cambio.cliente.domain.Cliente;
import com.example.cambio.enums.Moedas;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "compras")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Moedas moeda;

    @Column(nullable = false)
    private BigDecimal quantidadeEmMoeda;

    @Column(nullable = false)
    private BigDecimal taxaCotacao;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    @Column(nullable = false)
    private String agenciaRetirada;

    @Column(nullable = false)
    private LocalDateTime dataCompra;

    protected Compra() {
    }

    public Compra(
            Cliente cliente,
            Moedas moeda,
            BigDecimal quantidadeEmMoeda,
            BigDecimal taxaCotacao,
            BigDecimal valorTotal,
            String agenciaRetirada) {

        this.cliente = cliente;
        this.moeda = moeda;
        this.quantidadeEmMoeda = quantidadeEmMoeda;
        this.taxaCotacao = taxaCotacao;
        this.valorTotal = valorTotal;
        this.agenciaRetirada = agenciaRetirada;
        this.dataCompra = LocalDateTime.now();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Moedas getMoeda() {
        return moeda;
    }

    public BigDecimal getQuantidadeEmMoeda() {
        return quantidadeEmMoeda;
    }

    public BigDecimal getTaxaCotacao() {
        return taxaCotacao;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public String getAgenciaRetirada() {
        return agenciaRetirada;
    }

    public LocalDateTime getDataCompra() {
        return dataCompra;
    }
}
