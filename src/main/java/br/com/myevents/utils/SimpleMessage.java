package br.com.myevents.utils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Representa uma mensagem simples.
 */
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public final class SimpleMessage {

    /**
     * A mensagem.
     */
    private final String message;

}
