package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.controller.GrupoPermissaoController;
import com.algaworks.algafood.api.v1.dto.model.PermissaoModelDTO;
import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PermissaoModelDTOAssembler extends RepresentationModelAssemblerSupport<Permissao, PermissaoModelDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public PermissaoModelDTOAssembler() {
        super(GrupoPermissaoController.class, PermissaoModelDTO.class);
    }

    @Override
    public PermissaoModelDTO toModel(Permissao permissao) {
        var permissaoModel =  modelMapper.map(permissao, PermissaoModelDTO.class);

        return permissaoModel;
    }

    @Override
    public CollectionModel<PermissaoModelDTO> toCollectionModel(Iterable<? extends Permissao> entities) {
        return super.toCollectionModel(entities).add(algaLinks.linkToPermissoes());
    }

    //    public List<PermissaoModelDTO> toCollectionModel(Collection<Permissao> permissaos) {
//        return permissaos.stream()
//                .map(permissao -> toModel(permissao))
//                .collect(Collectors.toList());
//    }
}
