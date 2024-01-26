package com.algaworks.algafood.domain.model.service;

import lombok.Builder;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

@Service
public interface FotoStorageService {

    void armazenar(NovaFoto novaFoto);
    void remover(String nomeArquivo);
    FotoRecuperada recuperar(String nomeArquivo);

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
        private String contentType;
        private InputStream inputStream;
    }

    @Builder
    @Getter
    class FotoRecuperada {
        private InputStream inputStream;
        private String url;

        public boolean temUrl() {
            return url != null;
        }

        public boolean temInputStream() {
            return inputStream != null;
        }
    }
}
