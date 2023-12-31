package com.algaworks.algafood.domain.model.service;

import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private RestauranteService restauranteService;

    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto buscarOuFalhar(Long restauranteId, Long produtoId) {

        // Verificar existência do restaurante informado
        restauranteService.buscarOuFalhar(restauranteId);

        return produtoRepository.findById(restauranteId, produtoId)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(restauranteId, produtoId));
    }

    public List<Produto> findByRestaurante(Restaurante restaurante) {
        return produtoRepository.findByRestaurante(restaurante);
    }
}
