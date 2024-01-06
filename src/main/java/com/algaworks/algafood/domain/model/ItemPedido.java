package com.algaworks.algafood.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_item_pedido")
public class ItemPedido {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal precoTotal;
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "pedido_id",nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "produto_id",nullable = false)
    private Produto produto;

    public void calcularPrecoTotal() {
        BigDecimal precoUnitario = this.precoUnitario;
        Integer quantidade = this.quantidade;

        if(precoUnitario == null) precoUnitario = BigDecimal.ZERO;

        if(quantidade == null)  quantidade = 0;

        this.precoTotal = precoUnitario.multiply(new BigDecimal((quantidade)));
    }
}
