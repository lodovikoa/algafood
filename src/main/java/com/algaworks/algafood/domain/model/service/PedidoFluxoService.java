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
    @Autowired
    private EnvioEmailService envioEmailService;

    public void confirmar(String pedidoCodigo) {
        var pedido = pedidoEmissaoService.buscarOuFalhar(pedidoCodigo);
        pedido.confirmar();

        var mensagem = EnvioEmailService.Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
                .corpo("pedido-confirmado.html")
                .variavel("pedido", pedido)
                .destinatario(pedido.getCliente().getEmail())
                .build();

        envioEmailService.enviar(mensagem);
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
