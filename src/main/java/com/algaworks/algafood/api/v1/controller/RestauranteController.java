package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.RestauranteApenasNomeModelDTOAssembler;
import com.algaworks.algafood.api.v1.assembler.RestauranteBasicoModelDTOAssembler;
import com.algaworks.algafood.api.v1.assembler.RestauranteInputDTODisassembler;
import com.algaworks.algafood.api.v1.assembler.RestauranteModelDTOAssembler;
import com.algaworks.algafood.api.v1.dto.input.RestauranteInputDTO;
import com.algaworks.algafood.api.v1.dto.model.RestauranteApenasNomeModelDTO;
import com.algaworks.algafood.api.v1.dto.model.RestauranteBasicoModelDTO;
import com.algaworks.algafood.api.v1.dto.model.RestauranteModelDTO;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/v1/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private RestauranteModelDTOAssembler restauranteModelDtoAssembler;

    @Autowired
    private RestauranteInputDTODisassembler restauranteInputDtoDisassembler;

    @Autowired
    private RestauranteBasicoModelDTOAssembler restauranteBasicoModelDTOAssembler;

    @Autowired
    RestauranteApenasNomeModelDTOAssembler restauranteApenasNomeModelDTOAssembler;

    // @JsonView(RestauranteView.Resumo.class)
    @GetMapping
    public CollectionModel<RestauranteBasicoModelDTO> listar() {
        return restauranteBasicoModelDTOAssembler.toCollectionModel(restauranteService.findAll());
    }

    // @JsonView(RestauranteView.ApenasNome.class)
    @GetMapping(params = "projecao=apenas-nome")
    public CollectionModel<RestauranteApenasNomeModelDTO> listarApenasNome() {
        return restauranteApenasNomeModelDTOAssembler.toCollectionModel(restauranteService.findAll());
    }

    @GetMapping(value = "{restauranteId}")
    public RestauranteModelDTO buscar(@PathVariable Long restauranteId) {

        return restauranteModelDtoAssembler.toModel(restauranteService.buscarOuFalhar(restauranteId));
    }

    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteModelDTO salvar(@RequestBody @Valid RestauranteInputDTO restauranteImputDTO) {
        try {
            Restaurante restaurante = restauranteInputDtoDisassembler.toDomainObject(restauranteImputDTO);

            return restauranteModelDtoAssembler.toModel(restauranteService.salvar(restaurante));
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @Transactional
    @PutMapping(value = "{restauranteId}")
    public RestauranteModelDTO alterar(@PathVariable Long restauranteId, @RequestBody @Valid RestauranteInputDTO restauranteInputDTO) {
        var restauranteAtual = restauranteService.buscarOuFalhar(restauranteId);
        restauranteInputDtoDisassembler.copyToDomainObject(restauranteInputDTO, restauranteAtual);
        try {
            return restauranteModelDtoAssembler.toModel(restauranteService.salvar(restauranteAtual));
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }

    }

    @Transactional
    @PatchMapping("/{restauranteId}")
    public RestauranteModelDTO atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos, HttpServletRequest request) {
        return restauranteModelDtoAssembler.toModel(restauranteService.atualizarParcial(restauranteId, campos, request));
    }

    @Transactional
    @PutMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ativar(@PathVariable Long restauranteId) {
        restauranteService.ativar(restauranteId);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @DeleteMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inativar(@PathVariable Long restauranteId) {
        restauranteService.inativar(restauranteId);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/ativacoes")
    public void ativacoes(@RequestBody List<Long> restauranteIds) {
        restauranteService.ativacoes(restauranteIds);
    }

    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/inativacoes")
    public void inativacoes(@RequestBody List<Long> restauranteIds) {
        restauranteService.inativacoes(restauranteIds);
    }

    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{restauranteId}/abertura")
    public ResponseEntity<Void> abrirRestaurente(@PathVariable Long restauranteId) {
        restauranteService.abrirRestaurante(restauranteId);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{restauranteId}/fechamento")
    public ResponseEntity<Void> fecharRestaurente(@PathVariable Long restauranteId) {
        restauranteService.fecharRestaurante(restauranteId);
        return ResponseEntity.noContent().build();
    }
}