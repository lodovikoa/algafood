package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.dto.model.PedidoModelDTO;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoModelDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoModelDTO toModel(Pedido pedido) {
        return modelMapper.map(pedido, PedidoModelDTO.class);
    }

    public List<PedidoModelDTO> toCollectionModel(List<Pedido> pedidos) {
        return pedidos.stream().map(pedido -> toModel(pedido)).collect(Collectors.toList());
    }
}
