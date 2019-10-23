package br.com.myevents.model.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Representa um contrato de um usuário.
 */
@Data
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * O email de um usuário.
     */
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
    private String fullName;

    /**
     * O CPF de um usuário.
     */
    // TODO: adicionar validação para CPFs
    @NotBlank(message = "O cpf não deve ficar em branco.")
    @Size(message = "O cpf deve ter exatamente 11 dígitos.", min = 11, max = 11)
    private String cpf;

    /**
     * O número de celular de um usuário.
     */
    // TODO: adicionar validação para números de celulares
    @NotBlank(message = "O número de celular não deve ficar em branco.")
    @Size(message = "O número de celular deve ter entre 10 e 11 dígitos.", min = 10, max = 11)
    private String phoneNumber;

}
