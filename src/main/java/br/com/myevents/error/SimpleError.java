package br.com.myevents.error;

import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * Representa um erro simples.
 */
@SuperBuilder
@Data
public class SimpleError {

    /**
     * A mensagem sobre o erro.
     */
    private String message;

}
