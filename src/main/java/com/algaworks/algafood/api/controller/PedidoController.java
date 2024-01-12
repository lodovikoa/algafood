package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PedidoInputDTODisassembler;
import com.algaworks.algafood.api.assembler.PedidoModelDTOAssembler;
import com.algaworks.algafood.api.assembler.PedidoResumoModelDTOAssembler;
import com.algaworks.algafood.api.dto.input.PedidoInputDTO;
import com.algaworks.algafood.api.dto.model.CozinhaModelDTO;
import com.algaworks.algafood.api.dto.model.PedidoModelDTO;
import com.algaworks.algafood.api.dto.model.PedidoResumoModelDTO;
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.model.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.exception.NegocioException;
import com.algaworks.algafood.domain.model.repository.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.service.PedidoEmissaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    public Page<PedidoResumoModelDTO> pesquisar(PedidoFilter filtro, @PageableDefault(size = 10)Pageable pageable) {
        pageable = this.traduzirPageable(pageable);
        var pedidosPage = pedidoEmissaoService.findAll(filtro,pageable);
        var pedidosResumoModelDTO = pedidoResumoModelDTOAssembler.toCollectionModel(pedidosPage.getContent());
        Page<PedidoResumoModelDTO> pedidosModelPage = new PageImpl<>(pedidosResumoModelDTO, pageable, pedidosPage.getTotalElements());

        return pedidosModelPage;
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

    private Pageable traduzirPageable(Pageable apiPageable) {
        var mapeamento = Map.of(
                "codigo", "codigo",
                "restaurante.nome", "restaurante.nome",
                "clienteNome", "cliente.nome",
                "valorTotal", "valorTotal"
        );

        return PageableTranslator.translate(apiPageable, mapeamento);
    }
}
