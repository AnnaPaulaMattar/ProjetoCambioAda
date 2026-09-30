package com.example.cambio.cambio.api;


import com.example.cambio.cambio.application.CambioService;
import com.example.cambio.cambio.dto.CotacaoResponse;
import com.example.cambio.enums.Moedas;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cambio/cotacao")
@RequiredArgsConstructor
public class CambioController {

    private final CambioService service;

    @GetMapping("/{moeda}")
    public ResponseEntity<CotacaoResponse> cotacao (@PathVariable Moedas moeda){
        CotacaoResponse response = service.cotacao(moeda);
        return ResponseEntity.ok(response);
    }

}
