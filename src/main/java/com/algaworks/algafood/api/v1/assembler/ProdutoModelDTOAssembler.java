package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.v1.dto.model.ProdutoModelDTO;
import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProdutoModelDTOAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoModelDTO> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public ProdutoModelDTOAssembler() {
        super(RestauranteProdutoController.class, ProdutoModelDTO.class);
    }

    @Override
    public ProdutoModelDTO toModel(Produto produto) {
        var produtoModelDTO = createModelWithId(produto.getId(), produto, produto.getRestaurante().getId());
        modelMapper.map(produto, produtoModelDTO);

        if (algaSecurity.podeConsultarRestaurantes()) {
            produtoModelDTO.add(algaLinks.linkToProdutos(produto.getRestaurante().getId(), "produtos"));
            produtoModelDTO.add(algaLinks.linkToFotoProduto(produto.getRestaurante().getId(), produto.getId(), "foto"));
        }

        return produtoModelDTO;
    }
}
