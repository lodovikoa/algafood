package com.algaworks.algafood.api.dto.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@Relation(collectionRelation = "fotos")
public class FotoProdutoModelDTO extends RepresentationModel<FotoProdutoModelDTO> {

    private String nomeArquivo;
    private String descricao;
    private String contentType;
    private Long tamanho;
}
