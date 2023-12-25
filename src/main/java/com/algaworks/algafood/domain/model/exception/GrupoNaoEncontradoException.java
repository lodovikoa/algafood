package com.algaworks.algafood.domain.model.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException{
    public GrupoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public GrupoNaoEncontradoException(Long idGrupo) {
        this(String.format("Não existe um cadastro de Grupo com ID %d", idGrupo));
    }
}
