package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.model.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.repository.EstadoRepository;
import com.algaworks.algafood.domain.model.service.EstadoService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private EstadoService estadoService;

    @GetMapping
    public List<Estado> listar() {
        return estadoRepository.findAll();
    }

    @GetMapping("{estadoId}")
    public Estado buscar(@PathVariable Long estadoId) {
        return estadoService.buscarOuFalhar(estadoId);
    }

    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estado salvar(@RequestBody @Valid Estado estado) {
        return estadoService.salvar(estado);
    }

    @Transactional
    @PutMapping("{estadoId}")
    public Estado alterar(@PathVariable Long estadoId, @RequestBody @Valid Estado estado) {
        var estadoAtual = estadoService.buscarOuFalhar(estadoId);
        BeanUtils.copyProperties(estado, estadoAtual, "id");
        return estadoService.salvar(estadoAtual);
    }

    @Transactional
    @DeleteMapping("{estadoId}")
    public void remover(@PathVariable Long estadoId) {
        estadoService.remover(estadoId);
    }
}
