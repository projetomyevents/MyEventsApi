package br.com.myevents.error;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Representa um erro de violação de validação de um objeto.
 */
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ObjectError extends SimpleError {

    /**
     * O objeto em que o erro ocorreu.
     */
    private String object;

}
