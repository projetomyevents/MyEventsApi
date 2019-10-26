package br.com.myevents.model.dto;

import br.com.myevents.annotation.ConfirmPassword;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Representa um contrato de um novo usuário.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@ConfirmPassword
public class NewUserDTO extends UserDTO {

    /**
     * A senha confirmada de um usuário.
     */
    @NotBlank(message = "A senha não deve ficar em branco.")
    @Size(message = "A senha deve ter entre 6 e 127 caractéres.", min = 6, max = 127)
    private String confirmedPassword;

}