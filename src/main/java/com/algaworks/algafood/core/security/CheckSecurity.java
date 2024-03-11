package com.algaworks.algafood.core.security;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

public @interface CheckSecurity {
    // TODO Ainda não estamos usando essa definição de segurança
    // @PreAuthorize --> Verifica permissões antes de executar o método no controller
    // @PosAuthorize --> Verifica permissões somente após executar o método no controle - usar com moderação
    public @interface Cozinhas {
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_COZINHAS')") @Retention(RUNTIME) @Target(METHOD)
        public @interface PodeEditar{}

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()") @Retention(RUNTIME) @Target(METHOD)
        public @interface PodeConsultar{}
    }

    public @interface Restaurantes {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_RESTAURANTES')") @Retention(RUNTIME) @Target(METHOD)
        public @interface PodeGerenciarCadastro {}

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_RESTAURANTES') or @algaSecurity.gerenciarRestaurante(#restauranteId)") @Retention(RUNTIME) @Target(METHOD)
        public @interface PodeGerenciarFuncionamento {}

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()") @Retention(RUNTIME) @Target(METHOD)
        public @interface PodeConsultar {}
    }

    public @interface Pedidos {

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @PostAuthorize("hasAuthority(CONSULTAR_PEDIDOS) or @algaSecurity.usuarioAutenticadoIgual(returnObject.cliente.id) or @algaSecurity.gerenciarRestaurante(returnObject.restaurante.id)")
        @Retention(RUNTIME) @Target(METHOD)
        public @interface PodeBuscar {}


        @PreAuthorize("hasAuthority('SCOPE_READ') and hasAuthority('CONSULTAR_PEDIDOS') or " +
                "@algaSecurity.usuarioAutenticadoIgual(returnObject.cliente.id) or" +
                "@algaSecurity.gerenciarRestaurante(#filtro.restauranteId)")
        @Retention(RUNTIME) @Target(METHOD)
        public @interface PodePesquisar {}


        @PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()") @Retention(RUNTIME) @Target(METHOD)
        public @interface PodeCriar {}

        @PreAuthorize("@algaSecurity.podeGerenciarPedidos(#codigoPedido)") @Retention(RUNTIME) @Target(METHOD)
        public @interface PodeGerenciarPedidos {}
    }

    public @interface FormasPagamento {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_FORMAS_PAGAMENTO')") @Retention(RUNTIME) @Target(METHOD)
        public @interface PodeEditar {}

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()") @Retention(RUNTIME) @Target(METHOD)
        public @interface PodeConsultar {}
    }

    public @interface Cidades {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_CIDADES')") @Retention(RUNTIME) @Target(METHOD)
        public @interface PodeEditar {}

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()") @Retention(RUNTIME) @Target(METHOD)
        public @interface PodeConsultar {}
    }

    public @interface Estados {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_ESTADOS')") @Retention(RUNTIME) @Target(METHOD)
        public @interface PodeEditar {}

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()") @Retention(RUNTIME) @Target(METHOD)
        public @interface PodeConsultar {}
    }

    public @interface UsuariosGruposPermissoes {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and @algaSecurity.usuarioAutenticadoIgual(returnObject.cliente.id)") @Retention(RUNTIME) @Target(METHOD)
        public @interface PodeAlterarPropriaSenha {}

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES') or @algaSecurity.usuarioAutenticadoIgual(returnObject.cliente.id))") @Retention(RUNTIME) @Target(METHOD)
        public @interface PodeAlterarusuario {}

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES')") @Retention(RUNTIME) @Target(METHOD)
        public @interface PodeEditar {}

        @PreAuthorize("hasAuthority('SCOPE_READ') and hasAuthority('CONSULTAR_USUARIOS_GRUPOS_PERMISSOES')") @Retention(RUNTIME) @Target(METHOD)
        public @interface PodeConsultar {}
    }

    public @interface Estatisticas {

        @PreAuthorize("hasAuthority('SCOPE_READ') and hasAuthority('GERAR_RELATORIOS')") @Retention(RUNTIME) @Target(METHOD)
        public @interface PodeConsultar {}
    }
}
