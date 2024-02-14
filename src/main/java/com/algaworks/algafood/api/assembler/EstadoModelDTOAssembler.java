package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.EstadoController;
import com.algaworks.algafood.api.dto.model.EstadoModelDTO;
import com.algaworks.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EstadoModelDTOAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoModelDTO> {
    @Autowired
    private ModelMapper modelMapper;

    public EstadoModelDTOAssembler() {
        super(EstadoController.class, EstadoModelDTO.class);
    }

    @Override
    public EstadoModelDTO toModel(Estado estado) {
        var estadoModelDTO = createModelWithId(estado.getId(), estado);
        modelMapper.map(estado, estadoModelDTO);

        estadoModelDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstadoController.class).listar()).withRel("estados"));
        return estadoModelDTO;
    }

    @Override
    public CollectionModel<EstadoModelDTO> toCollectionModel(Iterable<? extends Estado> entities) {
        return super.toCollectionModel(entities).add(WebMvcLinkBuilder.linkTo(EstadoController.class).withSelfRel());
    }

//    public List<EstadoModelDTO> toCollectionModel(List<Estado> estados) {
//        return estados.stream()
//                .map(estado -> toModel(estado))
//                .collect(Collectors.toList());
//    }
}
