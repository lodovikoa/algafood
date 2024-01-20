package com.algaworks.algafood.api.dto.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoModelDTO {

    private String nomeArquivo;
    private String descricao;
    private String contentType;
    private Long tamanho;
}
