package com.example.cambio.cliente.application;

import com.example.cambio.cliente.domain.Cliente;
import com.example.cambio.cliente.domain.EstadoCivil;
import com.example.cambio.cliente.domain.Sexo;
import com.example.cambio.cliente.dto.CadastrarClienteRequest;
import com.example.cambio.cliente.dto.ClienteResponse;
import com.example.cambio.cliente.exception.CpfJaCadastradoException;
import com.example.cambio.cliente.infrastructure.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    private static final String CPF_VALIDO = "52998224725";

    @Mock
    private ClienteRepository clienteRepository;

    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        clienteService = new ClienteService(clienteRepository);
    }

    @Test
    void deveCadastrarClienteComSucesso() {

        CadastrarClienteRequest request = criarRequestValido();

        when(clienteRepository.existsByCpf(CPF_VALIDO))
                .thenReturn(false);

        Cliente clienteSalvo = Cliente.builder()
                .id(1L)
                .nome("Cliente Teste")
                .cpf(CPF_VALIDO)
                .dataNascimento(LocalDate.of(1990, 5, 20))
                .estadoCivil(EstadoCivil.SOLTEIRO)
                .sexo(Sexo.FEMININO)
                .build();

        when(clienteRepository.save(any(Cliente.class)))
                .thenReturn(clienteSalvo);

        ClienteResponse response = clienteService.cadastrar(request);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Cliente Teste", response.nome());
        assertEquals(CPF_VALIDO, response.cpf());
        assertEquals(
                LocalDate.of(1990, 5, 20),
                response.dataNascimento()
        );
        assertEquals(
                EstadoCivil.SOLTEIRO,
                response.estadoCivil()
        );
        assertEquals(
                Sexo.FEMININO,
                response.sexo()
        );

        verify(clienteRepository)
                .existsByCpf(CPF_VALIDO);

        verify(clienteRepository)
                .save(any(Cliente.class));
    }

    @Test
    void deveLancarExcecaoQuandoCpfJaCadastrado() {

        CadastrarClienteRequest request = criarRequestValido();

        when(clienteRepository.existsByCpf(CPF_VALIDO))
                .thenReturn(true);

        CpfJaCadastradoException exception = assertThrows(
                CpfJaCadastradoException.class,
                () -> clienteService.cadastrar(request)
        );

        assertEquals(
                "CPF já cadastrado.",
                exception.getMessage()
        );

        verify(clienteRepository)
                .existsByCpf(CPF_VALIDO);

        verify(clienteRepository, never())
                .save(any(Cliente.class));
    }

    private CadastrarClienteRequest criarRequestValido() {
        return new CadastrarClienteRequest(
                "Cliente Teste",
                CPF_VALIDO,
                LocalDate.of(1990, 5, 20),
                EstadoCivil.SOLTEIRO,
                Sexo.FEMININO
        );
    }
}