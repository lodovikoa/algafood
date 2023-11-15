package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.repository.CozinhaRepository;
import com.algaworks.algafood.domain.model.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.algaworks.algafood.infrastructue.repository.spec.RestauranteSpecs.*;

@RestController
@RequestMapping("/teste")
public class TesteController {

    @Autowired
    private CozinhaRepository cozinhaRepository;
    @Autowired
    private RestauranteRepository restauranteRepository;

    @GetMapping("/cozinhas/porNome")
    public List<Cozinha> consultarPorNome(String nome) {
        return cozinhaRepository.findTodasByNomeContaining(nome);
    }

    @GetMapping("/cozinhas/porNomeUnico")
    public Optional<Cozinha> findCozinhaByNome(String nome) {
        return cozinhaRepository.findCozinhaByNome(nome);
    }

    @GetMapping("/cozinhas/nomeExiste")
    public Boolean nomeExiste(String nome) {
        return cozinhaRepository.existsByNome(nome);
    }

    @GetMapping("/cozinhas/primeira")
    public Optional<Cozinha> cozinhaPrimeira() {
        return cozinhaRepository.buscarPrimeiro();
    }

    @GetMapping("/restaurantes/PorTaxa")
    public List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
    }

    @GetMapping("/restaurantes/PorNomeTaxa")
    public List<Restaurante> PorNomeTaxaCustomizado(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return restauranteRepository.findCustomizado(nome, taxaInicial, taxaFinal);
    }

    @GetMapping("/restaurantes/PorNomeCozinha")
    public List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long cozinhaId) {
        return restauranteRepository.consultarPorNomeECozinha(nome, cozinhaId);
    }

    @GetMapping("/restaurantes/PorPrimeiroNome")
    public Optional<Restaurante> findFirstByNomeContaining(String nome) {
        return restauranteRepository.findFirstByNomeContaining(nome);
    }

    @GetMapping("/restaurantes/Top2PorNome")
    public List<Restaurante> findTop2ByNomeContaining(String nome) {
        return restauranteRepository.findTop2ByNomeContaining(nome);
    }

    @GetMapping("/restaurantes/countPorCozinha")
    public Integer coutPorNome(Long cozinhaId) {
        return restauranteRepository.countByCozinhaId(cozinhaId);
    }

    @GetMapping("/restaurantes/comFreteGratis")
    public List<Restaurante> restaurantesComFreteGratis(String nome) {
        return restauranteRepository.findComFreteGratis(nome);
    }

    @GetMapping("/restaurantes/primeiro")
    public Optional<Restaurante> restaurantePrimeiro() {
        return restauranteRepository.buscarPrimeiro();
    }
}
