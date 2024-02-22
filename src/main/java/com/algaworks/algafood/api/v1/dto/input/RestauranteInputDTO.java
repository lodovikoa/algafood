package com.algaworks.algafood.api.v1.dto.input;

import com.algaworks.algafood.core.validation.ValorZeroIncluiDescricao;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@ValorZeroIncluiDescricao(valorField = "taxaFrete", descricaoField = "nome", descricaoObrigatoria = "Frete Grátis")
public class RestauranteInputDTO {

        @NotBlank
        String nome;

        @PositiveOrZero
        @NotNull
        BigDecimal taxaFrete;

        @Valid
        @NotNull
        CozinhaInputIdDTO cozinha;

        @Valid
        @NotNull
        EnderecoInputDTO endereco;
}
