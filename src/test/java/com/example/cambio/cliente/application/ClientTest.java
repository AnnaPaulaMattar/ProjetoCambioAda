package com.example.cambio.cliente.application;


import com.example.cambio.cliente.domain.Cliente;
import com.example.cambio.enums.EstadoCivil;
import com.example.cambio.enums.Sexo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ClientTest {

    private static final String CPF_VALIDO = "52998224725";
    private static final String PASSWORD = "senha-criptografada";

    @Test
    void deveRetornarCpfComoUsername() {

        Cliente cliente = criarCliente();

        assertThat(cliente.getUsername()).isEqualTo(CPF_VALIDO);
    }

    @Test
    void deveRetornarPasswordCorretamente() {

        Cliente cliente = criarCliente();

        assertThat(cliente.getPassword()).isEqualTo(PASSWORD);
    }

    private Cliente criarCliente() {

        return Cliente.builder()
                .id(1L)
                .nome("Cliente Teste")
                .cpf(CPF_VALIDO)
                .password(PASSWORD)
                .dataNascimento(LocalDate.of(1990, 5, 20))
                .estadoCivil(EstadoCivil.SOLTEIRO)
                .sexo(Sexo.FEMININO)
                .build();
    }

}
