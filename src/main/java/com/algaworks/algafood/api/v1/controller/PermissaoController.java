package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.PermissaoModelDTOAssembler;
import com.algaworks.algafood.api.v1.dto.model.PermissaoModelDTO;
import com.algaworks.algafood.domain.service.PermissaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissaoController {

    @Autowired
    private PermissaoService permissaoService;

    @Autowired
    private PermissaoModelDTOAssembler permissaoModelDTOAssembler;

    @GetMapping
    public CollectionModel<PermissaoModelDTO> listar() {
        var todasPermissoes = permissaoService.findAll();

        return permissaoModelDTOAssembler.toCollectionModel(todasPermissoes);
    }
}
