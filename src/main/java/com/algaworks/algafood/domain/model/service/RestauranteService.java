package com.algaworks.algafood.domain.model.service;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.repository.CozinhaRepository;
import com.algaworks.algafood.domain.model.repository.RestauranteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;

@Service
public class RestauranteService {

    public static final String MSG_RESTAURANTE_NAO_ENCONTRADO = "Não foi encontrado Restaurante com ID %d";
    @Autowired
    RestauranteRepository restauranteRespository;

    @Autowired
    CozinhaService cozinhaService;

    public Restaurante salvar(Restaurante restaurante) {
        var cozinha = cozinhaService.buscarOuFalhar(restaurante.getCozinha().getId());
        restaurante.setCozinha(cozinha);
        return restauranteRespository.save(restaurante) ;
    }

    public Restaurante atualizarParcial(Long restauranteId, Map<String, Object> campos) {
        var restauranteAtual = this.buscarOuFalhar(restauranteId);

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

    public Restaurante buscarOuFalhar(Long restauranteId) {
        return restauranteRespository.findById(restauranteId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format(MSG_RESTAURANTE_NAO_ENCONTRADO, restauranteId)));
    }

}
