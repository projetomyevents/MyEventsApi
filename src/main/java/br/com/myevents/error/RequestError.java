package br.com.myevents.error;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.time.Instant;

/**
 * Representa um erro de requisição HTTP.
 */
@SuperBuilder
@Data
public class RequestError {

    /**
     * O código de status HTTP.
     */
    private HttpStatus status;

    /**
     * O instante em que o erro ocorreu.
     */
    @Setter(AccessLevel.NONE) @Builder.Default private final Instant timestamp = Instant.now();

    /**
     * A mensagem sobre o erro.
     */
    private String message;

    /**
     * A mensagem de depuração sobre o erro.
     */
    private String debugMessage;

    /**
     * O nome da exceção.
     */
    private String exception;

}
