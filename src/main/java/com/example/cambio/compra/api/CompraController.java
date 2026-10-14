package com.example.cambio.compra.api;

import com.example.cambio.compra.application.CompraService;
import com.example.cambio.compra.dto.CompraResponse;
import com.example.cambio.compra.dto.RegistrarCompraRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compras")
@RequiredArgsConstructor
public class CompraController {

    private final CompraService compraService;

    @PostMapping
    public ResponseEntity<CompraResponse> registrarCompra(
            @Valid @RequestBody RegistrarCompraRequest request) {

        CompraResponse response = compraService.registrarCompra(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompraResponse> consultarCompra(@PathVariable Long id) {
        CompraResponse response = compraService.consultarCompra(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cliente/{cpf}")
    public ResponseEntity<List<CompraResponse>> consultarComprasPorCpf(
            @PathVariable String cpf) {

        List<CompraResponse> response = compraService.consultarComprasPorCpf(cpf);
        return ResponseEntity.ok(response);
    }
}
