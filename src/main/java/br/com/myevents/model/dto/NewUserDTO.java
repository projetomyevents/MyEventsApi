package br.com.myevents.model.dto;

import br.com.myevents.validation.FieldsMatch;
import br.com.myevents.validation.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Representa um contrato de um novo usuário.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldsMatch(message = "As senhas são diferentes.", firstField = "password", secondField = "confirmedPassword")
public class NewUserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * O email do usuário.
     */
    @NotBlank(message = "O email não deve ficar em branco.")
    @Email(message = "O email não deve ser inválido.")
    private String email;

    /**
     * A senha do usuário.
     */
    @NotBlank(message = "A senha não deve ficar em branco.")
    @Size(message = "A senha deve ter entre 6 e 127 caractéres.", min = 6, max = 127)
    private String password;

    /**
     * A senha confirmada do usuário.
     */
    private String confirmedPassword;

    /**
     * O nome completo do usuário.
     */
    @NotBlank(message = "O nome não deve ficar em branco.")
    private String name;

    /**
     * O CPF do usuário.
     */
    @NotBlank(message = "O CPF não deve ficar em branco.")
    @CPF(message = "O CPF não é válido.")
    private String CPF;

    /**
     * O número de celular ou telefone do usuário.
     */
    @NotBlank(message = "O número de celular ou telefone não deve ficar em branco.")
    @PhoneNumber(message = "O número de celular ou telefone não é válido.")
    private String phone;

}
