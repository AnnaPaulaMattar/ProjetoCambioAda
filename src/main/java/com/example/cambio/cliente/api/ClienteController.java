package com.example.cambio.cliente.api;

import com.example.cambio.cliente.application.ClienteService;
import com.example.cambio.cliente.domain.Cliente;
import com.example.cambio.cliente.dto.CadastrarClienteRequest;
import com.example.cambio.cliente.dto.ClienteResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<ClienteResponse> consultarPorCpf(
            @PathVariable String cpf) {

        ClienteResponse cliente = clienteService.consultarPorCpf(cpf);

        return ResponseEntity.ok(cliente);
    }
}
