package com.algaworks.algafood.domain.model.service;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.repository.CidadeRepository;
import com.algaworks.algafood.domain.model.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    public List<Cidade> listar() {
        return cidadeRepository.listar();
    }

    public Cidade buscar(Long cidadeId) {
        return cidadeRepository.buscar(cidadeId);
    }

    public Cidade salvar(Cidade cidade) {
        var estadoId = cidade.getEstado().getId();
        var estado = estadoRepository.buscar(estadoId);

        if(estado == null) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado cadastro de Estado com ID %d", estadoId));
        }

        cidade.setEstado(estado);
        return cidadeRepository.salvar(cidade);
    }

    public void remover(Long cidadeId) {
        try {
            cidadeRepository.remover(cidadeId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado a Cidade com ID %d", cidadeId));
        }
    }
}
