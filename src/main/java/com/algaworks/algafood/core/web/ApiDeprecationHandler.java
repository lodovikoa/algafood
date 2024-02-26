package com.algaworks.algafood.core.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ApiDeprecationHandler implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getRequestURI().startsWith("/v1/")) {
            response.addHeader("X-AlgaFood-Deprecated", "Essa versão depreciada e deixara de exister a partir de 01/02/2027. Use a versão mais atual da API");
        }

        return true;
    }
}
