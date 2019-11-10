package br.com.myevents.error;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Set;

/**
 * Representa um erro de requisição HTTP com uma coleção de sub-erros.
 */
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RequestMultipleErrors extends RequestError {

    /**
     * A coleção de sub-erros.
     */
    @Singular
    private Set<ObjectError> errors;

}
