package br.com.myevents.model;

import br.com.myevents.model.enums.PresenceStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

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
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
@Accessors(chain = true)
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
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * O status de presença do convidado.
     */
    @Column(nullable = false)
    @Builder.Default
    private PresenceStatus presenceStatus = PresenceStatus.PENDING;

    /**
     * O limite de acompanhantes do convidado.
     */
    private Integer companionLimit;

    /**
     * O número de acompanhantes confirmados do convidado.
     */
    @Column(nullable = false)
    @Builder.Default
    private Integer confirmedCompanions = 0;

    /**
     * O evento do convidado.
     */
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false, foreignKey = @ForeignKey(name = "guest_event_fkey"))
    @ToString.Exclude
    private Event event;

}
