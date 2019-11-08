package br.com.myevents.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Representa as credenciais de uma conta de usuário.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountCredentialsDTO implements Serializable {

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
