package com.algaworks.algafood.domain.model.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface FotoStorageService {

    void armazenar(NovaFoto novaFoto);
    void remover(String nomeArquivo);
    InputStream recuperar(String nomeArquivo);

    default void substituir(String nomeArquivoExistente, NovaFoto novaFoto) {
        this.armazenar(novaFoto);
        if(nomeArquivoExistente != null) this.remover(nomeArquivoExistente);
    }

    default String gerarNomeArquivo(String nomeOriginal) {
        return UUID.randomUUID().toString() + "_" + nomeOriginal;
    }

    @Builder
    @Getter
    class NovaFoto {
        private String nomeArquivo;
        private InputStream inputStream;
    }
}