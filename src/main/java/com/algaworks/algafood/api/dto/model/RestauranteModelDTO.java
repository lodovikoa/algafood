package com.algaworks.algafood.api.dto.model;

import com.algaworks.algafood.domain.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteModelDTO{
        @JsonView( {RestauranteView.Resumo.class, RestauranteView.ApenasNome.class} )
        Long id;

        @JsonView( {RestauranteView.Resumo.class, RestauranteView.ApenasNome.class} )
        String nome;

        @JsonView(RestauranteView.Resumo.class)
        BigDecimal taxaFrete;

        @JsonView(RestauranteView.Resumo.class)
        CozinhaModelDTO cozinha;
        Boolean ativo;
        Boolean aberto;
        EnderecoModelDTO endereco;
}
