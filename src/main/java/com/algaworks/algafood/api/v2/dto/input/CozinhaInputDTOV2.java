package com.algaworks.algafood.api.v2.dto.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaInputDTOV2 {

    @NotBlank
    private String nomeCozinha;
}
