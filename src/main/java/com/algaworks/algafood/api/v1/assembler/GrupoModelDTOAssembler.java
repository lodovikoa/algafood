package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.controller.GrupoController;
import com.algaworks.algafood.api.v1.dto.model.GrupoModelDTO;
import com.algaworks.algafood.api.v1.AlgaLinks;
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

    public GrupoModelDTOAssembler() {
        super(GrupoController.class, GrupoModelDTO.class);
    }

    @Override
    public GrupoModelDTO toModel(Grupo grupo) {
        var grupoModelDTO = createModelWithId(grupo.getId(), grupo);
        modelMapper.map(grupo, grupoModelDTO);

        grupoModelDTO.add(algaLinks.linkToGrupos("grupos"));
        grupoModelDTO.add(algaLinks.linkToGrupoPermissoes(grupo.getId(), "permissoes"));

        return grupoModelDTO;
    }

    @Override
    public CollectionModel<GrupoModelDTO> toCollectionModel(Iterable<? extends Grupo> entities) {
        return super.toCollectionModel(entities).add(algaLinks.linkToGrupos());
    }


    //    public List<GrupoModelDTO> toCollectionModel(Collection<Grupo> grupos) {
//        return grupos.stream()
//                .map(grupo -> dtoModel(grupo))
//                .collect(Collectors.toList());
//    }
}
