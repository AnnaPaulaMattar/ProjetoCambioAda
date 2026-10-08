package com.example.cambio.security.service;

import com.example.cambio.cliente.infrastructure.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ClienteRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username){
        return repository.findByCpf(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Cliente não encontrado"
                ));
    }

}
