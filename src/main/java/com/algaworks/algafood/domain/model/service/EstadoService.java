package com.algaworks.algafood.domain.model.service;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.model.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class EstadoService {
    public static final String MSG_ESTADO_EM_USO = "Estado com ID %d não pode ser removido, pois está em uso.";
    @Autowired
    private EstadoRepository estadoRepository;

    public Estado salvar(Estado estado) {
        return estadoRepository.save(estado);
    }

    public void remover(Long estadoId) {
        try {
            this.buscarOuFalhar(estadoId);

            estadoRepository.deleteById(estadoId);
            estadoRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_ESTADO_EM_USO, estadoId));
        } catch (Exception e) {
            throw new EntidadeEmUsoException(String.format(MSG_ESTADO_EM_USO,estadoId));
        }
    }

    public Estado buscarOuFalhar(Long estadoId) {
        return estadoRepository.findById(estadoId)
                .orElseThrow(() -> new EstadoNaoEncontradoException(estadoId));
    }
}
