package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.api.v1.dto.model.PedidoResumoModelDTO;
import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoResumoModelDTOAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModelDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public PedidoResumoModelDTOAssembler() {
        super(PedidoController.class, PedidoResumoModelDTO.class);
    }

    @Override
    public PedidoResumoModelDTO toModel(Pedido pedido) {
        var pedidoResumoModelDTO = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoResumoModelDTO);

        if(algaSecurity.podePesquisarPedidos()) {
            pedidoResumoModelDTO.add(algaLinks.linkToPedidos("pedidos"));
        }

        if(algaSecurity.podeConsultarRestaurantes()) {
            pedidoResumoModelDTO.add(algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));
        }

        if(algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
            pedidoResumoModelDTO.add(algaLinks.linkToUsuario(pedido.getCliente().getId()));
        }

        return pedidoResumoModelDTO;
    }
}
