package com.algaworks.algafood.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_restaurante")
public class Restaurante implements Serializable {

    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private BigDecimal taxaFrete;

    @ManyToOne // (fetch = FetchType.LAZY)
    @JoinColumn(name = "cozinha_id", nullable = false)
    private Cozinha cozinha;

    @Embedded
    private Endereco endereco;

    private Boolean ativo = Boolean.TRUE;

    private Boolean aberto = Boolean.TRUE;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataAtualizacao;

    @ManyToMany
    @JoinTable(name = "tb_restaurante_forma_pagamento", joinColumns = @JoinColumn(name = "restaurante_id"), inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    private Set<FormaPagamento> formaPagamentos = new HashSet<>();

    @OneToMany(mappedBy = "restaurante")
    private List<Produto> produtos = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "tb_restaurante_usuario_responsavel", joinColumns = @JoinColumn(name = "restaurante_id"), inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private Set<Usuario> responsaveis = new HashSet<>();


    /*
    *  Métodos
    * */

    public boolean isAberto() {
        return this.aberto;
    }

    public boolean isFechado() {
        return !this.isAberto();
    }

    public boolean isAtivo() {
        return this.ativo;
    }

    public boolean isInativo() {
        return !this.isAtivo();
    }

    public void ativar() {
        this.setAtivo(true);
    }

    public boolean aberturaPermitida() {
        return this.isAtivo() && this.isFechado();
    }

    public boolean fechamentoPermitido() {
        return this.isAberto();
    }

    public boolean ativacaoPermitida() {
        return this.isInativo();
    }

    public boolean inativacaoPermitida() {
        return isAtivo();
    }


    public void inativar() {
        this.setAtivo(false);
    }

    public void abrir() {
        this.setAberto(true);
    }

    public void fechar() {
        this.setAberto(false);
    }

    public void removerFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamentos.remove(formaPagamento);
    }

    public void adicionarFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamentos.add(formaPagamento);
    }

    public boolean removerResponsavel(Usuario usuario) {
        return responsaveis.remove(usuario);
    }

    public boolean adicionarResponsavel(Usuario usuario) {
        return responsaveis.add(usuario);
    }

    public boolean aceitaFormaPagamento(FormaPagamento formaPagamento) {
        return formaPagamentos.contains(formaPagamento);
    }

    public boolean naoAceitaFormaPagamento(FormaPagamento formaPagamento) {
        return !this.aceitaFormaPagamento(formaPagamento);
    }
}
