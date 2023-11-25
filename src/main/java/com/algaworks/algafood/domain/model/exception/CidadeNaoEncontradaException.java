package com.algaworks.algafood.domain.model.exception;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {

    public CidadeNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public CidadeNaoEncontradaException(Long cidadeId) {
        this(String.format("Não existe um cadastro de Cidade com ID %d", cidadeId));
    }
}
