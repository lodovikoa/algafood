package com.algaworks.algafood.api.v1.dto.input;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrupoInputDTO {

    @NotBlank
    private String nome;
}
