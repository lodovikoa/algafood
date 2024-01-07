package com.algaworks.algafood.domain.model.service;

import com.algaworks.algafood.domain.model.StatusPedido;
import com.algaworks.algafood.domain.model.exception.NegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class PedidoFluxoService {

    @Autowired
    private PedidoEmissaoService pedidoEmissaoService;

    public void confirmar(Long pedidoId) {
        var pedido = pedidoEmissaoService.buscarOuFalhar(pedidoId);
        pedido.confirmar();
    }

    public void entregar(Long pedidoId) {
        var pedido = pedidoEmissaoService.buscarOuFalhar(pedidoId);
        pedido.entregar();
    }

    public void cancelar(Long pedidoId) {
        var pedido = pedidoEmissaoService.buscarOuFalhar(pedidoId);
        pedido.cancelar();
    }
}
