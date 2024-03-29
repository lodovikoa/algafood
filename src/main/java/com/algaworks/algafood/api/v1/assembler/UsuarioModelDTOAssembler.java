package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.controller.UsuarioController;
import com.algaworks.algafood.api.v1.dto.model.UsuarioModelDTO;
import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.core.security.AlgaSecurity;
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

    @Autowired
    private AlgaSecurity algaSecurity;

    public UsuarioModelDTOAssembler() {
        super(UsuarioController.class, UsuarioModelDTO.class);
    }

    @Override
    public UsuarioModelDTO toModel(Usuario usuario) {
        var usuarioModelDTO = createModelWithId(usuario.getId(), usuario);
        modelMapper.map(usuario, usuarioModelDTO);

        if(algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
            usuarioModelDTO.add(algaLinks.linkToUsuario("usuarios"));
            usuarioModelDTO.add(algaLinks.linkToGrupoUsuarios(usuario.getId(), "grupos-usuarios"));
        }

        return usuarioModelDTO;
    }

    @Override
    public CollectionModel<UsuarioModelDTO> toCollectionModel(Iterable<? extends Usuario> entities) {
        return super.toCollectionModel(entities).add(algaLinks.linkToUsuario());
    }

}
