package com.example.cambio.cliente.application;

import com.example.cambio.cliente.domain.Cliente;
import com.example.cambio.cliente.dto.CadastrarClienteRequest;
import com.example.cambio.cliente.dto.ClienteResponse;
import com.example.cambio.cliente.exception.CpfJaCadastradoException;
import com.example.cambio.cliente.infrastructure.ClienteRepository;
import com.example.cambio.enums.EstadoCivil;
import com.example.cambio.enums.Sexo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

        Cliente clientePersistido = Cliente.builder()
                .id(1L)
                .nome("Cliente Teste")
                .cpf(CPF_VALIDO)
                .dataNascimento(LocalDate.of(1990, 5, 20))
                .estadoCivil(EstadoCivil.SOLTEIRO)
                .sexo(Sexo.FEMININO)
                .build();

        when(clienteRepository.save(any(Cliente.class)))
                .thenReturn(clientePersistido);

        ClienteResponse response =
                clienteService.cadastrar(request);

        ArgumentCaptor<Cliente> captor =
                ArgumentCaptor.forClass(Cliente.class);

        verify(clienteRepository)
                .save(captor.capture());

        Cliente clienteSalvo = captor.getValue();

        assertThat(clienteSalvo.getNome())
                .isEqualTo("Cliente Teste");

        assertThat(clienteSalvo.getCpf())
                .isEqualTo(CPF_VALIDO);

        assertThat(clienteSalvo.getDataNascimento())
                .isEqualTo(LocalDate.of(1990, 5, 20));

        assertThat(clienteSalvo.getEstadoCivil())
                .isEqualTo(EstadoCivil.SOLTEIRO);

        assertThat(clienteSalvo.getSexo())
                .isEqualTo(Sexo.FEMININO);

        assertThat(response)
                .isNotNull();

        assertThat(response.id())
                .isEqualTo(1L);

        assertThat(response.nome())
                .isEqualTo("Cliente Teste");

        assertThat(response.cpf())
                .isEqualTo(CPF_VALIDO);

        assertThat(response.dataNascimento())
                .isEqualTo(LocalDate.of(1990, 5, 20));

        assertThat(response.estadoCivil())
                .isEqualTo(EstadoCivil.SOLTEIRO);

        assertThat(response.sexo())
                .isEqualTo(Sexo.FEMININO);

        verify(clienteRepository)
                .existsByCpf(CPF_VALIDO);
    }

    @Test
    void deveLancarExcecaoQuandoCpfJaCadastrado() {

        CadastrarClienteRequest request =
                criarRequestValido();

        when(clienteRepository.existsByCpf(CPF_VALIDO))
                .thenReturn(true);

        assertThatThrownBy(
                () -> clienteService.cadastrar(request)
        )
                .isInstanceOf(CpfJaCadastradoException.class);

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