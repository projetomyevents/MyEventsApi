package br.com.myevents.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Representa as informações básicas de uma conta de usuário.
 */
@Data
public class UserAccountDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * O email de uma conta de usuário.
     */
    private String email;

    /**
     * A senha de uma conta de usuário.
     */
    private String password;

}
