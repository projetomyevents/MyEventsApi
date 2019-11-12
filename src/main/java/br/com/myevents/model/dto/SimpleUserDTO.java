package br.com.myevents.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Representa um contrato com as informações básicas de um usuário.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SimpleUserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * O email do usuário.
     */
    private String email;

    /**
     * O nome completo do usuário.
     */
    private String name;

    /**
     * O número de celular ou telefone do usuário.
     */
    private String phone;

}
