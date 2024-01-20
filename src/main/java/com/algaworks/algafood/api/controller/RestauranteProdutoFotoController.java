package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FotoProdutoModelDTOAssembler;
import com.algaworks.algafood.api.dto.input.FotoProdutoInput;
import com.algaworks.algafood.api.dto.model.FotoProdutoModelDTO;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.service.CatalogoFotoProdutoService;
import com.algaworks.algafood.domain.model.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

    @Autowired
    private CatalogoFotoProdutoService catalogoFotoProdutoService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private FotoProdutoModelDTOAssembler fotoProdutoModelDTOAssembler;

    @Transactional
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoModelDTO atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId, @Valid FotoProdutoInput fotoProdutoInput) {

        var produto = produtoService.buscarOuFalhar(restauranteId, produtoId);

        FotoProduto foto = new FotoProduto();
        foto.setProduto(produto);
        foto.setDescricao(fotoProdutoInput.getDescricao());
        foto.setContentType(fotoProdutoInput.getArquivo().getContentType());
        foto.setTamanho(fotoProdutoInput.getArquivo().getSize());
        foto.setNomeArquivo(fotoProdutoInput.getArquivo().getOriginalFilename());

        var fotoSalva = catalogoFotoProdutoService.salvar(foto);

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
}
