package br.com.myevents.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Representa um contrato de um evento.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO implements Serializable {

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
     * O cronograma do evento.
     */
    private String schedule;

    /**
     * O preço de entrada do evento.
     */
    private String admissionPrice;

    /**
     * A idade mínima permitida no evento.
     */
    private byte minAge;

    /**
     * O traje recomendado para o evento.
     */
    private String attire;

    /**
     * O CEP do endereço postal.
     */
    private String CEP;

    /**
     * O estado e cidade do endereço postal.
     */
    private String stateCity;

    /**
     * O bairro, rua e número do endereço postal.
     */
    private String local;

    /**
     * As informações adicionais do endereço postal.
     */
    private String complement;

    /**
     * Uma imagem ilustrativa do evento.
     */
    private byte[] image;

    /**
     * Os anexos do evento.
     */
    @Singular
    private List<byte[]> attachments;

    /**
     * As informações básicas do usuário dono do evento.
     */
    private SimpleUserDTO user;

}
