package br.com.myevents.domain;


import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Positive;

import lombok.*;

/**
 * Representa um evento.
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class Evento implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * A chave primária de um usuário.
     */
    @Positive
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE) private long id;

    // TODO: arrumar essa classe
    private String nomeCompleto;
    private LocalDate data;
    private String precoEntrada;
    private byte limiteAcompanhantes;
    private byte idadeMinima;
    private String traje;
    private String descricao;
    private byte[] imagem;
    private String cronograma;
    private byte[] anexos;

}
