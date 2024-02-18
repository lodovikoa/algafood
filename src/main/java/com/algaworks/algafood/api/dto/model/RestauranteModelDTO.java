package com.algaworks.algafood.api.dto.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Getter
@Setter
@Relation(collectionRelation = "restaurantes")
public class RestauranteModelDTO extends RepresentationModel<RestauranteModelDTO> {
        // @JsonView( {RestauranteView.Resumo.class, RestauranteView.ApenasNome.class} )
        Long id;

        // @JsonView( {RestauranteView.Resumo.class, RestauranteView.ApenasNome.class} )
        String nome;

        // @JsonView(RestauranteView.Resumo.class)
        BigDecimal taxaFrete;

        // @JsonView(RestauranteView.Resumo.class)
        CozinhaModelDTO cozinha;
        Boolean ativo;
        Boolean aberto;
        EnderecoModelDTO endereco;
}
