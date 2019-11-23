package br.com.myevents.exception;

/**
 * Lançada se um arquivo exceder um número máximo de bytes.
 */
public class MaxFileSizeException extends RuntimeException {

    public MaxFileSizeException(String message) {
        super(message);
    }

}
