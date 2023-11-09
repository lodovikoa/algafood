package com.algaworks.algafood.domain.model.service;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.repository.CozinhaRepository;
import com.algaworks.algafood.domain.model.repository.RestauranteRespository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

@Service
public class RestauranteService {

    @Autowired
    RestauranteRespository restauranteRespository;
    @Autowired
    CozinhaRepository cozinhaRepository;

    public Restaurante salvar(Restaurante restaurante) {
        var cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format("Não foi encontrado cadastro de Cozinha com ID %d", cozinhaId)
                ));

        restaurante.setCozinha(cozinha);
        return restauranteRespository.salvar(restaurante);
    }

    public Restaurante atualizarParcial(Long restauranteId, Map<String, Object> campos) {
        var restauranteAtual = restauranteRespository.buscar(restauranteId);
        if(restauranteAtual == null) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado Restaurante com ID %d", restauranteId));
        }

        merge(campos, restauranteAtual);
        return salvar(restauranteAtual);
    }

    private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

        dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
            Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
            field.setAccessible(true);

            Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
            System.out.println(nomePropriedade + " = " + valorPropriedade + " = " + novoValor);
            ReflectionUtils.setField(field, restauranteDestino, novoValor);
        });
    }

}
