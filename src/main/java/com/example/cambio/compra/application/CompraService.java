package com.example.cambio.compra.application;

import com.example.cambio.cambio.application.CambioService;
import com.example.cambio.cambio.dto.CotacaoResponse;
import com.example.cambio.cliente.domain.Cliente;
import com.example.cambio.cliente.exception.ClienteNaoEncontradoException;
import com.example.cambio.cliente.infrastructure.ClienteRepository;
import com.example.cambio.compra.domain.Compra;
import com.example.cambio.compra.dto.CompraResponse;
import com.example.cambio.compra.dto.RegistrarCompraRequest;
import com.example.cambio.compra.infrastructure.CompraRepository;
import com.example.cambio.compra.mapper.CompraMapper;
import com.example.cambio.enums.Moedas;
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

    // Lista de agências válidas
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

        // 1. Validar se cliente existe
        Cliente cliente = clienteRepository.findByCpf(request.getCpf())
                .orElseThrow(() -> new ClienteNaoEncontradoException(
                        "Cliente com CPF " + request.getCpf() + " não encontrado"
                ));

        // 2. Validar se agência é válida
        validarAgencia(request.getAgenciaRetirada());

        // 3. Obter cotação da moeda
        CotacaoResponse cotacao = cambioService.cotacao(request.getMoeda());

        // 4. Calcular valor total
        BigDecimal valorTotal = request.getQuantidadeEmMoeda()
                .multiply(cotacao.valor());

        // 5. Criar e persistir compra
        Compra compra = new Compra(
                cliente,
                request.getMoeda(),
                request.getQuantidadeEmMoeda(),
                cotacao.valor(),
                valorTotal,
                request.getAgenciaRetirada()
        );

        Compra compraSalva = compraRepository.save(compra);

        // 6. Retornar resposta
        return compraMapper.toCompraResponse(compraSalva);
    }

    public CompraResponse consultarCompra(Long id) {
        Compra compra = compraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra com ID " + id + " não encontrada"));

        return compraMapper.toCompraResponse(compra);
    }

    public List<CompraResponse> consultarComprasPorCpf(String cpf) {
        // Validar se cliente existe
        clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new ClienteNaoEncontradoException(
                        "Cliente com CPF " + cpf + " não encontrado"
                ));

        return compraRepository.findByClienteCpf(cpf)
                .stream()
                .map(compraMapper::toCompraResponse)
                .collect(Collectors.toList());
    }

    private void validarAgencia(String agencia) {
        if (!AGENCIAS_VALIDAS.contains(agencia)) {
            throw new RuntimeException("Agência " + agencia + " não é válida. " +
                    "Agências disponíveis: " + String.join(", ", AGENCIAS_VALIDAS));
        }
    }
}
