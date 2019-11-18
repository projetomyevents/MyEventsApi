package br.com.myevents.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Representa um arquivo.
 */
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class File implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * A chave primária do arquivo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * O nome do arquivo.
     */
    @Column(nullable = false)
    private String name;

    /**
     * O tipo do arquivo.
     */
    @Column(nullable = false)
    private String type;

    /**
     * O conteúdo do arquivo.
     */
    @Column(nullable = false)
    private byte[] content;

}
