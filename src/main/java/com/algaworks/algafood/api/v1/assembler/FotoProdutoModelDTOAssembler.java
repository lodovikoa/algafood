package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.controller.RestauranteProdutoFotoController;
import com.algaworks.algafood.api.v1.dto.model.FotoProdutoModelDTO;
import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoModelDTOAssembler extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoModelDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public FotoProdutoModelDTOAssembler() {
        super(RestauranteProdutoFotoController.class, FotoProdutoModelDTO.class);
    }

    @Override
    public FotoProdutoModelDTO toModel(FotoProduto foto) {
        var fotoProdutoModelDTO = modelMapper.map(foto, FotoProdutoModelDTO.class);

        // Quem pode consultar restaurantes, também pode consultar os produtos e fotos
        if(algaSecurity.podeConsultarRestaurantes()) {
            fotoProdutoModelDTO.add(algaLinks.linkToFotoProduto(foto.getRestauranteId(), foto.getProduto().getId()));
            fotoProdutoModelDTO.add(algaLinks.linkToProdutos(foto.getRestauranteId(), foto.getProduto().getId(), "produto"));
        }

        return fotoProdutoModelDTO;
    }
}
