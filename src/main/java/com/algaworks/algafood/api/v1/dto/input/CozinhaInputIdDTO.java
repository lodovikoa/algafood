package com.algaworks.algafood.api.v1.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaInputIdDTO {
        @NotNull
        Long id ;
}
