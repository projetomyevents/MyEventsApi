package br.com.myevents.exception;

import br.com.myevents.error.RequestError;
import br.com.myevents.error.FieldError;
import br.com.myevents.error.ObjectError;
import br.com.myevents.error.RequestMultipleErrors;
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

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A classe responsável pela formatação de exceções e erros em respostas HTTP.
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
        RequestError requestError = RequestError.builder()
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .message(String.format("Método de requisição '%s' não é suportado. Métodos suportados são: %s.",
                        ex.getMethod(),
                        Objects.requireNonNull(ex.getSupportedHttpMethods())
                                .stream().map(String::valueOf).collect(Collectors.joining(", "))))
                .debugMessage(ex.getLocalizedMessage())
                .build();
        return new ResponseEntity<>(requestError, requestError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        RequestError requestError = RequestError.builder()
                .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .message(String.format("Tipo de mídia '%s' não suportado. Tipos de mídia suportados são: %s.",
                        ex.getContentType(),
                        Objects.requireNonNull(ex.getSupportedMediaTypes())
                                .stream().map(String::valueOf).collect(Collectors.joining(", "))))
                .debugMessage(ex.getLocalizedMessage())
                .build();
        return new ResponseEntity<>(requestError, requestError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        RequestError requestError = RequestError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(String.format("Parâmetro '%s' está ausente.", ex.getParameterName()))
                .debugMessage(ex.getLocalizedMessage())
                .build();
        return new ResponseEntity<>(requestError, requestError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        RequestError requestError = RequestError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(String.format("O valor '%s' da propriedade '%s' deve ser de tipo '%s'.",
                        ex.getValue(), ex.getPropertyName(), ex.getRequiredType()))
                .debugMessage(ex.getLocalizedMessage())
                .build();
        return new ResponseEntity<>(requestError, requestError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        RequestError requestError = RequestMultipleErrors.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Argumentos inválidos.")
                .debugMessage(ex.getLocalizedMessage())
                .errors(ex.getBindingResult().getFieldErrors()
                        .stream().map(fieldError -> FieldError.builder()
                                .message(fieldError.getDefaultMessage())
                                .object(fieldError.getObjectName())
                                .field(fieldError.getField())
                                .rejectedValue(fieldError.getRejectedValue())
                                .build())
                        .collect(Collectors.toSet()))
                .errors(ex.getBindingResult().getGlobalErrors()
                        .stream().map(globalError -> ObjectError.builder()
                                .message(globalError.getDefaultMessage())
                                .object(globalError.getObjectName())
                                .build())
                        .collect(Collectors.toSet()))
                .build();
        return new ResponseEntity<>(requestError, requestError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(
            MissingServletRequestPartException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        RequestError requestError = RequestError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(String.format("A parte da requisição '%s' está ausente.", ex.getRequestPartName()))
                .debugMessage(ex.getLocalizedMessage())
                .build();
        return new ResponseEntity<>(requestError, requestError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleBindException(
            BindException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        RequestError requestError = RequestMultipleErrors.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Sei lá que porra é essa.")
                .debugMessage(ex.getLocalizedMessage())
                .errors(ex.getBindingResult().getFieldErrors()
                        .stream().map(fieldError -> FieldError.builder()
                                .message(fieldError.getDefaultMessage())
                                .object(fieldError.getObjectName())
                                .field(fieldError.getField())
                                .rejectedValue(fieldError.getRejectedValue())
                                .build())
                        .collect(Collectors.toSet()))
                .errors(ex.getBindingResult().getGlobalErrors()
                        .stream().map(globalError -> ObjectError.builder()
                                .message(globalError.getDefaultMessage())
                                .object(globalError.getObjectName())
                                .build())
                        .collect(Collectors.toSet()))
                .build();
        return new ResponseEntity<>(requestError, requestError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        RequestError requestError = RequestError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(String.format("Nenhum tratamento encontrado para %s %s.",
                        ex.getHttpMethod(), ex.getRequestURL()))
                .debugMessage(ex.getLocalizedMessage())
                .build();
        return new ResponseEntity<>(requestError, requestError.getStatus());
    }

    @ExceptionHandler({ EmailExistsException.class, CPFExistsException.class })
    public ResponseEntity<Object> handleExistsException(
            EmailExistsException ex
    ) {
        RequestError apiError = RequestError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(ex.getLocalizedMessage())
                .build();
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(Exception ex) {
        RequestError requestError = RequestError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("Um erro interno ocorreu.")
                .debugMessage(ex.getLocalizedMessage())
                .build();
        return new ResponseEntity<>(requestError, requestError.getStatus());
    }

}