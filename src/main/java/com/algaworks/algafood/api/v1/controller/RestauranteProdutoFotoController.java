package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.FotoProdutoModelDTOAssembler;
import com.algaworks.algafood.api.v1.dto.input.FotoProdutoInput;
import com.algaworks.algafood.api.v1.dto.model.FotoProdutoModelDTO;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.algaworks.algafood.domain.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

    // https://aws.amazon.com/pt/s3/

    @Autowired
    private CatalogoFotoProdutoService catalogoFotoProdutoService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private FotoProdutoModelDTOAssembler fotoProdutoModelDTOAssembler;
    @Autowired
    private FotoStorageService fotoStorageService;

    @Transactional
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoModelDTO atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId, @Valid FotoProdutoInput fotoProdutoInput) throws IOException {

        var produto = produtoService.buscarOuFalhar(restauranteId, produtoId);

        MultipartFile arquivo = fotoProdutoInput.getArquivo();

        FotoProduto foto = new FotoProduto();
        foto.setProduto(produto);
        foto.setDescricao(fotoProdutoInput.getDescricao());
        foto.setContentType(fotoProdutoInput.getArquivo().getContentType());
        foto.setTamanho(fotoProdutoInput.getArquivo().getSize());
        foto.setNomeArquivo(fotoProdutoInput.getArquivo().getOriginalFilename());

        var fotoSalva = catalogoFotoProdutoService.salvar(foto, arquivo.getInputStream());

        return fotoProdutoModelDTOAssembler.toModel(fotoSalva);

//        var nomeArquivo = UUID.randomUUID().toString() + "_" + fotoProdutoInput.getArquivo().getOriginalFilename();
//        var arquivoFoto = Path.of("C:\\ferramentas\\imagens\\catalogo", nomeArquivo);
//
//        System.out.println(arquivoFoto);
//        System.out.println(fotoProdutoInput.getArquivo().getContentType());
//
//        System.out.println(arquivoFoto.toAbsolutePath());
//        System.out.println(fotoProdutoInput.getDescricao());
//
//
//        try {
//            fotoProdutoInput.getArquivo().transferTo(arquivoFoto);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FotoProdutoModelDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        FotoProduto fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);
        return fotoProdutoModelDTOAssembler.toModel(fotoProduto);
    }

    @GetMapping
    public ResponseEntity<?> servirFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId, @RequestHeader(name = "accept") String acceptHeader) {
        try {
            var fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);

            MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
            List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);

            this.verificarCompatibilidadeMediaType(mediaTypeFoto,mediaTypesAceitas);

            var fotoRecuperada = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());

            if(fotoRecuperada.temUrl()) {
                return ResponseEntity
                        .status(HttpStatus.FOUND)
                        .header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
                        .build();
            } else {
                return ResponseEntity.ok().contentType(mediaTypeFoto).body(new InputStreamResource(fotoRecuperada.getInputStream()));
            }

        }catch (EntidadeNaoEncontradaException   | HttpMediaTypeNotAcceptableException  e) {
            return ResponseEntity.notFound().build();
        }
    }

    private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto, List<MediaType> mediaTypesAceitas) throws HttpMediaTypeNotAcceptableException {
        boolean compativel = mediaTypesAceitas.stream()
                .anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));

        if(!compativel) {
            throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
        }
    }

    @Transactional
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        catalogoFotoProdutoService.excluir(restauranteId, produtoId);
    }
}
