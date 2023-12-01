package com.algaworks.algafood.api.exceptionhandler;

import com.algaworks.algafood.domain.model.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.exception.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {


    public static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Ocorreu um erro interno inesperado no sistema. Tente novamente e se o problema persistir, entre em contato com o administrador do sistema";

    @Autowired
    private MessageSource messageSource;
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var problemType = ProblemType.DADOS_INVALIDOS;
        var detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente";
        var bindingResult = ex.getBindingResult();

        var problemFields = bindingResult
                .getAllErrors()
                .stream().map(objectError -> {
                    var message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

                    String name = objectError.getObjectName();
                    if(objectError instanceof FieldError) {
                        name = ((FieldError) objectError).getField();
                    }

                    return Problem.Object.builder()
                            .name(name)
                            .userMessage(message)
                            .build();
                })
                .collect(Collectors.toList());

        var problem = createProblemBuilder((HttpStatus) status, problemType,detail, detail,problemFields).build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var problemType = ProblemType.ERRO_DE_SISTEMA;
        var detail = MSG_ERRO_GENERICA_USUARIO_FINAL;

        ex.printStackTrace();

        var problem = createProblemBuilder(status, problemType, detail,MSG_ERRO_GENERICA_USUARIO_FINAL, null).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }


    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
        var detail = String.format("O recurso %s, que você tentou acessar é inexistente.", ex.getRequestURL());
        var problem = createProblemBuilder((HttpStatus) status, problemType, detail, detail, null).build();

        return handleExceptionInternal(ex, problem, headers, status, request);

    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        if(ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers, status, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var problemType = ProblemType.PARAMETRO_INVALIDO;
        var detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', que é um tipo inválido. Corrija e informe um valor compatível com o tipo %s", ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());
        var problem = createProblemBuilder((HttpStatus) status, problemType, detail, MSG_ERRO_GENERICA_USUARIO_FINAL, null).build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        var rootCause = ExceptionUtils.getRootCause(ex);

        if(rootCause instanceof InvalidFormatException) {
            return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
        } else if(rootCause instanceof PropertyBindingException) {
            return handlePropertyBinding((PropertyBindingException) rootCause,headers,status,request);
        }

        var problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
        var detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";

        var problem = createProblemBuilder((HttpStatus) status, problemType,detail, MSG_ERRO_GENERICA_USUARIO_FINAL, null).build();

        //return super.handleHttpMessageNotReadable(ex, headers, status, request);
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradaException (EntidadeNaoEncontradaException ex, WebRequest request) {
        var status = HttpStatus.NOT_FOUND;
        var problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
        var detail = ex.getMessage();

        var problem = createProblemBuilder(status, problemType, detail, detail, null).build();

        return this.handleExceptionInternal(ex, problem, new HttpHeaders(),status, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUsoException (EntidadeEmUsoException ex, WebRequest request) {
        var status = HttpStatus.CONFLICT;
        var problemType = ProblemType.ENTIDADE_EM_USO;
        var detail = ex.getMessage();

        var problem = createProblemBuilder(status, problemType, detail, detail, null).build();

        return this.handleExceptionInternal(ex,problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocioException (NegocioException ex, WebRequest request) {
        var status = HttpStatus.BAD_REQUEST;
        var problemType = ProblemType.ERRO_NEGOCIO;
        var detail = ex.getMessage();

        var problem = createProblemBuilder(status, problemType, detail, MSG_ERRO_GENERICA_USUARIO_FINAL, null).build();

        return this.handleExceptionInternal(ex, problem, new HttpHeaders(),status, request);
    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        if(body == null) {
            body = Problem.builder()
                    .title(ex.getMessage())
                    .status(statusCode.value())
                    .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                    .timesTamp(LocalDateTime.now())
                    .build();
        } else if(body instanceof String) {
            body = Problem.builder()
                    .title((String) body)
                    .status(statusCode.value())
                    .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                    .timesTamp(LocalDateTime.now())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail, String userMessage, List<Problem.Object> objectFields ) {
        return Problem.builder()
                .status(status.value())
                .timesTamp(LocalDateTime.now())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail)
                .userMessage(userMessage)
                .objects(objectFields);
    }

    private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var path = joinPath(ex.getPath());

        var problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
        var detail = String.format("A propriedade '%s' recebeu o valor '%s', que é um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                path, ex.getValue(), ex.getTargetType().getSimpleName());
        var problem = createProblemBuilder((HttpStatus) status, problemType, detail, MSG_ERRO_GENERICA_USUARIO_FINAL, null).build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var path = joinPath(ex.getPath());
        var problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
        var detail = String.format("A propriedade '%s' não existe. Corrija ou remova essa propriedade e tente novamente.", path);
        var problem = createProblemBuilder((HttpStatus) status, problemType, detail, MSG_ERRO_GENERICA_USUARIO_FINAL, null).build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }


    private String joinPath(List<JsonMappingException.Reference> references) {
        return references.stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));
    }
}
