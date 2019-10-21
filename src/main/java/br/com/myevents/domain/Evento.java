package br.com.myevents.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

/**
 * Representa um evento.
 */
@Entity
@Builder
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
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    /**
     * O nome do evento.
     */
    @Column(nullable = false)
    @NotBlank private String nome;

    /**
     * O data de inicio do evento.
     */
    @Column(nullable = false)
    @NotNull @Future private LocalDate data;

    /**
     * O preço de entrada do evento.
     */
    private String preco;

    /**
     * O limite de acompanhantes padrão de cada convidado.
     */
    @Column(nullable = false)
    @NotNull @PositiveOrZero private byte limiteAcompanhantes;

    /**
     * A idade mínima permitida no evento.
     */
    @PositiveOrZero private byte idadeMinima;

    /**
     * O traje recomendado para o evento.
     */
    private String traje;

    /**
     * A descrição do evento.
     */
    private String descricao;

    /**
     * A imagem ilustrativa do evento.
     */
    private byte[] imagem;

    /**
     * O cronograma do evento.
     */
    @Column(nullable = false)
    @NotBlank private String cronograma;

    /**
     * Os arquivos em anexo do evento.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "ANEXOS", foreignKey = @ForeignKey(name = "evento_anexo_fkey"))
    @Column(name = "arquivo")
    private List<@NotNull byte[]> anexos;

}
