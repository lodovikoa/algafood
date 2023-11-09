package com.algaworks.algafood.jpa;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.repository.CozinhaRepository;
import com.algaworks.algafood.domain.model.repository.RestauranteRespository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class ConsultarRestauranteMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext =  new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        RestauranteRespository restauranteRespository = applicationContext.getBean(RestauranteRespository.class);

        List<Restaurante> restaurantes = restauranteRespository.listar();

        for(Restaurante restaurante: restaurantes) {
            System.out.printf("%s - %f - %s\n", restaurante.getNome(), restaurante.getTaxaFrete(), restaurante.getCozinha().getNome());
        }
    }
}
