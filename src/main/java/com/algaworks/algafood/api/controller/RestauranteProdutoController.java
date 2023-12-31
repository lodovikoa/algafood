package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.ProdutoInputDTODisassembler;
import com.algaworks.algafood.api.assembler.ProdutoModelDTOAssembler;
import com.algaworks.algafood.api.dto.input.ProdutoInputDTO;
import com.algaworks.algafood.api.dto.model.ProdutoModelDTO;
import com.algaworks.algafood.domain.model.service.ProdutoService;
import com.algaworks.algafood.domain.model.service.RestauranteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private RestauranteService restauranteService;
    @Autowired
    private ProdutoModelDTOAssembler produtoModelDTOAssembler;
    @Autowired
    private ProdutoInputDTODisassembler produtoInputDTODisassembler;

    @GetMapping
    public List<ProdutoModelDTO> listar(@PathVariable Long restauranteId) {
        var restaurante = restauranteService.buscarOuFalhar(restauranteId);
        var todosProdutos = produtoService.findByRestaurante(restaurante);
        return produtoModelDTOAssembler.toCollectionMode(todosProdutos);
    }

    @GetMapping("/{produtoId}")
    public ProdutoModelDTO buscarProdutoPorRestaurante(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        var produto = produtoService.buscarOuFalhar(restauranteId, produtoId);
        return produtoModelDTOAssembler.toModel(produto);
    }

    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoModelDTO adicionarProdutoNoRestaurante(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInputDTO produtoInputDTO) {
        var restaurante = restauranteService.buscarOuFalhar(restauranteId);
        var produto = produtoInputDTODisassembler.toDomainObject(produtoInputDTO);

        produto.setRestaurante(restaurante);
        produto = produtoService.salvar(produto);
        return produtoModelDTOAssembler.toModel(produto);
    }

    @Transactional
    @PutMapping("/{produtoId}")
    public ProdutoModelDTO atualizarProdutoDoRestaurante(@PathVariable Long restauranteId, @PathVariable Long produtoId, @RequestBody @Valid ProdutoInputDTO produtoInputDTO) {
        var produtoAtual = produtoService.buscarOuFalhar(restauranteId, produtoId);
        produtoInputDTODisassembler.copyToDomainObject(produtoInputDTO, produtoAtual);
        produtoAtual = produtoService.salvar(produtoAtual);
        return produtoModelDTOAssembler.toModel(produtoAtual);
    }
}
