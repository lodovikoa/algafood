package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.repository.CozinhaRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = AlgafoodApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CadastroCozinhaIT {

	@LocalServerPort
	private int port;

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@BeforeEach   // Preparação: Esse método executa antes de cada um dos métodos de teste.
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";

		databaseCleaner.clearTables();
		this.prepararDados();
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {

		RestAssured.given()
					.accept(ContentType.JSON)
				.when()
					.get()
				.then()
				.statusCode(HttpStatus.OK.value());

	}

	@Test
	public void deveConter2Cozinhas_QuandoConsultarCozinhas() {

		RestAssured.given()
					.accept(ContentType.JSON)
				.when()
					.get()
				.then()
					.body("", Matchers.hasSize(2))
					.body("nome", Matchers.hasItems("Americana", "Tailandesa"));
	}

	@Test
	public void deveRetornarStatus201_QuandoCadastrarCozinha() {
		RestAssured.given()
				.body("{\"nome\":\"Chinesa\"}")
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
			.when()
				.post()
			.then()
				.statusCode(HttpStatus.CREATED.value());
	}

	@Test
	public void deverRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {
		RestAssured.given()
					.pathParam("cozinhaId", 2)
					.accept(ContentType.JSON)
				.when()
				.	get("/{cozinhaId}")
				.then()
					.statusCode(HttpStatus.OK.value())
				.body("nome", CoreMatchers.equalTo("Americana"));
	}

	@Test
	public void deverRetornarStatus404_QuandoConsultarCozinhaInexistente() {
		RestAssured.given()
				.pathParam("cozinhaId", 200)
				.accept(ContentType.JSON)
				.when()
				.	get("/{cozinhaId}")
				.then()
				.statusCode(HttpStatus.NOT_FOUND.value());
	}

	private void prepararDados() {
		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Tailandesa");
		cozinhaRepository.save(cozinha1);

		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("Americana");
		cozinhaRepository.save(cozinha2);
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