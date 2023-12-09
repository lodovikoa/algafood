package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.exception.NegocioException;
import com.algaworks.algafood.domain.model.repository.RestauranteRepository;
import com.algaworks.algafood.domain.model.service.RestauranteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
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
    RestauranteRepository restauranteRespository;

    @Autowired
    RestauranteService restauranteService;

    @GetMapping
    public List<Restaurante> listar() {
        return restauranteRespository.findAll();
    }

    @GetMapping(value = "{restauranteId}")
    public Restaurante buscar(@PathVariable Long restauranteId) {
        return restauranteService.buscarOuFalhar(restauranteId);
    }

    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurante salvar(@RequestBody @Valid Restaurante restaurante) {
        try {
            return restauranteService.salvar(restaurante);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @Transactional
    @PutMapping(value = "{restauranteId}")
    public Restaurante alterar(@PathVariable Long restauranteId, @RequestBody @Valid Restaurante restaurante) {
        var restauranteAtual = restauranteService.buscarOuFalhar(restauranteId);
        BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formaPagamentos", "endereco", "dataCadastro", "produtos");

        try {
            return restauranteService.salvar(restauranteAtual);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }

    }

    @Transactional
    @PatchMapping("/{restauranteId}")
    public Restaurante atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos, HttpServletRequest request) {
        return restauranteService.atualizarParcial(restauranteId, campos, request);
    }
}
