package com.algaworks.algafood.domain.model.service;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoEmissaoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public Pedido buscarOuFalhar(Long pedidoId) {
        return pedidoRepository.findById(pedidoId).orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
    }

}
