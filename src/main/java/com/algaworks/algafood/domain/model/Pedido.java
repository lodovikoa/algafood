package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.domain.model.exception.NegocioException;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_pedido")
public class Pedido {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;

    @Embedded
    private Endereco enderecoEntrega;

    @Enumerated(EnumType.STRING)
    private StatusPedido status = StatusPedido.CRIADO;

    @CreationTimestamp
    private OffsetDateTime dataCriacao;

    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataCancelamento;
    private OffsetDateTime dataEntrega;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forma_pagamento_id", nullable = false)
    private FormaPagamento formaPagamento;

    @ManyToOne
    @JoinColumn(name = "restaurante_id",nullable = false)
    private Restaurante restaurante;

    @ManyToOne
    @JoinColumn(name = "usuario_cliente_id", nullable = false)
    private Usuario cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens = new ArrayList<>();

    // Métodos
    public void calcularValorTotal() {
        itens.forEach(ItemPedido::calcularPrecoTotal);
        this.subtotal = itens.stream().map(item -> item.getPrecoTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
        this.valorTotal = this.subtotal.add(this.taxaFrete);
    }

    public void confirmar() {
        this.setStatus( StatusPedido.CONFIRMADO);
        this.dataConfirmacao = OffsetDateTime.now();
    }

    public void entregar() {
        this.setStatus(StatusPedido.ENTREGUE);
        this.dataEntrega = OffsetDateTime.now();
    }

    public void cancelar() {
        this.setStatus(StatusPedido.CANCELADO);
        this.dataCancelamento = OffsetDateTime.now();
    }

    private void setStatus(StatusPedido novoStatus) {
        if(status.naoPodeAlterarPara(novoStatus)) {
            throw new NegocioException(String.format("Status do pedido %s não pode ser alterado de %s para %s", codigo, this.status.getDescricao(), novoStatus.getDescricao()));
        }

        this.status = novoStatus;
    }

    @PrePersist // Método executado antes de persistir a entidade no banco de dados
    public void gerarCodigo() {
        this.codigo = UUID.randomUUID().toString();
    }

}
