package br.com.myevents.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * Representa um contrato de um arquivo.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FileDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * O nome do arquivo.
     */
    @NotBlank(message = "O nome não deve ficar em branco.")
    private String name;

    /**
     * O tipo do arquivo.
     */
    @NotBlank(message = "O tipo não deve ficar em branco.")
    private String type;

    /**
     * O conteúdo do arquivo.
     */
    @NotEmpty(message = "O conteúdo não deve ficar em branco.")
    private byte[] content;

}
