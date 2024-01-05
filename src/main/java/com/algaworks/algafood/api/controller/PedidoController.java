package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PedidoModelDTOAssembler;
import com.algaworks.algafood.api.dto.model.PedidoModelDTO;
import com.algaworks.algafood.domain.model.service.PedidoEmissaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

    @Autowired
    private PedidoEmissaoService pedidoEmissaoService;
    @Autowired
    private PedidoModelDTOAssembler pedidoModelDTOAssembler;

    @GetMapping
    public List<PedidoModelDTO> listar() {
        var todosPedidos = pedidoEmissaoService.findAll();
        return pedidoModelDTOAssembler.toCollectionModel(todosPedidos);
    }

    @GetMapping("/{pedidoId}")
    public PedidoModelDTO buscarPedido(@PathVariable Long pedidoId) {
        var pedido = pedidoEmissaoService.buscarOuFalhar(pedidoId);
        return pedidoModelDTOAssembler.toModel(pedido);
    }
}
