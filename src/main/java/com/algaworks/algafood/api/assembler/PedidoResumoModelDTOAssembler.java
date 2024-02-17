package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.controller.UsuarioGrupoController;
import com.algaworks.algafood.api.dto.model.PedidoModelDTO;
import com.algaworks.algafood.api.dto.model.PedidoResumoModelDTO;
import com.algaworks.algafood.api.utility.AlgaLinks;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoResumoModelDTOAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModelDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public PedidoResumoModelDTOAssembler() {
        super(PedidoController.class, PedidoResumoModelDTO.class);
    }

    @Override
    public PedidoResumoModelDTO toModel(Pedido pedido) {
        var pedidoResumoModelDTO = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoResumoModelDTO);

        pedidoResumoModelDTO.add(algaLinks.linkToPedidos("pedidos"));
        pedidoResumoModelDTO.add(algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));
        pedidoResumoModelDTO.add(algaLinks.linkToUsuario(pedido.getCliente().getId()));

        return pedidoResumoModelDTO;
    }
}
