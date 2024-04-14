package com.algaworks.algafood.api.v1.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeInputDTO {
    @Schema(example = "Minas Gerais")
    @NotBlank
    private String nome;

    @Valid
    @NotNull
    private EstadoInputIdDTO estado;
}
