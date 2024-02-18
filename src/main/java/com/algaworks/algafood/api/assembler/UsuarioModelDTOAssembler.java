package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.dto.model.UsuarioModelDTO;
import com.algaworks.algafood.api.utility.AlgaLinks;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class UsuarioModelDTOAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioModelDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public UsuarioModelDTOAssembler() {
        super(UsuarioController.class, UsuarioModelDTO.class);
    }

    @Override
    public UsuarioModelDTO toModel(Usuario usuario) {
        var usuarioModelDTO = createModelWithId(usuario.getId(), usuario);
        modelMapper.map(usuario, usuarioModelDTO);

        usuarioModelDTO.add(algaLinks.linkToUsuario("usuarios"));
        usuarioModelDTO.add(algaLinks.linkToGrupoUsuarios(usuario.getId(), "grupos-usuarios"));

        return usuarioModelDTO;
    }

    @Override
    public CollectionModel<UsuarioModelDTO> toCollectionModel(Iterable<? extends Usuario> entities) {
        return super.toCollectionModel(entities).add(algaLinks.linkToUsuario());
    }

}
