package br.com.myevents.error;

import lombok.Builder;
import lombok.Data;
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
    @Builder.Default private final Instant timestamp = Instant.now();

    /**
     * A mensagem sobre o erro.
     */
    private String message;

    /**
     * A mensagem de depuração sobre o erro.
     */
    private String debugMessage;

}
