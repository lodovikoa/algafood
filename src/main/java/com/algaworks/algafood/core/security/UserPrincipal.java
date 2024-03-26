package com.algaworks.algafood.core.security;

import com.algaworks.algafood.domain.model.Usuario;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class UserPrincipal {

    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    private UserPrincipal(Usuario usuario) {
        this.username = usuario.getEmail();
        this.password = usuario.getSenha();

        this.authorities = usuario.getGrupos().stream().map(role -> {
            return  new SimpleGrantedAuthority("ROLE_".concat(role.getNome()));
        }).collect(Collectors.toList());
    }

    public static UserPrincipal create(Usuario usuario) {
        return new UserPrincipal(usuario);
    }
}
