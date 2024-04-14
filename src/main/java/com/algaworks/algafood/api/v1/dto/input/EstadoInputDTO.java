package com.algaworks.algafood.api.v1.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoInputDTO {

    @Schema(example = "Minas Gerais")
    @NotBlank
    private String nome;
}
