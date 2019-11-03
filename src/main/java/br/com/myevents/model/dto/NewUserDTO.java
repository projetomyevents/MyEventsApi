package br.com.myevents.model.dto;

import br.com.myevents.validation.FieldsMatch;
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
@FieldsMatch(message = "As senhas são diferentes.", firstField = "password", secondField = "confirmedPassword")
public class NewUserDTO extends UserDTO {

    /**
     * A senha confirmada do usuário.
     */
    private String confirmedPassword;

}
