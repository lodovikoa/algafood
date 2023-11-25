package com.algaworks.algafood.domain.model.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {

    public CozinhaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public CozinhaNaoEncontradaException(Long cozinhaId) {
        this(String.format("Não existe um cadastro de Cozinha com ID %d", cozinhaId));
    }
}
