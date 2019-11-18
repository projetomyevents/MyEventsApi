package br.com.myevents.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Representa um contrato com as informações básicas de um evento.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SimpleEventDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * A chave primária do evento.
     */
    private Long id;

    /**
     * O nome do evento.
     */
    private String name;

    /**
     * A data de inicio do evento.
     */
    private LocalDate startDate;

    /**
     * A descrição do evento.
     */
    private String description;

    /**
     * Uma imagem ilustrativa do evento.
     */
    private FileDTO image;

}
