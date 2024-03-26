package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.AuthenticationDTO;
import com.algaworks.algafood.api.v1.dto.model.LoginResponseDTO;
import com.algaworks.algafood.core.security.TokenService;
import com.algaworks.algafood.domain.model.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login (@RequestBody @Valid AuthenticationDTO usuario) {
        var usernamePassord = new UsernamePasswordAuthenticationToken(usuario.email(), usuario.senha());
        var auth = this.authenticationManager.authenticate(usernamePassord);
        var token = tokenService.generatedToken((Usuario) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
