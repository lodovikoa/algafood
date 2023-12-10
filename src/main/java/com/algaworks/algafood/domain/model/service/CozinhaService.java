package com.algaworks.algafood.domain.model.service;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.model.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class CozinhaService {
    public static final String MSG_COZINHA_EM_USO = "Cozinha com ID %d não pode ser removida, pois está em uso.";
    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Cozinha salvar(Cozinha cozinha) {
        return cozinhaRepository.save(cozinha);
    }

    public void excluir(Long cozinhaId) {
        try{
            this.buscarOuFalhar(cozinhaId);
            cozinhaRepository.deleteById(cozinhaId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_COZINHA_EM_USO, cozinhaId));
        }
    }

    public Cozinha buscarOuFalhar(Long cozinhaId) {
        return cozinhaRepository.findById(cozinhaId)
                .orElseThrow(() -> new CozinhaNaoEncontradaException(cozinhaId));
    }
}
