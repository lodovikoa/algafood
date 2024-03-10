package com.algaworks.algafood.core.security;

import org.springframework.security.access.prepost.PreAuthorize;

import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

public @interface CheckSecurity {
 // TODO Ainda não estamos usando essa definição de segurança
    public @interface Cozinhas {
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_COZINHAS')") @Retention(RUNTIME) @Target(METHOD)
        public @interface PodeEditar{}

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()") @Retention(RUNTIME) @Target(METHOD)
        public @interface PodeConsultar{}
    }

    public @interface Restaurantes {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_RESTAURANTES')") @Retention(RUNTIME) @Target(METHOD)
        public @interface PodeGerenciarCadastro {}

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_RESTAURANTES') or @webSecurityConfig.gerenciarRestaurante(#restauranteId)") @Retention(RUNTIME) @Target(METHOD)
        public @interface PodeGerenciarFuncionamento {}

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()") @Retention(RUNTIME) @Target(METHOD)
        public @interface PodeConsultar {}
    }
}
