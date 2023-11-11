package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.repository.CozinhaRepository;
import com.algaworks.algafood.domain.model.repository.RestauranteRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teste")
public class TesteController {

    @Autowired
    private CozinhaRepository cozinhaRepository;
    @Autowired
    private RestauranteRespository restauranteRespository;

    @GetMapping("/cozinha/porNome")
    public List<Cozinha> consultarPorNome(String nome) {
        return cozinhaRepository.findTodasByNomeContaining(nome);
    }

    @GetMapping("/cozinha/porNomeUnico")
    public Optional<Cozinha> findCozinhaByNome(String nome) {
        return cozinhaRepository.findCozinhaByNome(nome);
    }

    @GetMapping("/cozinha/nomeExiste")
    public Boolean nomeExiste(String nome) {
        return cozinhaRepository.existsByNome(nome);
    }

    @GetMapping("/restaurante/PorTaxa")
    public List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return restauranteRespository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
    }

    @GetMapping("/restaurante/PorNomeCozinha")
    public List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long cozinhaId) {
        return restauranteRespository.consultarPorNomeECozinha(nome, cozinhaId);
    }

    @GetMapping("/restaurante/PorPrimeiroNome")
    public Optional<Restaurante> findFirstByNomeContaining(String nome) {
        return restauranteRespository.findFirstByNomeContaining(nome);
    }

    @GetMapping("/restaurante/Top2PorNome")
    public List<Restaurante> findTop2ByNomeContaining(String nome) {
        return restauranteRespository.findTop2ByNomeContaining(nome);
    }

    @GetMapping("/restaurante/countPorCozinha")
    public Integer coutPorNome(Long cozinhaId) {
        return restauranteRespository.countByCozinhaId(cozinhaId);
    }
}
