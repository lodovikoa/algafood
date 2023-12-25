package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.GrupoInputDTODisassembler;
import com.algaworks.algafood.api.assembler.GrupoModelDTOAssembler;
import com.algaworks.algafood.api.dto.input.GrupoInputDTO;
import com.algaworks.algafood.api.dto.model.GrupoModelDTO;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.service.GrupoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;
    @Autowired
    private GrupoModelDTOAssembler grupoModelDTOAssembler;

    @Autowired
    private GrupoInputDTODisassembler grupoInputDTODisassembler;

    @GetMapping
    public List<GrupoModelDTO> listar() {
        var todosGrupos = grupoService.listar();
        return grupoModelDTOAssembler.toCollectionModel(todosGrupos);
    }

    @GetMapping("/{idGrupo}")
    public GrupoModelDTO buscarPorId(@PathVariable Long idGrupo) {
        var grupo = grupoService.buscarOuFalhar(idGrupo);
        return grupoModelDTOAssembler.dtoModel(grupo);
    }

    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoModelDTO cadastrar(@Valid @RequestBody GrupoInputDTO grupoInputDTO) {
        var grupo = grupoInputDTODisassembler.toDomaninObject(grupoInputDTO);
        grupo = grupoService.salvar(grupo);
        return grupoModelDTOAssembler.dtoModel(grupo);
    }

    @Transactional
    @PutMapping("/{idGrupo}")
    public GrupoModelDTO alterar(@Valid @RequestBody GrupoInputDTO grupoInputDTO, @PathVariable Long idGrupo) {
        var grupoOriginal = grupoService.buscarOuFalhar(idGrupo);
        grupoInputDTODisassembler.copyDomainObject(grupoInputDTO, grupoOriginal);
        var grupo = grupoService.salvar(grupoOriginal);
        return grupoModelDTOAssembler.dtoModel(grupo);
    }
}
