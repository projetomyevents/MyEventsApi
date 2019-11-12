package br.com.myevents.security;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Representa as credenciais de uma conta de usuário, usada por {@link com.fasterxml.jackson.databind.ObjectMapper}.
 */
@Getter
@EqualsAndHashCode
@ToString
public class UserAccountCredentials implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * O email da conta do usuário.
     */
    private String email;

    /**
     * A senha da conta do usuário.
     */
    private String password;

}
