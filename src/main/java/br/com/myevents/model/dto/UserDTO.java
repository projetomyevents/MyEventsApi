package br.com.myevents.model.dto;

import br.com.myevents.validation.PhoneNumberBR;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Representa um contrato de um usuário.
 */
@SuperBuilder
@Data
@NoArgsConstructor
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * O email de um usuário.
     */
    @NotBlank(message = "O email não deve ficar em branco.")
    @Email(message = "O email não deve ser inválido.")
    private String email;

    /**
     * A senha de um usuário.
     */
    @NotBlank(message = "A senha não deve ficar em branco.")
    @Size(message = "A senha deve ter entre 6 e 127 caractéres.", min = 6, max = 127)
    private String password;

    /**
     * O nome completo de um usuário.
     */
    @NotBlank(message = "O nome não deve ficar em branco.")
    private String name;

    /**
     * O CPF de um usuário.
     */
    @NotBlank(message = "O CPF não deve ficar em branco.")
    @Size(message = "O CPF deve ter exatamente 11 dígitos.", min = 11, max = 11)
    @CPF(message = "O CPF não é válido.")
    private String CPF;

    /**
     * O número de celular ou telefone de um usuário.
     */
    @NotBlank(message = "O número de celular ou telefone não deve ficar em branco.")
    @Size(message = "O número de celular ou telefone deve ter entre 10 e 11 dígitos.", min = 10, max = 11)
    @PhoneNumberBR(message = "O número de celular ou telefone não é válido.")
    private String phone;

}
