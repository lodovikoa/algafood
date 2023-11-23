package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.repository.CidadeRepository;
import com.algaworks.algafood.domain.model.service.CidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

    @Autowired
    CidadeService cidadeService;

    @GetMapping
    public List<Cidade> listar() {
        return cidadeService.listar();
    }

    @GetMapping("{cidadeId}")
    public Cidade buscar(@PathVariable Long cidadeId) {
        return cidadeService.buscarOuFalhar(cidadeId);
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Cidade cidade) {
        try{
             cidade = cidadeService.salvar(cidade);
             return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{cidadeId}")
    public Cidade alterar(@PathVariable Long cidadeId, @RequestBody Cidade cidade) {
        var cidadeAtual = cidadeService.buscarOuFalhar(cidadeId);

        BeanUtils.copyProperties(cidade, cidadeAtual, "id");
        return cidadeService.salvar(cidadeAtual);
    }

    @DeleteMapping("{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId) {
        cidadeService.remover(cidadeId);
    }
}
