package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FormaPagamentoModelDTOAssembler;
import com.algaworks.algafood.api.dto.model.FormaPagamentoModelDTO;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormasPagamentoController {

    @Autowired
    private RestauranteService restauranteService;
    @Autowired
    private FormaPagamentoModelDTOAssembler formaPagamentoModelDTOAssembler;

    @GetMapping
    public List<FormaPagamentoModelDTO> listar(@PathVariable Long restauranteId) {
        var restaurante = restauranteService.buscarOuFalhar(restauranteId);
        return formaPagamentoModelDTOAssembler.toCollectionModel(restaurante.getFormaPagamentos());
    }

    @Transactional
    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociarFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        restauranteService.desassociarFormaPagamento(restauranteId, formaPagamentoId);
    }

    @Transactional
    @PutMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associarFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        restauranteService.associarFormaPagamento(restauranteId, formaPagamentoId);
    }
}