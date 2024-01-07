package com.algaworks.algafood.api.dto.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class PedidoModelDTO {

    private String codigo;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private String status;
    private OffsetDateTime dataCriacao;
    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataEntrega;
    private OffsetDateTime dataCancelamento;
    private RestauranteResumoModelDTO restaurante;
    private UsuarioModelDTO cliente;
    private FormaPagamentoModelDTO formaPagamento;
    private EnderecoModelDTO enderecoEntrega;
    private List<ItemPedidoModelDTO> itens;
}
