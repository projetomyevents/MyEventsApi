package br.com.myevents.model.dto;

import br.com.myevents.model.enums.PresenceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

/**
 * Representa um contrato de um convidado.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GuestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * A chave primária do evento.
     */
    private Long id;

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
     * O status de presença do convidado.
     */
    private PresenceStatus presenceStatus;

    /**
     * O limite de acompanhantes do convidado.
     */
    @NotNull(message = "O limite de acompanhantes não deve ser nulo.")
    @PositiveOrZero(message = "O limite de acompanhantes deve ser positivo ou zero.")
    private Integer companionLimit;

    /**
     * O número de acompanhantes confirmados do convidado.
     */
    @PositiveOrZero(message = "O limite de acompanhantes deve ser positivo ou zero.")
    private Integer confirmedCompanions;

}
