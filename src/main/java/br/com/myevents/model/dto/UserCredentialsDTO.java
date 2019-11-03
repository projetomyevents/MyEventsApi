package br.com.myevents.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Representa as credenciais de um usuário.
 */
@Data
public class UserCredentialsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * O email do usuário.
     */
    private String email;

    /**
     * A senha do usuário.
     */
    private String password;

}
