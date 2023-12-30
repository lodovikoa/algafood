package com.algaworks.algafood.domain.model.service;

import com.algaworks.algafood.core.validation.ValidacaoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.repository.RestauranteRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;

import java.lang.reflect.Field;
import java.util.Map;

@Service
public class RestauranteService {
    @Autowired
    RestauranteRepository restauranteRespository;
    @Autowired
    CozinhaService cozinhaService;
    @Autowired
    CidadeService cidadeService;
    @Autowired
    FormaPagamentoService formaPagamentoService;
    @Autowired
    private SmartValidator validator;

    public Restaurante salvar(Restaurante restaurante) {
        var cozinha = cozinhaService.buscarOuFalhar(restaurante.getCozinha().getId());
        var cidade = cidadeService.buscarOuFalhar(restaurante.getEndereco().getCidade().getId());

        restaurante.setCozinha(cozinha);
        restaurante.getEndereco().setCidade(cidade);
        return restauranteRespository.save(restaurante) ;
    }

    public Restaurante atualizarParcial(Long restauranteId, Map<String, Object> campos,  HttpServletRequest request) {
        var restauranteAtual = this.buscarOuFalhar(restauranteId);

        merge(campos, restauranteAtual, request);
        validate(restauranteAtual, "restaurante");

        return salvar(restauranteAtual);
    }

    private void validate(Restaurante restaurante, String objectName) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
        validator.validate(restaurante, bindingResult);

        if(bindingResult.hasErrors()) {
            throw new ValidacaoException(bindingResult);
        }
    }

    private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino, HttpServletRequest request) {
        var servletHttpRequest = new ServletServerHttpRequest(request);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);

            Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

            dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
                Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
                field.setAccessible(true);

                Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
                System.out.println(nomePropriedade + " = " + valorPropriedade + " = " + novoValor);
                ReflectionUtils.setField(field, restauranteDestino, novoValor);
            });
        } catch (IllegalArgumentException e) {
            var rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, servletHttpRequest);
        }
    }

    public void ativar(Long restauranteId) {
        Restaurante restauranteAtual = this.buscarOuFalhar(restauranteId);
        restauranteAtual.ativar();
    }

    public void inativar(Long restauranteId) {
        Restaurante restauranteAtual = this.buscarOuFalhar(restauranteId);
        restauranteAtual.inativar();
    }

    public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        var restaurante = this.buscarOuFalhar(restauranteId);
        var formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);

        restaurante.removerFormaPagamento(formaPagamento);
    }

    public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        var restaurante = this.buscarOuFalhar(restauranteId);
        var formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);

        restaurante.adicionarFormaPagamento(formaPagamento);
    }

    public Restaurante buscarOuFalhar(Long restauranteId) {
        return restauranteRespository.findById(restauranteId)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
    }

}
