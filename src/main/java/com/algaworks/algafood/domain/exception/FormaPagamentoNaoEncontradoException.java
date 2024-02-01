package com.algaworks.algafood.domain.exception;

public class FormaPagamentoNaoEncontradoException extends EntidadeNaoEncontradaException{

    public FormaPagamentoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public FormaPagamentoNaoEncontradoException(Long formaPagamentoId) {
        this(String.format("Não existe um cadastro de Forma de Pagamento com ID %d", formaPagamentoId));
    }
}
