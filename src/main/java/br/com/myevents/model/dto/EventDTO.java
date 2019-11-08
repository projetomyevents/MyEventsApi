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
     * O limite de acompanhantes do evento.
     */
    private byte companionLimit;

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
     * O nome do estado do endereço postal.
     */
    private String stateName;

    /**
     * O nome da cidade do endereço postal.
     */
    private String cityName;

    /**
     * O bairro do endereço postal.
     */
    private String neighborhood;

    /**
     * O logradouro do endereço postal.
     */
    private String street;

    /**
     * A identificação do local do endereço postal.
     */
    private String number;

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
    @Singular private List<byte[]> attachments;

    /**
     * As informações básicas do usuário dono do evento.
     */
    private SimpleUserDTO user;

}