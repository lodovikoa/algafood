package com.algaworks.algafood.api.v1.dto.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@Relation(collectionRelation = "pedidos")
public class PedidoResumoModelDTO extends RepresentationModel<PedidoResumoModelDTO> {

    private String codigo;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private String status;
    private OffsetDateTime dataCriacao;
    private RestauranteResumoModelDTO restaurante;
    private UsuarioModelDTO cliente;
}
