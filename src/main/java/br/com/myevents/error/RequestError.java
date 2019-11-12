package br.com.myevents.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

/**
 * Representa um erro de requisição HTTP.
 */
@Builder
@Getter
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RequestError implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * O instante em que o erro ocorreu.
     */
    @Builder.Default
    private final Instant timestamp = Instant.now();

    /**
     * O status HTTP.
     */
    private HttpStatus status;

    /**
     * A mensagem sobre o erro.
     */
    private String message;

    /**
     * O caminho para onde a requisição foi enviada.
     */
    private String path;

    /**
     * A coleção de sub-erros.
     */
    @Singular
    private Set<ObjectError> subErrors;

}
