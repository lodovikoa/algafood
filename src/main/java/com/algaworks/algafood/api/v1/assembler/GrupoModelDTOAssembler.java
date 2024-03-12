package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.controller.GrupoController;
import com.algaworks.algafood.api.v1.dto.model.GrupoModelDTO;
import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class GrupoModelDTOAssembler extends RepresentationModelAssemblerSupport<Grupo, GrupoModelDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public GrupoModelDTOAssembler() {
        super(GrupoController.class, GrupoModelDTO.class);
    }

    @Override
    public GrupoModelDTO toModel(Grupo grupo) {
        var grupoModelDTO = createModelWithId(grupo.getId(), grupo);
        modelMapper.map(grupo, grupoModelDTO);

        if(algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
            grupoModelDTO.add(algaLinks.linkToGrupos("grupos"));
            grupoModelDTO.add(algaLinks.linkToGrupoPermissoes(grupo.getId(), "permissoes"));
        }

        return grupoModelDTO;
    }

    @Override
    public CollectionModel<GrupoModelDTO> toCollectionModel(Iterable<? extends Grupo> entities) {
        var collectionModel = super.toCollectionModel(entities);

        if(algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
            collectionModel.add(algaLinks.linkToGrupos());
        }

        return collectionModel;
    }
}
