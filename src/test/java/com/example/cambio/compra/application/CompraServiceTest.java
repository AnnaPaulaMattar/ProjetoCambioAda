package com.example.cambio.compra.application;

import com.example.cambio.cambio.application.CambioService;
import com.example.cambio.cambio.dto.CotacaoResponse;
import com.example.cambio.cliente.domain.Cliente;
import com.example.cambio.cliente.infrastructure.ClienteRepository;
import com.example.cambio.compra.domain.Compra;
import com.example.cambio.compra.dto.CompraResponse;
import com.example.cambio.compra.dto.RegistrarCompraRequest;
import com.example.cambio.compra.infrastructure.CompraRepository;
import com.example.cambio.compra.mapper.CompraMapper;
import com.example.cambio.enums.EstadoCivil;
import com.example.cambio.enums.Moedas;
import com.example.cambio.enums.Sexo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CompraServiceTest {
    @Mock
    private CompraRepository compraRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private CambioService cambioService;

    @Mock
    private CompraMapper compraMapper;

    private CompraService compraService;

    @BeforeEach
    void setUp() {
        compraService = new CompraService(
                compraRepository,
                clienteRepository,
                cambioService,
                compraMapper
        );
    }

    @Test
    void deveRegistrarCompraComSucesso() {

        RegistrarCompraRequest request = new RegistrarCompraRequest();
        request.setCpf("52998224725");
        request.setMoeda(Moedas.USD);
        request.setQuantidadeEmMoeda(new BigDecimal("100"));
        request.setAgenciaRetirada("São Paulo - SP");

        Cliente cliente = new Cliente(
                "Cliente Teste",
                "52998224725",
                LocalDate.of(1990, 5, 20),
                EstadoCivil.SOLTEIRO,
                Sexo.FEMININO
        );

        CotacaoResponse cotacao = new CotacaoResponse(
                Moedas.USD,
                new BigDecimal("5.00")
        );

        Compra compraSalva = new Compra(
                cliente,
                Moedas.USD,
                new BigDecimal("100"),
                new BigDecimal("5.00"),
                new BigDecimal("500.00"),
                "São Paulo - SP"
        );

        CompraResponse compraResponse = new CompraResponse();
        compraResponse.setId(1L);
        compraResponse.setCpfCliente("52998224725");
        compraResponse.setMoeda(Moedas.USD);
        compraResponse.setQuantidadeEmMoeda(new BigDecimal("100"));
        compraResponse.setTaxaCotacao(new BigDecimal("5.00"));
        compraResponse.setValorTotal(new BigDecimal("500.00"));
        compraResponse.setAgenciaRetirada("São Paulo - SP");

        when(clienteRepository.findByCpf("52998224725"))
                .thenReturn(Optional.of(cliente));

        when(cambioService.cotacao(Moedas.USD))
                .thenReturn(cotacao);

        when(compraRepository.save(any(Compra.class)))
                .thenReturn(compraSalva);

        when(compraMapper.toCompraResponse(compraSalva))
                .thenReturn(compraResponse);

        CompraResponse response =
                compraService.registrarCompra(request);

        assertNotNull(response);

        assertEquals("52998224725",
                response.getCpfCliente());

        assertEquals(Moedas.USD,
                response.getMoeda());

        assertEquals(new BigDecimal("100"),
                response.getQuantidadeEmMoeda());

        assertEquals(new BigDecimal("5.00"),
                response.getTaxaCotacao());

        assertEquals(new BigDecimal("500.00"),
                response.getValorTotal());

        assertEquals("São Paulo - SP",
                response.getAgenciaRetirada());

        verify(clienteRepository)
                .findByCpf("52998224725");

        verify(cambioService)
                .cotacao(Moedas.USD);

        verify(compraRepository)
                .save(any(Compra.class));

        verify(compraMapper)
                .toCompraResponse(compraSalva);
    }

}


