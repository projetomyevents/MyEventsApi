package br.com.myevents.model;

import br.com.myevents.model.enums.PresenceStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
 * Representa um convidado.
 */
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class Guest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * A chave primária do evento.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * O nome do convidado.
     */
    @Column(nullable = false)
    private String name;

    /**
     * O email do convidado.
     */
    @Column(nullable = false)
    private String email;

    /**
     * O número de acompanhantes confirmados do convidado.
     */
    @Column(nullable = false)
    private byte confirmedCompanions;

    /**
     * O status de presença do convidado.
     */
    @Column(nullable = false)
    private PresenceStatus presenceStatus;

    /**
     * O evento do convidado.
     */
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false, foreignKey = @ForeignKey(name = "event_guest_fkey"))
    private Event event;

}
