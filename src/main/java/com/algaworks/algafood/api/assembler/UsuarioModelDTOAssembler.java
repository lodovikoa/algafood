package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.dto.model.UsuarioModelDTO;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioModelDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioModelDTO toModel(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioModelDTO.class);
    }

    public List<UsuarioModelDTO> toCollectionModel(Collection<Usuario> usuarios) {
        return usuarios.stream()
                .map(usuario -> toModel(usuario))
                .collect(Collectors.toList());
    }
}
