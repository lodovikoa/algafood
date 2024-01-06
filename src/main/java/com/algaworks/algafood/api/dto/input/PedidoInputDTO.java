package com.algaworks.algafood.api.dto.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PedidoInputDTO {

    @Valid
    @NotNull
    private RestauranteIdInputDTO restaurante;

    @Valid
    @NotNull
    private EnderecoInputDTO enderecoEntrega;

    @Valid
    @NotNull
    private FormaPagamentoIdInputDTO formaPagamento;

    @Valid
    @Size(min = 1)
    @NotNull
    private List<ItemPedidoInputDTO> itens;
}
