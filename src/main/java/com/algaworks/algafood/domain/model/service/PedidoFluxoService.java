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

    public void confirmar(String pedidoCodigo) {
        var pedido = pedidoEmissaoService.buscarOuFalhar(pedidoCodigo);
        pedido.confirmar();
    }

    public void entregar(String pedidoCodigo) {
        var pedido = pedidoEmissaoService.buscarOuFalhar(pedidoCodigo);
        pedido.entregar();
    }

    public void cancelar(String pedidoCodigo) {
        var pedido = pedidoEmissaoService.buscarOuFalhar(pedidoCodigo);
        pedido.cancelar();
    }
}
