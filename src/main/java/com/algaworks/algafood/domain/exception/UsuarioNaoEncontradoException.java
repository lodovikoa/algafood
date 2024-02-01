package com.algaworks.algafood.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {
    public UsuarioNaoEncontradoException(String mensagem) {
        super(mensagem);
    }


    public UsuarioNaoEncontradoException(Long idUsuario) {
        this(String.format("Não existe um cadastro de Usuario com ID %d", idUsuario));
    }
}
