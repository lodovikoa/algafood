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
import com.algaworks.algafood.domain.model.repository.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.service.PedidoEmissaoService;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
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

//    @GetMapping
//    public MappingJacksonValue listar(@RequestParam(required = false) String campos) {
//        var pedidosModel = pedidoResumoModelDTOAssembler.toCollectionModel(pedidoEmissaoService.findAll());
//        var pedidosWrapper = new MappingJacksonValue(pedidosModel);
//
//        var filterProvider = new SimpleFilterProvider();
//        filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
//
//        if(StringUtils.isNotBlank(campos)) {
//            filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
//        }
//
//        pedidosWrapper.setFilters(filterProvider);
//
//        return pedidosWrapper;
//    }

    @GetMapping
    public List<PedidoResumoModelDTO> pesquisar(PedidoFilter filtro) {
        var todosPedidos = pedidoEmissaoService.findAll(filtro);
        return pedidoResumoModelDTOAssembler.toCollectionModel(todosPedidos);
    }

    @GetMapping("/{pedidoCodigo}")
    public PedidoModelDTO buscarPedido(@PathVariable String pedidoCodigo) {
        var pedido = pedidoEmissaoService.buscarOuFalhar(pedidoCodigo);
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
