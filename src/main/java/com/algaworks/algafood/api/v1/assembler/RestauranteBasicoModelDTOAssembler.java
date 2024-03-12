package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.dto.model.RestauranteBasicoModelDTO;
import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteBasicoModelDTOAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteBasicoModelDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public RestauranteBasicoModelDTOAssembler() {
        super(RestauranteController.class, RestauranteBasicoModelDTO.class);
    }

    @Override
    public RestauranteBasicoModelDTO toModel(Restaurante restaurante) {
        var restauranteModel = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restauranteModel, restauranteModel);

        if(algaSecurity.podeConsultarRestaurantes()) {
            restauranteModel.add(algaLinks.linkToRestaurante("restaurantes"));
        }
        if(algaSecurity.podeConsultarCozinhas()) {
            restauranteModel.getCozinha().add(algaLinks.linkToCozinha(restaurante.getId()));
        }

        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteBasicoModelDTO> toCollectionModel(Iterable<? extends Restaurante> entities) {
        var collectionModel = super.toCollectionModel(entities);

        if(algaSecurity.podeConsultarRestaurantes()) {
            collectionModel.add(algaLinks.linkToRestaurante());
        }

        return collectionModel;
    }
}
