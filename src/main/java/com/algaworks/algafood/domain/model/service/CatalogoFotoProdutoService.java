package com.algaworks.algafood.domain.model.service;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatalogoFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public FotoProduto salvar(FotoProduto foto) {
        var restauranteId = foto.getRestauranteId();
        var produtoId = foto.getProduto().getId();

        var fotoExistente = produtoRepository.findFotoById(restauranteId, produtoId);

        if(fotoExistente.isPresent()) {
            produtoRepository.delete(fotoExistente.get());
        }

        return produtoRepository.save(foto);
    }
}
