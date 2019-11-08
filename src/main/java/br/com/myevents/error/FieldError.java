package br.com.myevents.error;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Representa um erro em um atributo de um {@link Object}.
 */
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FieldError extends ObjectError {

    /**
     * O atributo em que o erro ocorreu.
     */
    private String field;

    /**
     * O valor rejeitado.
     */
    private Object rejectedValue;

}
