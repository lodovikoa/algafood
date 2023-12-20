package com.algaworks.algafood.domain.model.exception;

public class FormaPagamentoNaoEncontradoException extends EntidadeNaoEncontradaException{

    public FormaPagamentoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public FormaPagamentoNaoEncontradoException(Long formaPagamentoId) {
        this(String.format("Não existe um cadastro de Cidade com ID %d", formaPagamentoId));
    }
}
