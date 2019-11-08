package br.com.myevents.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Representa uma mensagem simples.
 */
@Builder
@Data
@AllArgsConstructor
public class SimpleMessage {

    /**
     * A mensagem.
     */
    private String message;

}
