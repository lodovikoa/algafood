package com.algaworks.algafoodapi;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.service.CozinhaService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = AlgafoodApiApplication.class )
class CadastroCozinhaIntegrationTests {

	@Autowired
	private CozinhaService cozinhaService;

	@Test
	public void testarCadasgtroCozinhaComSucesso() {
		// Cenario
		Cozinha  novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");

		// Ação
		novaCozinha = cozinhaService.salvar(novaCozinha);

		// Validação
		Assertions.assertNotNull(novaCozinha);;
		Assertions.assertNotNull(novaCozinha.getId());
	}

	@Test
	public void testarCadastroCozinhaSemNome() {
		// Cenario
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);

		// Ação e Validação
		Assertions.assertThrows(ConstraintViolationException.class, () -> this.cozinhaService.salvar(novaCozinha));
	}

}