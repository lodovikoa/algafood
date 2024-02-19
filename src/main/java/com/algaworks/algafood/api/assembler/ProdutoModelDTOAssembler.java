package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.dto.model.ProdutoModelDTO;
import com.algaworks.algafood.api.utility.AlgaLinks;
import com.algaworks.algafood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProdutoModelDTOAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoModelDTO> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public ProdutoModelDTOAssembler() {
        super(RestauranteProdutoController.class, ProdutoModelDTO.class);
    }

    @Override
    public ProdutoModelDTO toModel(Produto produto) {
        var produtoModelDTO = createModelWithId(produto.getId(), produto, produto.getRestaurante().getId());
        modelMapper.map(produto, produtoModelDTO);

        produtoModelDTO.add(algaLinks.linkToProdutos(produto.getRestaurante().getId(), "produtos"));
        produtoModelDTO.add(algaLinks.linkToFotoProduto(produto.getRestaurante().getId(), produto.getId(), "foto"));

        return produtoModelDTO;
    }
}
