package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.GrupoModelDTOAssembler;
import com.algaworks.algafood.api.dto.model.GrupoModelDTO;
import com.algaworks.algafood.domain.model.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private GrupoModelDTOAssembler grupoModelDTOAssembler;

    @GetMapping
    public List<GrupoModelDTO> listar(@PathVariable Long usuarioId) {
        var usuario = usuarioService.buscarOuFalhar(usuarioId);
        return grupoModelDTOAssembler.toCollectionModel(usuario.getGrupos());
    }

    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{grupoId}")
    public void dessasociarGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.desassociarGrupo(usuarioId, grupoId);
    }

    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{grupoId}")
    public void asociarGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.associarGrupo(usuarioId, grupoId);
    }
}
