package br.com.myevents.utils;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

/**
 * Representa uma mensagem. (usado para retornar apenas uma mensagem simples pela {@link ResponseEntity})
 */
@Builder
@Data
public class SimpleMessage {

    /**
     * A mensagem.
     */
    private String message;

}
