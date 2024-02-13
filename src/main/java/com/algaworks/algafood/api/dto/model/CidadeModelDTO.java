package com.algaworks.algafood.api.dto.model;

import com.algaworks.algafood.domain.model.Estado;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@Relation(collectionRelation = "cidades")
public class CidadeModelDTO extends RepresentationModel<CidadeModelDTO> {
    private Long id;
    private String nome;
    private EstadoModelDTO estado;
}
