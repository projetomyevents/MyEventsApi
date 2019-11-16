package br.com.myevents.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

/**
 * Representa um contrato de um convidado criado/modificado pelo dono do evento.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GuestEditDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * O nome do convidado.
     */
    @NotBlank(message = "O nome não deve ficar em branco.")
    private String name;

    /**
     * O email do convidado.
     */
    @NotBlank(message = "O email não deve ficar em branco.")
    @Email(message = "O email deve ser válido.")
    private String email;

    /**
     * O limite de acompanhantes do convidado.
     */
    @PositiveOrZero(message = "O limite de acompanhantes deve ser positivo ou zero.")
    private Integer companionLimit;

}
