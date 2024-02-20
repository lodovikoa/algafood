package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.GrupoModelDTOAssembler;
import com.algaworks.algafood.api.dto.model.GrupoModelDTO;
import com.algaworks.algafood.api.utility.AlgaLinks;
import com.algaworks.algafood.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private GrupoModelDTOAssembler grupoModelDTOAssembler;

    @Autowired
    private AlgaLinks algaLinks;

    @GetMapping
    public CollectionModel<GrupoModelDTO> listar(@PathVariable Long usuarioId) {
        var usuario = usuarioService.buscarOuFalhar(usuarioId);
        var gruposModel = grupoModelDTOAssembler
                .toCollectionModel(usuario.getGrupos())
                .removeLinks()
                .add(algaLinks.linkToUsuarioGrupoAssociacao(usuarioId, "associar"));

        gruposModel.getContent().forEach(gpModel -> {
            gpModel.add(algaLinks.linkToUsuarioGrupoDesassociacao(usuarioId, gpModel.getId(), "desassociar"));
        });

        return gruposModel;
    }

    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{grupoId}")
    public ResponseEntity<Void> desassociarGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.desassociarGrupo(usuarioId, grupoId);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{grupoId}")
    public ResponseEntity<Void> associarGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.associarGrupo(usuarioId, grupoId);
        return ResponseEntity.noContent().build();
    }
}
