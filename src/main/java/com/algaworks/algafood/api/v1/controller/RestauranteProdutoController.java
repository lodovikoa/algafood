package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.ProdutoInputDTODisassembler;
import com.algaworks.algafood.api.v1.assembler.ProdutoModelDTOAssembler;
import com.algaworks.algafood.api.v1.dto.input.ProdutoInputDTO;
import com.algaworks.algafood.api.v1.dto.model.ProdutoModelDTO;
import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteProdutoControllerOpenApi;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteProdutoFotoControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.ProdutoService;
import com.algaworks.algafood.domain.service.RestauranteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi {

    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private RestauranteService restauranteService;
    @Autowired
    private ProdutoModelDTOAssembler produtoModelDTOAssembler;
    @Autowired
    private ProdutoInputDTODisassembler produtoInputDTODisassembler;

    @Autowired
    private AlgaLinks algaLinks;

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping
    public CollectionModel<ProdutoModelDTO> listar(@PathVariable Long restauranteId, @RequestParam(required = false) Boolean incluirInativos) {
        var restaurante = restauranteService.buscarOuFalhar(restauranteId);

        List<Produto> todosProdutos = produtoService.findByRestaurante(restaurante, incluirInativos);

        return produtoModelDTOAssembler.toCollectionModel(todosProdutos).add(algaLinks.linkToProdutos(restauranteId));
    }

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping("/{produtoId}")
    public ProdutoModelDTO buscarProdutoPorRestaurante(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        var produto = produtoService.buscarOuFalhar(restauranteId, produtoId);
        return produtoModelDTOAssembler.toModel(produto);
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
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

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @Transactional
    @PutMapping("/{produtoId}")
    public ProdutoModelDTO atualizarProdutoDoRestaurante(@PathVariable Long restauranteId, @PathVariable Long produtoId, @RequestBody @Valid ProdutoInputDTO produtoInputDTO) {
        var produtoAtual = produtoService.buscarOuFalhar(restauranteId, produtoId);
        produtoInputDTODisassembler.copyToDomainObject(produtoInputDTO, produtoAtual);
        produtoAtual = produtoService.salvar(produtoAtual);
        return produtoModelDTOAssembler.toModel(produtoAtual);
    }
}
