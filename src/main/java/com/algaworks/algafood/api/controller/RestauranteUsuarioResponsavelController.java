package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.UsuarioModelDTOAssembler;
import com.algaworks.algafood.api.dto.model.UsuarioModelDTO;
import com.algaworks.algafood.domain.model.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController {

    @Autowired
    private RestauranteService restauranteService;
    @Autowired
    private UsuarioModelDTOAssembler usuarioModelDTOAssembler;

    @GetMapping
    public List<UsuarioModelDTO> listar(@PathVariable Long restauranteId) {
        var restaurante = restauranteService.buscarOuFalhar(restauranteId);
        return usuarioModelDTOAssembler.toCollectionModel(restaurante.getResponsaveis());
    }

    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{usuarioId}")
    public void removerResponsavel(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        restauranteService.desassociarResponsavel(restauranteId, usuarioId);
    }

    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{usuarioId}")
    public void adicioinarResponsavel(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        restauranteService.associarResponsavel(restauranteId, usuarioId);
    }
}
