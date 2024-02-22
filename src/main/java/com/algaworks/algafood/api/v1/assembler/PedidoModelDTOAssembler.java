package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.api.v1.dto.model.PedidoModelDTO;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoModelDTOAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModelDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public PedidoModelDTOAssembler() {
        super(PedidoController.class, PedidoModelDTO.class);
    }

    @Override
    public PedidoModelDTO toModel(Pedido pedido) {
        var pedidoModelDTO = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModelDTO);

        pedidoModelDTO.add(algaLinks.linkToPedidos("pedidos"));

        if(pedido.podeSerConfirmado())
            pedidoModelDTO.add(algaLinks.linkToPedidoConfirmacao(pedido.getCodigo(), "confirmar"));

        if(pedido.podeSerCancelado())
            pedidoModelDTO.add(algaLinks.linkToPedidoCancelamento(pedido.getCodigo(), "cancelar"));

        if(pedido.podeSerEntregue())
            pedidoModelDTO.add(algaLinks.linkToPedidoEntrega(pedido.getCodigo(), "entregar"));

        pedidoModelDTO.getRestaurante().add(algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));
        pedidoModelDTO.getCliente().add(algaLinks.linkToUsuario(pedido.getCliente().getId()));

        // Passamos null no segundo argumento porque é indiferente para a construção da URL do recurso de forma de pagamento
        pedidoModelDTO.getFormaPagamento().add(algaLinks.linkToFormasPagamento(pedido.getFormaPagamento().getId()));
        pedidoModelDTO.getEnderecoEntrega().getCidade().add(algaLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));

        pedidoModelDTO.getItens().forEach(item -> {
            item.add(algaLinks.linkToItens(pedidoModelDTO.getRestaurante().getId(), item.getProdutoId(), "produto"));
        });

        return pedidoModelDTO;
    }

}
