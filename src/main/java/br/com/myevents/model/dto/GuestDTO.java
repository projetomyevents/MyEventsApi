package br.com.myevents.model.dto;

import br.com.myevents.model.enums.PresenceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
     * O nome do convidado.
     */
    private String name;

    /**
     * O email do convidado.
     */
    private String email;

    /**
     * O status de presença do convidado.
     */
    private PresenceStatus presenceStatus;

    /**
     * O limite de acompanhantes do convidado.
     */
    private Integer companionLimit;

    /**
     * O número de acompanhantes confirmados do convidado.
     */
    private Integer confirmedCompanions;

}
