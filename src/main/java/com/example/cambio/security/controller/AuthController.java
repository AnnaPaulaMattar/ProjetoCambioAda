package com.example.cambio.security.controller;

import com.example.cambio.cliente.dto.CadastrarClienteRequest;
import com.example.cambio.security.dto.LoginRequestDTO;
import com.example.cambio.security.dto.LoginResponseDTO;
import com.example.cambio.security.dto.RegisterResponseDTO;
import com.example.cambio.security.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService service;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login (@RequestBody LoginRequestDTO dto) {
        LoginResponseDTO response = service.login(dto);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register (@Valid @RequestBody CadastrarClienteRequest dto){
        RegisterResponseDTO response = service.register(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
