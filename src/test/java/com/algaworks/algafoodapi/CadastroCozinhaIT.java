package com.algaworks.algafoodapi;

import com.algaworks.algafood.AlgafoodApiApplication;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@SpringBootTest(classes = AlgafoodApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CadastroCozinhaIT {

	@LocalServerPort
	private int port;

	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

		RestAssured.given()
					.basePath("/cozinhas")
					.port(port)
					.accept(ContentType.JSON)
				.when()
					.get()
				.then()
				.statusCode(HttpStatus.OK.value());

	}


//
//	@Autowired
//	private CozinhaService cozinhaService;
//
//	@Test
//	public void deveGerarId_quandoCadastrarCozinhaComDadosCorretos() {
//		// Cenario
//		Cozinha  novaCozinha = new Cozinha();
//		novaCozinha.setNome("Chinesa");
//
//		// Ação
//		novaCozinha = cozinhaService.salvar(novaCozinha);
//
//		// Validação
//		Assertions.assertNotNull(novaCozinha);;
//		Assertions.assertNotNull(novaCozinha.getId());
//	}
//
//	@Test
//	public void deveFalhar_QuandoCadastrarCozinhaSemNome() {
//		// Cenario
//		Cozinha novaCozinha = new Cozinha();
//		novaCozinha.setNome(null);
//
//		// Ação e Validação
//		Assertions.assertThrows(ConstraintViolationException.class, () -> this.cozinhaService.salvar(novaCozinha));
//	}
//
//	@Test
//	public void deveFalhar_QuandoExcluirCozinhaEmUso() {
//		// Cenario
//		//Ação e Validação
//		Assertions.assertThrows(EntidadeEmUsoException.class, () -> cozinhaService.excluir(1L));
//	}
//
//	@Test
//	public void deveFalhar_QuandoExcluirCozinhaInexistente() {
//		// Cenario
//		//Ação e Validação
//		Assertions.assertThrows(EntidadeNaoEncontradaException.class, () -> cozinhaService.excluir(100L));
//	}

}