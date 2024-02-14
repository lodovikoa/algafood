package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.controller.UsuarioGrupoController;
import com.algaworks.algafood.api.dto.model.UsuarioModelDTO;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioModelDTOAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioModelDTO> {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioModelDTOAssembler() {
        super(UsuarioController.class, UsuarioModelDTO.class);
    }

    @Override
    public UsuarioModelDTO toModel(Usuario usuario) {
        var usuarioModelDTO = createModelWithId(usuario.getId(), usuario);
        modelMapper.map(usuario, usuarioModelDTO);

        usuarioModelDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).listar()).withRel("usuarios"));
        usuarioModelDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioGrupoController.class).listar(usuario.getId())).withRel("grupos-usuarios"));

        return usuarioModelDTO;
    }

    @Override
    public CollectionModel<UsuarioModelDTO> toCollectionModel(Iterable<? extends Usuario> entities) {
        return super.toCollectionModel(entities).add(WebMvcLinkBuilder.linkTo(UsuarioController.class).withSelfRel());
    }

}
