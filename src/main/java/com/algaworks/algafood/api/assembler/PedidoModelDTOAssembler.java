package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.*;
import com.algaworks.algafood.api.dto.model.PedidoModelDTO;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class PedidoModelDTOAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModelDTO> {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoModelDTOAssembler() {
        super(PedidoController.class, PedidoModelDTO.class);
    }

    @Override
    public PedidoModelDTO toModel(Pedido pedido) {
        var pedidoModelDTO = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModelDTO);

        var pageVariables = new TemplateVariables(
                new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("size", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("sort", TemplateVariable.VariableType.REQUEST_PARAM)
        );

        var filtrosVariables = new TemplateVariables(
                new TemplateVariable("clientId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("restauranteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoInicio", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoFim", TemplateVariable.VariableType.REQUEST_PARAM)
        );

        var pedidosUrl = WebMvcLinkBuilder.linkTo(PedidoController.class).toUri().toString();

        pedidoModelDTO.add(Link.of(UriTemplate.of(pedidosUrl, pageVariables.concat(filtrosVariables)), "pedidos"));

        // pedidoModelDTO.add(WebMvcLinkBuilder.linkTo(PedidoController.class).withRel("pedidos"));

        pedidoModelDTO.getRestaurante().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class).buscar(pedido.getRestaurante().getId())).withSelfRel());
        pedidoModelDTO.getCliente().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).buscarPorId(pedido.getCliente().getId())).withSelfRel());

        // Passamos null no segundo argumento porque é indiferente para a construção da URL do recurso de forma de pagamento
        pedidoModelDTO.getFormaPagamento().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FormaPagamentoController.class).buscarPorId(pedido.getFormaPagamento().getId(), null)).withSelfRel());
        pedidoModelDTO.getEnderecoEntrega().getCidade().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class).buscar(pedido.getEnderecoEntrega().getCidade().getId())).withSelfRel());

        pedidoModelDTO.getItens().forEach(item -> {
            item.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteProdutoController.class).buscarProdutoPorRestaurante(pedidoModelDTO.getRestaurante().getId(), item.getProdutoId())).withRel("produto"));
        });

        return pedidoModelDTO;
    }

//    public List<PedidoModelDTO> toCollectionModel(List<Pedido> pedidos) {
//        return pedidos.stream().map(pedido -> toModel(pedido)).collect(Collectors.toList());
//    }
}
