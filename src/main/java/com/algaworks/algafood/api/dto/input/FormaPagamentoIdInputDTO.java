package com.algaworks.algafood.api.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoIdInputDTO {
    @NotNull
    private Long id;
}
