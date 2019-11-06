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
     * A chave primária do endereço postal.
     */
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    /**
     * O CEP do endereço postal.
     */
    @Column(nullable = false, length = 8)
    private String CEP;

    /**
     * A cidade em que o endereço postal reside.
     */
    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false, foreignKey = @ForeignKey(name = "address_city_fkey"))
    private City city;

    /**
     * O nome do bairro do endereço postal.
     */
    @Column(nullable = false)
    private String neighborhood;

    /**
     * O nome do logradouro do endereço postal.
     */
    @Column(nullable = false)
    private String street;

    /**
     * O número/identificador da residência do endereço postal. (Pode conter letras.)
     */
    private String number;

    /**
     * As informações adicionais do endereço postal.
     */
    private String complement;

}
