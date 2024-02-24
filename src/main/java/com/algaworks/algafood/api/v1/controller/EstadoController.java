package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.EstadoInputDTODisassembler;
import com.algaworks.algafood.api.v1.assembler.EstadoModelDTOAssembler;
import com.algaworks.algafood.api.v1.dto.input.EstadoInputDTO;
import com.algaworks.algafood.api.v1.dto.model.EstadoModelDTO;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.EstadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/estados")
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
    public CollectionModel<EstadoModelDTO> listar() {
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
