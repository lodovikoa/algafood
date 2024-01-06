package com.algaworks.algafood.api.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteIdInputDTO {
    @NotNull
    private Long id;
}
