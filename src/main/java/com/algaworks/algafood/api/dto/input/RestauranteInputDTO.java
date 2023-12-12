package com.algaworks.algafood.api.dto.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record RestauranteInputDTO(
        @NotBlank
        String nome,
        @PositiveOrZero
        @NotNull
        BigDecimal taxaFrete,
        @Valid
        @NotNull
        CozinhaInputIdDTO cozinha
) {
}
