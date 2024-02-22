package com.algaworks.algafood.api.v1.dto.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoInputDTO {

    @NotBlank
    private String descricao;
}
