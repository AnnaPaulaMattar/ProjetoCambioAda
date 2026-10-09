package com.example.cambio.cliente.application;

import com.example.cambio.cliente.domain.Cliente;
import com.example.cambio.cliente.dto.CadastrarClienteRequest;
import com.example.cambio.cliente.dto.ClienteResponse;
import com.example.cambio.cliente.infrastructure.ClienteRepository;
import com.example.cambio.exceptions.ClienteNaoEncontradoException;
import com.example.cambio.exceptions.CpfJaCadastradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    public Cliente cadastrar(CadastrarClienteRequest request) {
        try {
            if (clienteRepository.existsByCpf(request.cpf())) {
                throw new CpfJaCadastradoException();
            }

            Cliente cliente = Cliente.builder()
                    .nome(request.nome())
                    .cpf(request.cpf())
                    .dataNascimento(request.dataNascimento())
                    .estadoCivil(request.estadoCivil())
                    .sexo(request.sexo())
                    .build();

            cliente.setPassword(
                    passwordEncoder.encode(request.password())
            );

            return clienteRepository.save(cliente);

        } catch (DataIntegrityViolationException exception) {
            throw new DataIntegrityViolationException(
                    "Erro de integridade de dados",
                    exception
            );
        }
    }

    public ClienteResponse consultarPorCpf(String cpf) {

        Cliente cliente = clienteRepository.findByCpf(cpf)
                .orElseThrow(() ->
                        new ClienteNaoEncontradoException(cpf)
                );

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