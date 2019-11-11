package br.com.myevents.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Representa um contrato com as informações básicas de um convidado.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleGuestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * O nome do convidado.
     */
    private String name;

    /**
     * O número de acompanhantes confirmados do convidado.
     */
    private byte confirmedCompanions;

    /**
     * O identificador do status de presença do convidado.
     */
    private int presenceStatus;

}
