package com.algaworks.algafood.domain.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class EntidadeEmUsoException extends RuntimeException{
    public EntidadeEmUsoException(String mensagem) {
        super(mensagem);
    }
}
