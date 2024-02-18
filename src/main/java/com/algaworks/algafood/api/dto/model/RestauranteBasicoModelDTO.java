package com.algaworks.algafood.api.dto.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Getter
@Setter
@Relation(collectionRelation = "restaurantes")
public class RestauranteBasicoModelDTO extends RepresentationModel<RestauranteBasicoModelDTO> {

    private Long id;
    private String nome;
    private BigDecimal taxaFrete;
    private CozinhaModelDTO cozinha;
}
