package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PermissaoModelDTOAssembler;
import com.algaworks.algafood.api.dto.model.PermissaoModelDTO;
import com.algaworks.algafood.domain.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private PermissaoModelDTOAssembler permissaoModelDTOAssembler;

    @GetMapping
    public List<PermissaoModelDTO> listar(@PathVariable Long grupoId) {
        var grupo = grupoService.buscarOuFalhar(grupoId);
        return permissaoModelDTOAssembler.toCollectionModel(grupo.getPermissoes());
    }

    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{permissaoId}")
    public void desassociarPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.desassociarPermissao(grupoId, permissaoId);
    }

    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{permissaoId}")
    public void associarPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.associarPermissao(grupoId, permissaoId);
    }
}
