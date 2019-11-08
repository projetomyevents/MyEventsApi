package br.com.myevents.error;

import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * Representa um erro em um {@link Object}.
 */
@SuperBuilder
@Data
public class ObjectError {

    /**
     * A mensagem sobre o erro.
     */
    private String message;

    /**
     * O objeto em que o erro ocorreu.
     */
    private String object;

}
