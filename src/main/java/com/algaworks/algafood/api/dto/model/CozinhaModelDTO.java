package com.algaworks.algafood.api.dto.model;

import com.algaworks.algafood.domain.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaModelDTO {
        @JsonView(RestauranteView.Resumo.class)
        Long id;

        @JsonView(RestauranteView.Resumo.class)
        String nome;
}
