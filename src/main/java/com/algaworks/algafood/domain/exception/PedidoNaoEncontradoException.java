package com.algaworks.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public PedidoNaoEncontradoException(String pedidoCodigo) {
        super(String.format("Não existe um pedido com código %s", pedidoCodigo));
    }
}
