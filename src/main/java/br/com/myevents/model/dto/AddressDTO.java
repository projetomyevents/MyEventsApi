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
    @NotBlank private String CEP;

    /**
     * O nome do bairro do endereço postal.
     */
    @NotBlank private String neighborhood;

    /**
     * O nome do logradouro do endereço postal.
     */
    @NotBlank private String street;

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
    @NotNull private Integer cityId;

}
