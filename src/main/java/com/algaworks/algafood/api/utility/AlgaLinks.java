package com.algaworks.algafood.api.utility;

import com.algaworks.algafood.api.controller.*;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class AlgaLinks {

    private static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
            new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("size", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("sort", TemplateVariable.VariableType.REQUEST_PARAM)
    );

    private static final TemplateVariables PROJECAO_VARIAVLES = new TemplateVariables(
            new TemplateVariable("projecao", TemplateVariable.VariableType.REQUEST_PARAM)
    );

    public Link linkToPedidos(String rel) {

        var filtrosVariables = new TemplateVariables(
                new TemplateVariable("clientId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("restauranteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoInicio", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoFim", TemplateVariable.VariableType.REQUEST_PARAM)
        );

        var pedidosUrl = WebMvcLinkBuilder.linkTo(PedidoController.class).toUri().toString();

        return Link.of(UriTemplate.of(pedidosUrl, PAGINACAO_VARIABLES.concat(filtrosVariables)), rel);
    }

//    public Link linkToPedidos(String rel) {
//        return WebMvcLinkBuilder.linkTo(PedidoController.class).withRel(rel);
//    }

    public Link linkToPedidoConfirmacao(String codigoPedido, String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PedidoFluxoController.class).confirmar(codigoPedido)).withRel(rel);
    }

    public Link linkToPedidoEntrega(String codigoPedido, String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PedidoFluxoController.class).entregar(codigoPedido)).withRel(rel);
    }

    public Link linkToPedidoCancelamento(String codigoPedido, String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PedidoFluxoController.class).cancelar(codigoPedido)).withRel(rel);
    }

    public Link linkToRestaurante() {
        return WebMvcLinkBuilder.linkTo(RestauranteController.class).withSelfRel();
    }

    public Link linkToRestaurante(Long restauranteId) {
        return  WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class).buscar(restauranteId)).withSelfRel();
    }

    public Link linkToRestaurante(String rel) {
        var restauranteUrl = WebMvcLinkBuilder.linkTo(RestauranteController.class).toUri().toString();
        return Link.of(UriTemplate.of(restauranteUrl, PROJECAO_VARIAVLES), rel);
    }

//    public Link linkToRestaurante(String rel) {
//        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class).listar()).withRel(rel);
//    }

    public Link linkToRestauranteFormasPagamento(Long restauranteId, String rel) {
        return  WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteFormasPagamentoController.class).listar(restauranteId)).withRel(rel);
    }

    public Link linkToRestauranteResponsaveis(Long restauranteId) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteUsuarioResponsavelController.class).listar(restauranteId)).withSelfRel();
    }

    public Link linkToRestauranteResponsaveis(Long restauranteId, String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteUsuarioResponsavelController.class).listar(restauranteId)).withRel(rel);
    }

    public Link linkToRestauranteAtivacao(Long restauranteId, String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class).ativar(restauranteId)).withRel(rel);
    }

    public Link linkToRestauranteInativacao(Long restauranteId, String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class).inativar(restauranteId)).withRel(rel);
    }

    public Link linkToRestauranteAbertura(Long restauranteId, String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class).abrirRestaurente(restauranteId)).withRel(rel);
    }

    public Link linkToRestauranteFechamento(Long restauranteId, String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class).fecharRestaurente(restauranteId)).withRel(rel);
    }

    public Link linkToUsuario() {
        return WebMvcLinkBuilder.linkTo(UsuarioController.class).withSelfRel();
    }

    public Link linkToUsuario(String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).listar()).withRel(rel);
    }


    public Link linkToUsuario(Long pedidoId) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).buscarPorId(pedidoId)).withSelfRel();
    }

    public Link linkToGrupoUsuarios(Long usuarioId, String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioGrupoController.class).listar(usuarioId)).withRel(rel);
    }


    public Link linkToFormasPagamento() {
        return linkToFormasPagamento(IanaLinkRelations.SELF.value());
    }

    public Link linkToFormasPagamento(Long formaPagamentoId) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FormaPagamentoController.class).buscarPorId(formaPagamentoId, null)).withSelfRel();
    }

    public Link linkToRestauranteFormasPagamento(Long restauranteId) {
        return linkToRestauranteFormasPagamento(restauranteId, IanaLinkRelations.SELF.value());
    }

    public Link linkToRestauranteFormasPagamentoDesassociacao(Long restauranteId, Long formaPagamentoId, String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteFormasPagamentoController.class).desassociarFormaPagamento(restauranteId, formaPagamentoId)).withRel(rel);
    }

    public Link linkToFormasPagamento(String rel) {
        return WebMvcLinkBuilder.linkTo(FormaPagamentoController.class).withRel(rel);
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

    public Link linkToCozinha(Long cozinhaId) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CozinhaController.class).buscar(cozinhaId)).withSelfRel();
    }
}
