package com.algaworks.algafood.api.dto.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaInputDTO {

    @NotBlank
    private String nome;
}
