package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.FormaPagamentoNaoEncontradoException;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormaPagamentoService {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;
    private static final String MSG_FORMA_PAGAMENTO_EM_USO = "Forma de Pagamento com ID %d não pode ser removida, pois está em uso.";

    public List<FormaPagamento> listar() {
        return formaPagamentoRepository.findAll();
    }

    public FormaPagamento salvar(FormaPagamento formaPagamento) {
        return formaPagamentoRepository.save(formaPagamento);
    }

    public void excluir(Long formaPagamentoId) {
        try {
            this.buscarOuFalhar(formaPagamentoId);
            formaPagamentoRepository.deleteById(formaPagamentoId);
            formaPagamentoRepository.flush();
        }catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_FORMA_PAGAMENTO_EM_USO, formaPagamentoId));
        }
    }

    public FormaPagamento buscarOuFalhar(Long formaPagamentoId) {
        return formaPagamentoRepository.findById(formaPagamentoId)
                .orElseThrow(() -> new FormaPagamentoNaoEncontradoException(formaPagamentoId));
    }
}
