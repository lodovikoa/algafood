//package com.algaworks.algafood.core.security;
//
//import com.algaworks.algafood.domain.model.Usuario;
//import com.algaworks.algafood.domain.service.UsuarioService;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.Base64;
//
//@Component
//@RequiredArgsConstructor
//public class CustomBasicAuthenticationFilter extends OncePerRequestFilter {
//
//    private static final String AUTHORIZATION = "Authorization";
//    private static final String BASIC = "Basic ";
//    private final UsuarioService usuarioService;
//    private final PasswordEncoder passwordEncoder;
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        if(isBasicAuthentication(request)) {
//            String[] credentials = decodeBase64(getHeader(request).replace(BASIC, "")).split(":");
//
//            String username = credentials[0];
//            String password = credentials[1];
//
//            Usuario usuario = usuarioService.findByEmailFetchGrupos(username);
//
//            if(usuario == null) {
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                response.getWriter().write("Usu'ario não encontrado!");
//                return;
//            }
//
//            boolean valid = checkPassword(password, usuario.getSenha());
//
//            if(!valid) {
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                response.getWriter().write("Senha não confere!");
//            }
//            setAuthentication(usuario);
//        }
//
//        filterChain.doFilter(request, response);
//
//    }
//
//    private void setAuthentication(Usuario usuario) {
//        Authentication authentication = createAuthenticationToken(usuario);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//    }
//
//    private boolean checkPassword(String passwordUser, String senhaBanco) {
//        return passwordEncoder.matches(passwordUser, senhaBanco);
//    }
//
//    private Authentication createAuthenticationToken(Usuario usuario) {
//        UserPrincipal userPrincipal = UserPrincipal.create(usuario);
//        return new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
//    }
//
//    private String decodeBase64(String base64) {
//        byte[] decoderBytes = Base64.getDecoder().decode(base64);
//        return new String(decoderBytes);
//    }
//
//    private boolean isBasicAuthentication(HttpServletRequest request) {
//        String header = getHeader(request);
//        return header != null && header.startsWith(BASIC);
//    }
//
//    private String getHeader(HttpServletRequest request) {
//        return request.getHeader(AUTHORIZATION);
//    }
//}
