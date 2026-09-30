package com.example.cambio.cliente.domain;

import com.example.cambio.enums.EstadoCivil;
import com.example.cambio.enums.Sexo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(
        name = "clientes",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_cliente_cpf",
                        columnNames = "cpf"
                )
        }
)
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, length = 11)
    private String cpf;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoCivil estadoCivil;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sexo sexo;
}