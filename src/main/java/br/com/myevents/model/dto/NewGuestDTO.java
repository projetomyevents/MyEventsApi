package br.com.myevents.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Representa um contrato de um novo convidado.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewGuestDTO implements Serializable {

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
    @Email(message = "O email não deve ser inválido.")
    private String email;

    /**
     * O limite de acompanhantes do convidado.
     */
    @Min(message = "O limite de acompanhantes não deve ser menor que 0.", value = 0)
    @Max(message = "O limite de acompanhantes não deve ser maior que 127.", value = 127)
    private byte companionLimit;

    /**
     * O identificador do evento do convidado.
     */
    @NotNull(message = "O identificador do evento não deve ser nulo.")
    private Long eventId;

}
