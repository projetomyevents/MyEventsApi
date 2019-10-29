package br.com.myevents.model.dto;

import br.com.myevents.validation.ConfirmPassword;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Representa um contrato de um novo usuário.
 */
@SuperBuilder
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@ConfirmPassword
public class NewUserDTO extends UserDTO {

    /**
     * A senha confirmada de um usuário.
     */
    private String confirmedPassword;

}
