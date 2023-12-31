package com.algaworks.algafood.domain.model.service;

import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.model.exception.PermissaoNaoEncontradaException;
import com.algaworks.algafood.domain.model.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissaoService {

    @Autowired
    private PermissaoRepository permissaoRepository;

    public Permissao buscarOuFalhar(Long permissaoId) {
        return permissaoRepository.findById(permissaoId)
                .orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId));
    }
}
