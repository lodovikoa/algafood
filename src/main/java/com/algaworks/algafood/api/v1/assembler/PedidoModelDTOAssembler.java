package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.api.v1.dto.model.PedidoModelDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
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

    @Autowired
    private AlgaSecurity algaSecurity;

    public PedidoModelDTOAssembler() {
        super(PedidoController.class, PedidoModelDTO.class);
    }

    @Override
    public PedidoModelDTO toModel(Pedido pedido) {
        var pedidoModelDTO = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModelDTO);

        if(algaSecurity.podePesquisarPedidos()) {
            pedidoModelDTO.add(algaLinks.linkToPedidos("pedidos"));
        }

        if(algaSecurity.podeGerenciarPedidos(pedido.getCodigo())) {
            if (pedido.podeSerConfirmado())
                pedidoModelDTO.add(algaLinks.linkToPedidoConfirmacao(pedido.getCodigo(), "confirmar"));

            if (pedido.podeSerCancelado())
                pedidoModelDTO.add(algaLinks.linkToPedidoCancelamento(pedido.getCodigo(), "cancelar"));

            if (pedido.podeSerEntregue())
                pedidoModelDTO.add(algaLinks.linkToPedidoEntrega(pedido.getCodigo(), "entregar"));
        }

        if(algaSecurity.podeConsultarRestaurantes()) {
            pedidoModelDTO.getRestaurante().add(algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));
        }

        if(algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
            pedidoModelDTO.getCliente().add(algaLinks.linkToUsuario(pedido.getCliente().getId()));
        }

        if(algaSecurity.podeConsultarFormasPagamento()) {
            // Passamos null no segundo argumento porque é indiferente para a construção da URL do recurso de forma de pagamento
            pedidoModelDTO.getFormaPagamento().add(algaLinks.linkToFormasPagamento(pedido.getFormaPagamento().getId()));
        }

        if(algaSecurity.podeConsultarCidades()) {
            pedidoModelDTO.getEnderecoEntrega().getCidade().add(algaLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));
        }

        // Quem pode consultar restaurantes também pode consultar os produtos dos restaurantes
        if(algaSecurity.podeConsultarRestaurantes()) {
            pedidoModelDTO.getItens().forEach(item -> {
                item.add(algaLinks.linkToItens(pedidoModelDTO.getRestaurante().getId(), item.getProdutoId(), "produto"));
            });
        }

        return pedidoModelDTO;
    }

}
