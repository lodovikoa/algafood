package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.controller.EstadoController;
import com.algaworks.algafood.api.v1.dto.model.EstadoModelDTO;
import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class EstadoModelDTOAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoModelDTO> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public EstadoModelDTOAssembler() {
        super(EstadoController.class, EstadoModelDTO.class);
    }

    @Override
    public EstadoModelDTO toModel(Estado estado) {
        var estadoModelDTO = createModelWithId(estado.getId(), estado);
        modelMapper.map(estado, estadoModelDTO);

        if(algaSecurity.podeConsultarEstados()) {
            estadoModelDTO.add(algaLinks.linkToEstado("estados"));
        }

        return estadoModelDTO;
    }

    @Override
    public CollectionModel<EstadoModelDTO> toCollectionModel(Iterable<? extends Estado> entities) {
        var collectionModel = super.toCollectionModel(entities);

        if(algaSecurity.podeConsultarEstados()) {
            collectionModel.add(algaLinks.linktoEstado());
        }

        return collectionModel;
    }

}
