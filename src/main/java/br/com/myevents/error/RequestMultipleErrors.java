package br.com.myevents.error;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Set;

/**
 * Representa múltiplos erros de requisição HTTP.
 */
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RequestMultipleErrors extends RequestError {

    /**
     * O conjunto de erros.
     */
    @Singular private Set<SimpleError> errors;

}
