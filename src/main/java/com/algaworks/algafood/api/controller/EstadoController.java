package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.EstadoInputDTODisassembler;
import com.algaworks.algafood.api.assembler.EstadoModelDTOAssembler;
import com.algaworks.algafood.api.dto.input.EstadoInputDTO;
import com.algaworks.algafood.api.dto.model.EstadoModelDTO;
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
    private EstadoModelDTOAssembler estadoModelDTOAssembler;

    @Autowired
    private EstadoInputDTODisassembler estadoInputDTODisassembler;

    @Autowired
    private EstadoService estadoService;

    @GetMapping
    public List<EstadoModelDTO> listar() {
        List<Estado> todosEstados = estadoRepository.findAll();
        return estadoModelDTOAssembler.toCollectionModel(todosEstados);
    }

    @GetMapping("{estadoId}")
    public EstadoModelDTO buscar(@PathVariable Long estadoId) {
        Estado estado = estadoService.buscarOuFalhar(estadoId);
        return estadoModelDTOAssembler.toModel(estado);
    }

    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoModelDTO adicionar(@RequestBody @Valid EstadoInputDTO estadoInputDTO) {
        Estado estado = estadoInputDTODisassembler.toDomainObject(estadoInputDTO);
        estado = estadoService.salvar(estado);
        return estadoModelDTOAssembler.toModel(estado);
    }

    @Transactional
    @PutMapping("{estadoId}")
    public EstadoModelDTO alterar(@PathVariable Long estadoId, @RequestBody @Valid EstadoInputDTO estadoInputDTO) {
        var estadoAtual = estadoService.buscarOuFalhar(estadoId);

        estadoInputDTODisassembler.copyToDomainObject(estadoInputDTO, estadoAtual);
        estadoAtual = estadoService.salvar(estadoAtual);

        return estadoModelDTOAssembler.toModel(estadoAtual);
    }

    @Transactional
    @DeleteMapping("{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long estadoId) {
        estadoService.remover(estadoId);
    }
}
