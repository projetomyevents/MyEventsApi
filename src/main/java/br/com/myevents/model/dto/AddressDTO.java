package br.com.myevents.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Representa um contrato de um endereço postal.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * O CEP do endereço postal.
     */
    // TODO: validar CEP
    @NotBlank(message = "O CEP não deve ficar em branco.")
    private String CEP;

    /**
     * O nome do bairro do endereço postal.
     */
    @NotBlank(message = "O bairro não deve ficar em branco.")
    private String neighborhood;

    /**
     * O nome do logradouro do endereço postal.
     */
    @NotBlank(message = "A rua não deve ficar em branco.")
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
     * O identificador da cidade em que o endereço postal reside.
     */
    @NotNull(message = "O identificador da cidade não deve ser nulo.")
    private Integer cityId;

}
