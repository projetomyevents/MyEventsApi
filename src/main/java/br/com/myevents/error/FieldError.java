package br.com.myevents.error;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Representa um erro de violação de validação de um atributo em um objeto.
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
     * O valor rejeitado pela validação.
     */
    private Object rejectedValue;

}