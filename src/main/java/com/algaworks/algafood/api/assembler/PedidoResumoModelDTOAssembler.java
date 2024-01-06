package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.dto.model.PedidoModelDTO;
import com.algaworks.algafood.api.dto.model.PedidoResumoModelDTO;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoResumoModelDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoResumoModelDTO toModel(Pedido pedido) {
        return modelMapper.map(pedido, PedidoResumoModelDTO.class);
    }

    public List<PedidoResumoModelDTO> toCollectionModel(List<Pedido> pedidos) {
        return pedidos.stream().map(pedido -> toModel(pedido)).collect(Collectors.toList());
    }
}
