package br.com.myevents.model.dto;

import br.com.myevents.model.enums.PresenceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

/**
 * Representa um contrato de um convidado modificado pelo próprio convidado.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GuestUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * O status de presença do convidado.
     */
    private PresenceStatus presenceStatus;

    /**
     * O número de acompanhantes confirmados do convidado.
     */
    @PositiveOrZero(message = "O limite de acompanhantes deve ser positivo ou zero.")
    private Integer confirmedCompanions;

}
