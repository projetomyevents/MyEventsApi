package br.com.myevents.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Representa um erro em um {@link Object}.
 */
@Builder
@Getter
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ObjectError implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * A mensagem do erro.
     */
    private String message;

    /**
     * O objeto em que o erro ocorreu.
     */
    private String object;

    /**
     * O atributo em que o erro ocorreu.
     */
    private String field;

    /**
     * O valor rejeitado.
     */
    private Object rejectedValue;

}
