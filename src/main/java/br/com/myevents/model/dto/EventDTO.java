package br.com.myevents.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

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
     * O cep do endereço postal.
     */
    private String cep;

    /**
     * O nome do estado do endereço postal.
     */
    private String stateName;

    /**
     * O nome da cidade do endereço postal.
     */
    private String cityName;

    /**
     * O nome do bairro do endereço postal.
     */
    private String neighborhood;

    /**
     * O nome do logradouro do endereço postal.
     */
    private String street;

    /**
     * O número/identificador da residência do endereço postal. (Pode conter letras.)
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
    private byte[] attachments;

    /**
     * Informações básicas do usuário dono do evento.
     */
    private SimpleUserDTO user;

}
