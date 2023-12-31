package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.dto.model.ProdutoModelDTO;
import com.algaworks.algafood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProdutoModelDTOAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public ProdutoModelDTO toModel(Produto produto) {
        return modelMapper.map(produto, ProdutoModelDTO.class);
    }

    public List<ProdutoModelDTO> toCollectionMode(List<Produto> produtos) {
        return  produtos.stream()
                .map(produto -> toModel(produto))
                .collect(Collectors.toList());
    }
}
