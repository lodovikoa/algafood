package com.algaworks.algafood.domain.model.exception;

public class EntidadeNaoEncontradaException extends RuntimeException{

    public EntidadeNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
