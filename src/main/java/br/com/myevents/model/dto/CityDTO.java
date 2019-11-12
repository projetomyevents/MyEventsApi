package br.com.myevents.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Representa um contrato de uma cidade.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CityDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * A chave primária da cidade.
     */
    private Integer id;

    /**
     * O nome da cidade.
     */
    private String name;

    /**
     * O identificador do estado da cidade.
     */
    private Integer stateId;

}
