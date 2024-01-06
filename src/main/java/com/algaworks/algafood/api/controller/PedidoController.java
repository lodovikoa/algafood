package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PedidoInputDTODisassembler;
import com.algaworks.algafood.api.assembler.PedidoModelDTOAssembler;
import com.algaworks.algafood.api.assembler.PedidoResumoModelDTOAssembler;
import com.algaworks.algafood.api.dto.input.PedidoInputDTO;
import com.algaworks.algafood.api.dto.model.PedidoModelDTO;
import com.algaworks.algafood.api.dto.model.PedidoResumoModelDTO;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.model.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.exception.NegocioException;
import com.algaworks.algafood.domain.model.service.PedidoEmissaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

    @Autowired
    private PedidoEmissaoService pedidoEmissaoService;
    @Autowired
    private PedidoModelDTOAssembler pedidoModelDTOAssembler;
    @Autowired
    private PedidoResumoModelDTOAssembler pedidoResumoModelDTOAssembler;
    @Autowired
    private PedidoInputDTODisassembler pedidoInputDTODisassembler;

    @GetMapping
    public List<PedidoResumoModelDTO> listar() {
        var todosPedidos = pedidoEmissaoService.findAll();
        return pedidoResumoModelDTOAssembler.toCollectionModel(todosPedidos);
    }

    @GetMapping("/{pedidoId}")
    public PedidoModelDTO buscarPedido(@PathVariable Long pedidoId) {
        var pedido = pedidoEmissaoService.buscarOuFalhar(pedidoId);
        return pedidoModelDTOAssembler.toModel(pedido);
    }

    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoModelDTO adicionar(@Valid @RequestBody PedidoInputDTO pedidoInputDTO) {
        try {
            var novoPedido = pedidoInputDTODisassembler.toDomainObject(pedidoInputDTO);

            //TODO Pegar usuário autenticado
            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(1L);

            novoPedido = pedidoEmissaoService.emitir(novoPedido);

            return pedidoModelDTOAssembler.toModel(novoPedido);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(),e);
        }
    }
}
