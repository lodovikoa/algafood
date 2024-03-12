package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.core.security.AlgaSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1" ,produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    @GetMapping
    public RootEntryPointModel root() {
        var rootEntryPointModel = new RootEntryPointModel();

        if(algaSecurity.podeConsultarCozinhas()) {
            rootEntryPointModel.add(algaLinks.linkToCozinha("cozinhas"));
        }

        if(algaSecurity.podePesquisarPedidos()) {
            rootEntryPointModel.add(algaLinks.linkToPedidos("pedidos"));
        }

        if(algaSecurity.podeConsultarRestaurantes()) {
            rootEntryPointModel.add(algaLinks.linkToRestaurante("restaurantes"));
        }

        if(algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
            rootEntryPointModel.add(algaLinks.linkToGrupos("grupos"));
            rootEntryPointModel.add(algaLinks.linkToUsuario("usuarios"));
            rootEntryPointModel.add(algaLinks.linkToPermissoes("permissões"));
        }

        if(algaSecurity.podeConsultarFormasPagamento()) {
            rootEntryPointModel.add(algaLinks.linkToFormasPagamento("formas-pagamento"));
        }

        if(algaSecurity.podeConsultarEstados()) {
            rootEntryPointModel.add(algaLinks.linkToEstado("estados"));
        }

        if(algaSecurity.podeConsultarCidades()) {
            rootEntryPointModel.add(algaLinks.linkToCidade("cidades"));
        }

        if(algaSecurity.podeConsultarEstatisticas()) {
            rootEntryPointModel.add(algaLinks.linkToEstatisticas("estatisticas"));
        }

        return rootEntryPointModel;
    }

    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
    }
}
