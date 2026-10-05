package com.example.cambio.compra.mapper;

import com.example.cambio.compra.domain.Compra;
import com.example.cambio.compra.dto.CompraResponse;
import org.springframework.stereotype.Component;

@Component
public class CompraMapper {

    public CompraResponse toCompraResponse(Compra compra) {
        return new CompraResponse(
                compra.getId(),
                compra.getCliente().getCpf(),
                compra.getMoeda(),
                compra.getQuantidadeEmMoeda(),
                compra.getTaxaCotacao(),
                compra.getValorTotal(),
                compra.getAgenciaRetirada(),
                compra.getDataCompra()
        );
    }
}
