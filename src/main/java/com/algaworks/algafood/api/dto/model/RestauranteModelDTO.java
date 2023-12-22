package com.algaworks.algafood.api.dto.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteModelDTO{
        Long id;
        String nome;
        BigDecimal taxaFrete;
        CozinhaModelDTO cozinha;
        Boolean ativo;
        EnderecoModelDTO endereco;
}
