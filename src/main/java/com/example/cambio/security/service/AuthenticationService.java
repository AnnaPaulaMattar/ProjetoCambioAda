package com.example.cambio.security.service;

import com.example.cambio.cliente.application.ClienteService;
import com.example.cambio.cliente.domain.Cliente;
import com.example.cambio.cliente.dto.CadastrarClienteRequest;
import com.example.cambio.security.dto.LoginRequestDTO;
import com.example.cambio.security.dto.LoginResponseDTO;
import com.example.cambio.security.dto.RegisterResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final ClienteService clienteService;

    private JwtService jwtService;

    public LoginResponseDTO login (LoginRequestDTO request){
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                );

        Authentication auth = authenticationManager.authenticate(token);

        Cliente cliente = (Cliente) auth.getPrincipal();

        String jwt = jwtService.generateToken(cliente);

        return new LoginResponseDTO(cliente.getNome(), jwt);
    }

    public RegisterResponseDTO register (CadastrarClienteRequest request){
        Cliente cliente = clienteService.cadastrar(request);

        String jwt = jwtService.generateToken(cliente);

        return new RegisterResponseDTO(cliente.getNome(), jwt);
    }

}
