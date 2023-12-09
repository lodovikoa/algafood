package com.algaworks.algafood.domain.model.service;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.repository.CidadeRepository;
import com.algaworks.algafood.domain.model.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CidadeService {
    public static final String MSG_CIDADE_EM_USU = "Cidade com ID %d não pode ser removida, pois está em uso.";
    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private EstadoService estadoService;

    public List<Cidade> listar() {
        return cidadeRepository.findAll();
    }

    public Optional<Cidade> buscar(Long cidadeId) {
        return cidadeRepository.findById(cidadeId);
    }

    public Cidade salvar(Cidade cidade) {
        var estado = estadoService.buscarOuFalhar(cidade.getEstado().getId());

        cidade.setEstado(estado);
        return cidadeRepository.save(cidade);
    }

    public void remover(Long cidadeId) {
        try {
            if (!cidadeRepository.existsById(cidadeId)) {
                throw new CidadeNaoEncontradaException(cidadeId);
            }

            cidadeRepository.deleteById(cidadeId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_CIDADE_EM_USU,cidadeId ));
        }
    }

    public Cidade buscarOuFalhar(Long cidadeId) {
        return cidadeRepository.findById(cidadeId)
                .orElseThrow(() -> new CidadeNaoEncontradaException(cidadeId));
    }
}
