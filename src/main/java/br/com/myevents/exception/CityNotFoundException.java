package br.com.myevents.exception;

/**
 * Lançada se uma cidade não existe.
 */
public class CityNotFoundException extends RuntimeException {

    public CityNotFoundException(String msg) {
        super(msg);
    }

}
