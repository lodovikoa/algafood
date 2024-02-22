package com.algaworks.algafood.api.v1.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoInputDTO {
    @NotBlank
    private String nome;
    @NotBlank
    private String descricao;
    @NotNull
    @PositiveOrZero
    private BigDecimal preco;
    @NotNull
    private Boolean ativo;
}
