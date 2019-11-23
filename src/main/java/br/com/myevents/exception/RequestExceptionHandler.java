package br.com.myevents.exception;

import br.com.myevents.error.ObjectError;
import br.com.myevents.error.RequestError;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Trata exceções lançadas em métodos anotados com {@code @RequestMapping}.
 */
@ControllerAdvice
public class RequestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(RequestError.builder()
                        .status(HttpStatus.METHOD_NOT_ALLOWED)
                        .message(String.format("Método de requisição %s não é suportado. Métodos suportados: %s.",
                                ex.getMethod(),
                                Optional.ofNullable(ex.getSupportedHttpMethods()).stream()
                                        .map(String::valueOf).collect(Collectors.joining(", "))))
                        .path(request.getContextPath())
                        .build());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(RequestError.builder()
                        .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                        .message(String.format("Tipo de mídia '%s' não suportado. Tipos de mídia suportados: %s.",
                                ex.getContentType(),
                                Optional.ofNullable(ex.getSupportedMediaTypes()).stream()
                                        .map(String::valueOf).collect(Collectors.joining(", "))))
                        .path(request.getContextPath())
                        .build());
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        return ResponseEntity.badRequest()
                .body(RequestError.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .message(String.format("Parâmetro '%s' está ausente.", ex.getParameterName()))
                        .path(request.getContextPath())
                        .build());
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        return ResponseEntity.badRequest()
                .body(RequestError.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .message(String.format("O valor '%s' da propriedade '%s' deve ser de tipo '%s'.",
                                ex.getValue(), ex.getPropertyName(), ex.getRequiredType()))
                        .build());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        return ResponseEntity.badRequest()
                .body(RequestError.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .message("Argumentos inválidos.")
                        .subErrors(ex.getBindingResult().getGlobalErrors().stream()
                                .map(error -> ObjectError.builder()
                                        .message(error.getDefaultMessage())
                                        .object(error.getObjectName())
                                        .build())
                                .collect(Collectors.toSet()))
                        .subErrors(ex.getBindingResult().getFieldErrors().stream()
                                .map(error -> ObjectError.builder()
                                        .message(error.getDefaultMessage())
                                        .object(error.getObjectName())
                                        .field(error.getField())
                                        .rejectedValue(error.getRejectedValue())
                                        .build())
                                .collect(Collectors.toSet()))
                        .build());
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(
            MissingServletRequestPartException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        return ResponseEntity.badRequest()
                .body(RequestError.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .message(String.format("A parte da requisição '%s' está ausente.", ex.getRequestPartName()))
                        .path(request.getContextPath())
                        .build());
    }

    @Override
    protected ResponseEntity<Object> handleBindException(
            BindException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        return ResponseEntity.badRequest()
                .body(RequestError.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .message(ex.getLocalizedMessage())
                        .path(request.getContextPath())
                        .subErrors(ex.getBindingResult().getGlobalErrors()
                                .stream().map(error -> ObjectError.builder()
                                        .message(error.getDefaultMessage())
                                        .object(error.getObjectName())
                                        .build())
                                .collect(Collectors.toSet()))
                        .subErrors(ex.getBindingResult().getFieldErrors().stream()
                                .map(error -> ObjectError.builder()
                                        .message(error.getDefaultMessage())
                                        .object(error.getObjectName())
                                        .field(error.getField())
                                        .rejectedValue(error.getRejectedValue())
                                        .build())
                                .collect(Collectors.toSet()))
                        .build());
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(RequestError.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message(String.format("Nenhum tratamento encontrado em %s para o método %s.",
                                ex.getRequestURL(), ex.getHttpMethod()))
                        .path(request.getContextPath())
                        .build());
    }

    @ExceptionHandler({
            EmailExistsException.class,
            CPFExistsException.class,
            TokenNotFoundException.class,
            TokenExpiredException.class,
            TokenSubjectNotFoundException.class,
            UserNotFoundException.class,
            EventNotFoundException.class,
            CityNotFoundException.class,
            MaxFileSizeException.class
    })
    public ResponseEntity<Object> handleCustomException(RuntimeException ex) {
        return ResponseEntity.badRequest()
                .body(RequestError.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .message(ex.getLocalizedMessage())
                        .build());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAny(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(RequestError.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .message(ex.getLocalizedMessage())
                        .build());
    }

}
