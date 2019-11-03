package br.com.myevents.error;

import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * Representa um erro de um objeto.
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
