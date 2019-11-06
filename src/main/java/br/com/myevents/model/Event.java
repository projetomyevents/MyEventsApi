package br.com.myevents.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

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
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * A chave primária do evento.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * O nome do evento.
     */
    @Column(nullable = false)
    private String name;

    /**
     * A data de inicio do evento.
     */
    @Column(nullable = false)
    private LocalDate startDate;

    /**
     * O limite de acompanhantes do evento.
     */
    private byte companionLimit;

    /**
     * A descrição do evento.
     */
    @Column(nullable = false)
    private String description;

    /**
     * O cronograma do evento.
     */
    @Column(nullable = false)
    private String schedule;

    /**
     * O preço de entrada do evento.
     */
    private String admissionPrice;

    /**
     * A idade mínima permitida no evento.
     */
    private byte minAge;

    /**
     * O traje recomendado para o evento.
     */
    private String attire;

    /**
     * Endereço postal em que ocorrerá o evento.
     */
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "address_id", unique = true, nullable = false,
            foreignKey = @ForeignKey(name = "event_address_fkey"))
    private Address address;

    /**
     * Uma imagem ilustrativa do evento.
     */
    private byte[] image;

    /**
     * Os anexos do evento.
     */
    private byte[] attachments;

    /**
     * O dono do evento.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "user_event_fkey"))
    private User user;

    /**
     * Os convidados de um evento.
     */
    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "event", orphanRemoval = true)
    @Singular private Set<Guest> guests;

}
