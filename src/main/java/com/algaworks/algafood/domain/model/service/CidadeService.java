package com.algaworks.algafood.domain.model.service;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.repository.CidadeRepository;
import com.algaworks.algafood.domain.model.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CidadeService {

    public static final String MSG_CIDADE_NAO_ENCONTRADA = "Não foi encontrado a Cidade com ID %d";
    public static final String MSG_ESTADO_NAO_ENCONTRADO = "Não foi encontrado cadastro de Estado com ID %d";
    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    public List<Cidade> listar() {
        return cidadeRepository.findAll();
    }

    public Optional<Cidade> buscar(Long cidadeId) {
        return cidadeRepository.findById(cidadeId);
    }

    public Cidade salvar(Cidade cidade) {
        var estadoId = cidade.getEstado().getId();
        var estado = estadoRepository.findById(estadoId);

        if(estado.isEmpty()) {
            throw new EntidadeNaoEncontradaException(String.format(MSG_ESTADO_NAO_ENCONTRADO, estadoId));
        }

        cidade.setEstado(estado.get());
        return cidadeRepository.save(cidade);
    }

    public void remover(Long cidadeId) {
        if(!cidadeRepository.existsById(cidadeId)) {
            throw new EntidadeNaoEncontradaException(String.format(MSG_CIDADE_NAO_ENCONTRADA, cidadeId));
        }

        cidadeRepository.deleteById(cidadeId);
    }

    public Cidade buscarOuFalhar(Long cidadeId) {
        return cidadeRepository.findById(cidadeId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format(MSG_CIDADE_NAO_ENCONTRADA, cidadeId)));
    }
}
