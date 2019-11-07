package br.com.myevents.exception;

/**
 * Levantada se uma cidade não existe.
 */
public class CityNotFoundException extends RuntimeException {

    public CityNotFoundException(String msg) {
        super(msg);
    }

}
