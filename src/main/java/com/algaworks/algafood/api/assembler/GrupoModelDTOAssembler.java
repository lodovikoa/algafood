package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.dto.model.GrupoModelDTO;
import com.algaworks.algafood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GrupoModelDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public GrupoModelDTO dtoModel(Grupo grupo) {
        return modelMapper.map(grupo, GrupoModelDTO.class);
    }

    public List<GrupoModelDTO> toCollectionModel(Collection<Grupo> grupos) {
        return grupos.stream()
                .map(grupo -> dtoModel(grupo))
                .collect(Collectors.toList());
    }
}
