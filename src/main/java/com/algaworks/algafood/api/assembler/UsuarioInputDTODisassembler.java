package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.dto.input.UsuarioInputDTO;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioInputDTODisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Usuario toDomainObject(UsuarioInputDTO usuarioInputDTO) {
        return modelMapper.map(usuarioInputDTO, Usuario.class);
    }

    public void copyDomainObject(UsuarioInputDTO usuarioInputDTO, Usuario usuario) {
        modelMapper.map(usuarioInputDTO, usuario);
    }
}
