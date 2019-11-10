package br.com.myevents.model.dto;

import br.com.myevents.validation.CEP;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Representa um contrato de um novo evento.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    @Min(message = "O limite de acompanhantes não deve ser menor que 0.", value = 0)
    @Max(message = "O limite de acompanhantes não deve ser maior que 127.", value = 127)
    private byte companionLimit;

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
    @Min(message = "A idade mínima não deve ser menor que 0.", value = 0)
    @Max(message = "A idade mínima não deve ser maior que 127.", value = 127)
    private byte minAge;

    /**
     * O traje recomendado para o evento.
     */
    private String attire;

    /**
     * O CEP do endereço postal.
     */
    @NotBlank(message = "O CEP não deve ficar em branco.")
    @CEP(message = "O CEP não é válido.")
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
     * Uma imagem ilustrativa do evento.
     */
    private byte[] image;

    /**
     * Os anexos do evento.
     */
    @Singular
    private List<byte[]> attachments;

}
