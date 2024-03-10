package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoFluxoService {

    @Autowired
    private PedidoEmissaoService pedidoEmissaoService;
    @Autowired
    private PedidoRepository pedidoRepository;

    public void confirmar(String pedidoCodigo) {
        var pedido = pedidoEmissaoService.buscarOuFalhar(pedidoCodigo);
        pedido.confirmar();

        pedidoRepository.save(pedido);

    }

    public void entregar(String pedidoCodigo) {
        var pedido = pedidoEmissaoService.buscarOuFalhar(pedidoCodigo);
        pedido.entregar();

    }

    public void cancelar(String pedidoCodigo) {
        var pedido = pedidoEmissaoService.buscarOuFalhar(pedidoCodigo);
        pedido.cancelar();

        pedidoRepository.save(pedido);
    }

    public boolean isPedidoGerenciadoPor(String codigoPedido, Long usuarioId) {
        return pedidoRepository.isPedidoGerenciadoPor(codigoPedido, usuarioId);
    }
}
