package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.controller.CidadeController;
import com.algaworks.algafood.api.v1.dto.model.CidadeModelDTO;
import com.algaworks.algafood.api.v1.AlgaLinks;
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

    public CidadeModelDTOAssembler() {
        super(CidadeController.class, CidadeModelDTO.class);
    }

    @Override
    public CidadeModelDTO toModel(Cidade cidade) {
        var cidadeModelDTO = createModelWithId(cidade.getId(), cidade);
        modelMapper.map(cidade, cidadeModelDTO);

        cidadeModelDTO.add(algaLinks.linkToCidade("cidades"));
        cidadeModelDTO.getEstado().add(algaLinks.linkToEstado(cidadeModelDTO.getEstado().getId()));

        return cidadeModelDTO;
    }

    @Override
    public CollectionModel<CidadeModelDTO> toCollectionModel(Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities).add(algaLinks.linkToCidade());
    }
}
