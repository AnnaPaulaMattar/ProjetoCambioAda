package com.example.cambio.cliente.application;

import com.example.cambio.cliente.dto.CadastrarClienteRequest;
import com.example.cambio.cliente.dto.ClienteResponse;
import com.example.cambio.cliente.domain.Cliente;
import com.example.cambio.cliente.exception.ClienteNaoEncontradoException;
import com.example.cambio.cliente.exception.CpfJaCadastradoException;
import com.example.cambio.cliente.infrastructure.ClienteRepository;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public ClienteResponse cadastrar(CadastrarClienteRequest request) {

        if (clienteRepository.existsByCpf(request.getCpf())) {
            throw new CpfJaCadastradoException();
        }

        Cliente cliente = new Cliente(
                request.getNome(),
                request.getCpf(),
                request.getDataNascimento(),
                request.getEstadoCivil(),
                request.getSexo()
        );

        Cliente clienteSalvo = clienteRepository.save(cliente);

        return toResponse(clienteSalvo);
    }

    public ClienteResponse consultarPorCpf(String cpf) {

        Cliente cliente = clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new ClienteNaoEncontradoException(cpf));

        return toResponse(cliente);
    }

    private ClienteResponse toResponse(Cliente cliente) {
        return new ClienteResponse(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getDataNascimento(),
                cliente.getEstadoCivil(),
                cliente.getSexo()
        );
    }
}