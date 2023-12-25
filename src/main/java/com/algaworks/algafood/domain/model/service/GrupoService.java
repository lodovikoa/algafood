package com.algaworks.algafood.domain.model.service;

import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.model.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    public List<Grupo> listar() {
        return grupoRepository.findAll();
    }

    public Grupo salvar(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    public Grupo buscarOuFalhar(Long idGrupo) {
        return grupoRepository.findById(idGrupo)
                .orElseThrow(() -> new GrupoNaoEncontradoException(idGrupo));
    }
}
