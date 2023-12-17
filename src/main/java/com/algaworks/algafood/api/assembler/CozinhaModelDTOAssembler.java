package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.dto.model.CozinhaModelDTO;
import com.algaworks.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CozinhaModelDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public CozinhaModelDTO toModel(Cozinha cozinha) {
        return modelMapper.map(cozinha, CozinhaModelDTO.class);
    }

    public List<CozinhaModelDTO> toCollectionModel(List<Cozinha> cozinhas) {
        return cozinhas.stream()
                .map(cozinha -> toModel(cozinha))
                .collect(Collectors.toList());
    }
}
