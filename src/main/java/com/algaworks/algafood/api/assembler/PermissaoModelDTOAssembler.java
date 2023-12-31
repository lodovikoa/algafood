package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.dto.model.PermissaoModelDTO;
import com.algaworks.algafood.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PermissaoModelDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PermissaoModelDTO toModel(Permissao permissao) {
        return  modelMapper.map(permissao, PermissaoModelDTO.class);
    }

    public List<PermissaoModelDTO> toCollectionModel(Collection<Permissao> permissaos) {
        return permissaos.stream()
                .map(permissao -> toModel(permissao))
                .collect(Collectors.toList());
    }
}
