package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.RestauranteInputDTODisassembler;
import com.algaworks.algafood.api.assembler.RestauranteModelDTOAssembler;
import com.algaworks.algafood.api.dto.input.RestauranteInputDTO;
import com.algaworks.algafood.api.dto.model.RestauranteModelDTO;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.exception.NegocioException;
import com.algaworks.algafood.domain.model.repository.RestauranteRepository;
import com.algaworks.algafood.domain.model.service.RestauranteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRespository;

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private RestauranteModelDTOAssembler restauranteModelDtoAssembler;

    @Autowired
    private RestauranteInputDTODisassembler restauranteInputDtoDisassembler;

    @GetMapping
    public List<RestauranteModelDTO> listar() {
        return restauranteModelDtoAssembler.toCollectionModel(restauranteRespository.findAll());
    }

    @GetMapping(value = "{restauranteId}")
    public RestauranteModelDTO buscar(@PathVariable Long restauranteId) {

        return restauranteModelDtoAssembler.toModelDTO(restauranteService.buscarOuFalhar(restauranteId));
    }

    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteModelDTO salvar(@RequestBody @Valid RestauranteInputDTO restauranteImputDTO) {
        try {
            Restaurante restaurante = restauranteInputDtoDisassembler.toDomainObject(restauranteImputDTO);

            return restauranteModelDtoAssembler.toModelDTO(restauranteService.salvar(restaurante));
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
            return restauranteModelDtoAssembler.toModelDTO(restauranteService.salvar(restauranteAtual));
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }

    }

    @Transactional
    @PatchMapping("/{restauranteId}")
    public RestauranteModelDTO atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos, HttpServletRequest request) {
        return restauranteModelDtoAssembler.toModelDTO(restauranteService.atualizarParcial(restauranteId, campos, request));
    }

    @Transactional
    @PutMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long restauranteId) {
        restauranteService.ativar(restauranteId);
    }

    @Transactional
    @DeleteMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long restauranteId) {
        restauranteService.inativar(restauranteId);
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
    public void abrirRestaurente(@PathVariable Long restauranteId) {
        restauranteService.abrirRestaurante(restauranteId);
    }

    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{restauranteId}/fechamento")
    public void fecharRestaurente(@PathVariable Long restauranteId) {
        restauranteService.fecharRestaurante(restauranteId);
    }
}