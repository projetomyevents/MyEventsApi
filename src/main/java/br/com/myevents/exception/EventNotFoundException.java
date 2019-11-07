package br.com.myevents.exception;

/**
 * Levantada se um evento não existe.
 */
public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException(String msg) {
        super(msg);
    }

}
