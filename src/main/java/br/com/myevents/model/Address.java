package br.com.myevents.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Representa um endereço postal.
 */
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * A chave primária de um endereço postal.
     */
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    /**
     * O nome do logradouro de um endereço postal.
     */
    @Column(nullable = false)
    @NotBlank private String street;

    /**
     * O número/identificador da residência de um endereço postal. (Pode conter letras.)
     */
    private String number;

    /**
     * O número/identificador do apartamento/quarto de um endereço postal. (Pode conter letras.)
     */
    private String numberAptRoom;

    /**
     * As informações adicionais de um endereço postal.
     */
    private String complement;

    /**
     * O nome do bairro de um endereço postal.
     */
    private String neighborhood;

    /**
     * O código postal de um endereço postal.
     */
    @Column(nullable = false, length = 12)
    @NotBlank @Size(min = 2, max = 12) private String postalCode;

    /**
     * A cidade em que um endereço postal reside.
     */
    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false, foreignKey = @ForeignKey(name = "address_city_fkey"))
    @NotNull private City city;

    // O estado está implícito em uma cidade.

}
