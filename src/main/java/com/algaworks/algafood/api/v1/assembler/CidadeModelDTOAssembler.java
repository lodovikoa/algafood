package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.controller.CidadeController;
import com.algaworks.algafood.api.v1.dto.model.CidadeModelDTO;
import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CidadeModelDTOAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeModelDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public CidadeModelDTOAssembler() {
        super(CidadeController.class, CidadeModelDTO.class);
    }

    @Override
    public CidadeModelDTO toModel(Cidade cidade) {
        var cidadeModelDTO = createModelWithId(cidade.getId(), cidade);
        modelMapper.map(cidade, cidadeModelDTO);

        if(algaSecurity.podeConsultarCidades()) {
            cidadeModelDTO.add(algaLinks.linkToCidade("cidades"));
        }

        if(algaSecurity.podeConsultarEstados()) {
            cidadeModelDTO.getEstado().add(algaLinks.linkToEstado(cidadeModelDTO.getEstado().getId()));
        }

        return cidadeModelDTO;
    }

    @Override
    public CollectionModel<CidadeModelDTO> toCollectionModel(Iterable<? extends Cidade> entities) {
        var collectionModel = super.toCollectionModel(entities);

        if(algaSecurity.podeConsultarCidades()) {
            collectionModel.add(algaLinks.linkToCidade());
        }

        return collectionModel;
    }
}
