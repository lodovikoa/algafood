package com.algaworks.algafood.api.dto.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@Relation(collectionRelation = "cozinhas")
public class CozinhaModelDTO extends RepresentationModel<CozinhaModelDTO> {
        // @JsonView(RestauranteView.Resumo.class)
        private Long id;

        // @JsonView(RestauranteView.Resumo.class)
        private String nome;
}
