package br.com.myevents.model.dto;

import br.com.myevents.validation.CEP;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Representa um contrato de um novo evento.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewEventDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * O nome do evento.
     */
    @NotBlank(message = "O nome não deve ficar em branco.")
    private String name;

    /**
     * A data de inicio do evento.
     */
    @FutureOrPresent(message = "A data de início deve estar no presente ou futuro.")
    private LocalDate startDate;

    /**
     * O limite de acompanhantes do evento.
     */
    @NotNull(message = "O limite de acompanhantes não deve ser nulo.")
    @PositiveOrZero(message = "O limite de acompanhantes deve ser positivo ou zero.")
    private Integer companionLimit;

    /**
     * A descrição do evento.
     */
    @NotBlank(message = "A descrição não deve ficar em branco.")
    private String description;

    /**
     * O cronograma do evento.
     */
    @NotBlank(message = "O cronograma não deve ficar em branco.")
    private String schedule;

    /**
     * O preço de entrada do evento.
     */
    private String admissionPrice;

    /**
     * A idade mínima permitida no evento.
     */
    @PositiveOrZero(message = "A idade mínima deve ser positiva ou zero.")
    @Max(message = "A idade mínima não deve ser maior que 122.", value = 122)
    private Integer minimumAge;

    /**
     * O traje recomendado para o evento.
     */
    private String attire;

    /**
     * O CEP do endereço postal.
     */
    @NotBlank(message = "O CEP não deve ficar em branco.")
    @CEP(message = "O CEP é inválido.")
    private String CEP;

    /**
     * O identificador da cidade em que o endereço postal reside.
     */
    @NotNull(message = "O identificador da cidade não deve ser nulo.")
    private Integer cityId;

    /**
     * O bairro do endereço postal.
     */
    @NotBlank(message = "O bairro não deve ficar em branco.")
    private String neighborhood;

    /**
     * O logradouro do endereço postal.
     */
    @NotBlank(message = "A rua não deve ficar em branco.")
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
     * A imagem ilustrativa do evento.
     */
    private byte[] image;

    /**
     * Os anexos do evento.
     */
    @Singular
    private List<byte[]> attachments;

}
