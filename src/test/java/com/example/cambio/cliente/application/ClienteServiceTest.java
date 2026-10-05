package com.example.cambio.cliente.application;

import com.example.cambio.cliente.domain.Cliente;
import com.example.cambio.enums.EstadoCivil;
import com.example.cambio.enums.Sexo;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        clienteService = new ClienteService(clienteRepository);
    }

    @Test
    void deveCadastrarClienteComSucesso() {

        CadastrarClienteRequest request = new CadastrarClienteRequest();
        request.setNome("Cliente Teste");
        request.setCpf("52998224725");
        request.setDataNascimento(LocalDate.of(1990, 5, 20));
        request.setEstadoCivil(EstadoCivil.SOLTEIRO);
        request.setSexo(Sexo.FEMININO);

        when(clienteRepository.existsByCpf("52998224725"))
                .thenReturn(false);

        Cliente clienteSalvo = new Cliente(
                "Cliente Teste",
                "52998224725",
                LocalDate.of(1990, 5, 20),
                EstadoCivil.SOLTEIRO,
                Sexo.FEMININO
        );

        when(clienteRepository.save(any(Cliente.class)))
                .thenReturn(clienteSalvo);

        ClienteResponse response = clienteService.cadastrar(request);

        assertNotNull(response);
        assertEquals("Cliente Teste", response.getNome());
        assertEquals("52998224725", response.getCpf());
        assertEquals(
                LocalDate.of(1990, 5, 20),
                response.getDataNascimento()
        );
        assertEquals(
                EstadoCivil.SOLTEIRO,
                response.getEstadoCivil()
        );
        assertEquals(
                Sexo.FEMININO,
                response.getSexo()
        );

        verify(clienteRepository)
                .existsByCpf("52998224725");

        verify(clienteRepository)
                .save(any(Cliente.class));
    }

    @Test
    void deveLancarExcecaoQuandoCpfJaCadastrado() {

        CadastrarClienteRequest request = new CadastrarClienteRequest();
        request.setNome("Cliente Teste");
        request.setCpf("52998224725");
        request.setDataNascimento(LocalDate.of(1990, 5, 20));
        request.setEstadoCivil(EstadoCivil.SOLTEIRO);
        request.setSexo(Sexo.FEMININO);

        when(clienteRepository.existsByCpf("52998224725"))
                .thenReturn(true);

        assertThrows(
                CpfJaCadastradoException.class,
                () -> clienteService.cadastrar(request)
        );

        verify(clienteRepository)
                .existsByCpf("52998224725");

        verify(clienteRepository, never())
                .save(any(Cliente.class));
    }
}