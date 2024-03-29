package br.com.myevents.model.dto;

import br.com.myevents.validation.FieldsMatch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Representa um contrato de uma nova senha para um usuário.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldsMatch(message = "As senhas são diferentes.", firstField = "password", secondField = "confirmedPassword")
public class NewPasswordDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * A nova senha.
     */
    @NotBlank(message = "A senha não deve ficar em branco.")
    @Size(message = "A senha deve ter entre 6 e 255 caractéres.", min = 6, max = 255)
    private String password;

    /**
     * A nova senha confirmada.
     */
    private String confirmedPassword;

}
