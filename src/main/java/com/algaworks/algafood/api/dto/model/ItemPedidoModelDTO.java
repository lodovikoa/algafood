package com.algaworks.algafood.api.dto.model;

import com.algaworks.algafood.api.dto.input.ItemPedidoInputDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemPedidoModelDTO extends RepresentationModel<ItemPedidoModelDTO> {
    private Long produtoId;
    private String produtoNome;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal precoTotal;
    private String observacao;
}
