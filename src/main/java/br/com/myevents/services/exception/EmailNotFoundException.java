package br.com.myevents.services.exception;

/**
 * Essa exceção indica que o email requisitado não foi achado.
 */
public class EmailNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EmailNotFoundException(String msg) {
        super(msg);
    }

}
