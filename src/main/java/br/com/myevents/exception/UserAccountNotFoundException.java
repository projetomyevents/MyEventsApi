package br.com.myevents.exception;

/**
 * Levantada se uma conta de usuário não existe.
 */
public class UserAccountNotFoundException extends RuntimeException {

    public UserAccountNotFoundException() {
        super("Não existe nenhuma conta de usuário com a combinação dos dados informados.");
    }

    public UserAccountNotFoundException(String message) {
        super(message);
    }

}
