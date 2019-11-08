package br.com.myevents.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Representa um contrato de uma cidade.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
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
     * O identificador do estado em que a cidade reside.
     */
    private Integer stateId;

}
