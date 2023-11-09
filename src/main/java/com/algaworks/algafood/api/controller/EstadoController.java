package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.model.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.repository.EstadoRepository;
import com.algaworks.algafood.domain.model.service.EstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return estadoRepository.listar();
    }

    @GetMapping("{estadoId}")
    public ResponseEntity<Estado> buscar(@PathVariable Long estadoId) {
        var estado = estadoRepository.buscar(estadoId);
        if(estado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(estado);
    }

    @PostMapping
    public ResponseEntity<Estado> salvar(@RequestBody Estado estado) {
        estado = estadoService.salvar(estado);
        return ResponseEntity.ok(estado);
    }

    @PutMapping("{estadoId}")
    public ResponseEntity<Estado> alterar(@PathVariable Long estadoId, @RequestBody Estado estado) {
        var estadoAtual = estadoRepository.buscar(estadoId);
        if(estadoAtual != null) {
            BeanUtils.copyProperties(estado, estadoAtual, "id");
            estadoAtual = estadoService.salvar(estadoAtual);
            return ResponseEntity.ok(estadoAtual);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{estadoId}")
    public ResponseEntity<String> remover(@PathVariable Long estadoId) {
        try {
            estadoService.remover(estadoId);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
