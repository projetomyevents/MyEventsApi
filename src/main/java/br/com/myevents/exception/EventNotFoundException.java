package br.com.myevents.exception;

/**
 * Lançada se um evento não existe.
 */
public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException(String msg) {
        super(msg);
    }

}
