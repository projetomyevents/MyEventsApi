package br.com.myevents.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Representa um contrato de um estado.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * A chave primária do estado.
     */
    private Integer id;

    /**
     * O nome do estado.
     */
    private String name;

}
