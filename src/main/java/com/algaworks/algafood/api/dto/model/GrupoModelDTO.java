package com.algaworks.algafood.api.dto.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@Relation(collectionRelation = "grupos")
public class GrupoModelDTO extends RepresentationModel<GrupoModelDTO> {
    private Long id;
    private String nome;
}
