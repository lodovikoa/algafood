package com.algaworks.algafood.api.dto.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteInputDTO {
        @NotBlank
        String nome;
        @PositiveOrZero
        @NotNull
        BigDecimal taxaFrete;
        @Valid
        @NotNull
        CozinhaInputIdDTO cozinha;
}
