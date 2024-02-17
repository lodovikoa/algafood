package com.algaworks.algafood.api.utility;

import com.algaworks.algafood.api.controller.*;
import org.hibernate.annotations.CreationTimestamp;
import org.jfree.chart.renderer.category.LineRenderer3D;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class AlgaLinks {

    private static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
            new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("size", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("sort", TemplateVariable.VariableType.REQUEST_PARAM)
    );

    public Link linkToPedidos() {

        var filtrosVariables = new TemplateVariables(
                new TemplateVariable("clientId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("restauranteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoInicio", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoFim", TemplateVariable.VariableType.REQUEST_PARAM)
        );

        var pedidosUrl = WebMvcLinkBuilder.linkTo(PedidoController.class).toUri().toString();

        return Link.of(UriTemplate.of(pedidosUrl, PAGINACAO_VARIABLES.concat(filtrosVariables)), "pedidos");
    }

    public Link linkToPedidos(String rel) {
        return WebMvcLinkBuilder.linkTo(PedidoController.class).withRel(rel);
    }

    public Link linkToPedidoConfirmacao(String codigoPedido, String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PedidoFluxoController.class).confirmar(codigoPedido)).withRel(rel);
    }

    public Link linkToPedidoEntrega(String codigoPedido, String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PedidoFluxoController.class).entregar(codigoPedido)).withRel(rel);
    }

    public Link linkToPedidoCancelamento(String codigoPedido, String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PedidoFluxoController.class).cancelar(codigoPedido)).withRel(rel);
    }

    public Link linkToRestaurante(Long restauranteId) {
        return  WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class).buscar(restauranteId)).withSelfRel();
    }

    public Link linkToResponsaveisRestaurante(Long restauranteId) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteUsuarioResponsavelController.class).listar(restauranteId)).withSelfRel();
    }

    public Link linktoUsuario(String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).listar()).withRel(rel);
    }


    public Link linkToUsuario(Long pedidoId) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).buscarPorId(pedidoId)).withSelfRel();
    }

    public Link linkToGrupoUsuarios(Long usuarioId, String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioGrupoController.class).listar(usuarioId)).withRel(rel);
    }

    public Link linkToFormaPagamento(Long formaPagamentoId) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FormaPagamentoController.class).buscarPorId(formaPagamentoId, null)).withSelfRel();
    }

    public Link linkToCidade() {
        return WebMvcLinkBuilder.linkTo(CidadeController.class).withSelfRel();
    }

    public Link linkToCidade(String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class).listar()).withRel(rel);
    }

    public Link linkToCidade(Long cidadeId) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class).buscar(cidadeId)).withSelfRel();
    }

    public Link linktoEstado() {
        return WebMvcLinkBuilder.linkTo(EstadoController.class).withSelfRel();
    }

    public Link linkToEstado(String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstadoController.class).listar()).withRel(rel);
    }

    public Link linkToEstado(Long estadoId) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstadoController.class).buscar(estadoId)).withSelfRel();
    }

    public Link linkToItens(Long restauranteId, Long produdoId, String rel ) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteProdutoController.class).buscarProdutoPorRestaurante(restauranteId, produdoId)).withRel(rel);
    }

    public Link linkToCozinha(String rel) {
        return WebMvcLinkBuilder.linkTo(CozinhaController.class).withRel(rel);
    }
}
