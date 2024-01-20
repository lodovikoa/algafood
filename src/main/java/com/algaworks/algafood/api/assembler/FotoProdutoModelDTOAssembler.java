package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.dto.model.FotoProdutoModelDTO;
import com.algaworks.algafood.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoModelDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public FotoProdutoModelDTO toModel(FotoProduto foto) {
        return modelMapper.map(foto, FotoProdutoModelDTO.class);
    }
}
