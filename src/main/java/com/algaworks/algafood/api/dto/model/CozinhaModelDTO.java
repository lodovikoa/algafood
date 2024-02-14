package com.algaworks.algafood.api.dto.model;

import com.algaworks.algafood.domain.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@Relation(collectionRelation = "cozinhas")
public class CozinhaModelDTO extends RepresentationModel<CozinhaModelDTO> {
        @JsonView(RestauranteView.Resumo.class)
        Long id;

        @JsonView(RestauranteView.Resumo.class)
        String nome;
}
