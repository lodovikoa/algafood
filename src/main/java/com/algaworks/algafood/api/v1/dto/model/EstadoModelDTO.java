package com.algaworks.algafood.api.v1.dto.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@Relation(collectionRelation = "estados")
public class EstadoModelDTO extends RepresentationModel<EstadoModelDTO> {
    private Long id;
    private String nome;
}
