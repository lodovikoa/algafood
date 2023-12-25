package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.dto.input.GrupoInputDTO;
import com.algaworks.algafood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GrupoInputDTODisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Grupo toDomaninObject(GrupoInputDTO grupoInputDTO) {
        return modelMapper.map(grupoInputDTO, Grupo.class);
    }

    public void copyDomainObject(GrupoInputDTO grupoInputDTO, Grupo grupo) {
        modelMapper.map(grupoInputDTO, grupo);
    }
}
