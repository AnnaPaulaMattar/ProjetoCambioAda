package com.example.cambio.cliente.application;

import com.example.cambio.cliente.dto.CadastrarClienteRequest;
import com.example.cambio.cliente.dto.ClienteResponse;
import com.example.cambio.cliente.domain.Cliente;
import com.example.cambio.cliente.exception.ClienteNaoEncontradoException;
import com.example.cambio.cliente.exception.CpfJaCadastradoException;
import com.example.cambio.cliente.infrastructure.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClienteService {

    @Autowired
    private final ClienteRepository clienteRepository;

    // public ClienteService(ClienteRepository clienteRepository) {
       // this.clienteRepository = clienteRepository;
    //}

    public ClienteResponse cadastrar(CadastrarClienteRequest request) {

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

        Cliente clienteSalvo = clienteRepository.save(cliente);

        log.info(
                "Cliente cadastrado com sucesso. id={}",
                clienteSalvo.getId()
        );

        return toResponse(clienteSalvo);
    }

    public ClienteResponse consultarPorCpf(String cpf) {

        Cliente cliente = clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new ClienteNaoEncontradoException(cpf));

        log.info(
                "Cliente consultado com sucesso. id={}",
                cliente.getId()
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