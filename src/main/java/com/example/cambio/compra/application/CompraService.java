package com.example.cambio.compra.application;

import com.example.cambio.cambio.application.CambioService;
import com.example.cambio.cambio.dto.CotacaoResponse;
import com.example.cambio.cliente.domain.Cliente;
import com.example.cambio.cliente.infrastructure.ClienteRepository;
import com.example.cambio.compra.domain.Compra;
import com.example.cambio.compra.dto.CompraResponse;
import com.example.cambio.compra.dto.RegistrarCompraRequest;
import com.example.cambio.compra.exception.AgenciaInvalidaException;
import com.example.cambio.compra.exception.CompraNaoEncontradaException;
import com.example.cambio.compra.infrastructure.CompraRepository;
import com.example.cambio.compra.mapper.CompraMapper;
import com.example.cambio.exceptions.ClienteNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompraService {

    private final CompraRepository compraRepository;
    private final ClienteRepository clienteRepository;
    private final CambioService cambioService;
    private final CompraMapper compraMapper;

    private static final List<String> AGENCIAS_VALIDAS = List.of(
            "São Paulo - SP",
            "Rio de Janeiro - RJ",
            "Belo Horizonte - MG",
            "Curitiba - PR",
            "Porto Alegre - RS",
            "Salvador - BA",
            "Brasília - DF"
    );

    public CompraResponse registrarCompra(RegistrarCompraRequest request) {
        Cliente cliente = clienteRepository.findByCpf(request.getCpf())
                .orElseThrow(() -> new ClienteNaoEncontradoException(request.getCpf()));

        validarAgencia(request.getAgenciaRetirada());

        CotacaoResponse cotacao = cambioService.cotacao(request.getMoeda());

        BigDecimal valorTotal = request.getQuantidadeEmMoeda()
                .multiply(cotacao.valor());

        Compra compra = new Compra(
                cliente,
                request.getMoeda(),
                request.getQuantidadeEmMoeda(),
                cotacao.valor(),
                valorTotal,
                request.getAgenciaRetirada()
        );

        Compra compraSalva = compraRepository.save(compra);

        return compraMapper.toCompraResponse(compraSalva);
    }

    public CompraResponse consultarCompra(Long id) {
        Compra compra = compraRepository.findById(id)
                .orElseThrow(() -> new CompraNaoEncontradaException(id));

        return compraMapper.toCompraResponse(compra);
    }

    public List<CompraResponse> consultarComprasPorCpf(String cpf) {
        clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new ClienteNaoEncontradoException(cpf));

        return compraRepository.findByClienteCpf(cpf)
                .stream()
                .map(compraMapper::toCompraResponse)
                .collect(Collectors.toList());
    }

    private void validarAgencia(String agencia) {
        if (!AGENCIAS_VALIDAS.contains(agencia)) {
            throw new AgenciaInvalidaException(agencia, AGENCIAS_VALIDAS);
        }
    }
}
