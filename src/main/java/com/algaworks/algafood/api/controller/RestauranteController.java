package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.repository.RestauranteRepository;
import com.algaworks.algafood.domain.model.service.RestauranteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurante salvar(@RequestBody Restaurante restaurante) {
        return restauranteService.salvar(restaurante);

    }

    @PutMapping(value = "{restauranteId}")
    public Restaurante alterar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante) {
        var restauranteAtual = restauranteService.buscarOuFalhar(restauranteId);
        BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formaPagamentos", "endereco", "dataCadastro", "produtos");
        return restauranteService.salvar(restauranteAtual);

    }

    @PatchMapping("/{restauranteId}")
    public Restaurante atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos) {
        return restauranteService.atualizarParcial(restauranteId,campos);
    }
}
