package br.com.myevents.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Representa um token de status de presença de um convidado.
 */
@Entity
@Table(name = "sptoken")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class StatusPresenceToken implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * A chave primária do token de status de presença.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * O valor do token de status de presença. (Uma sequência de caractéres gerada aleatóriamente.)
     */
    @Column(nullable = false)
    private final String token = UUID.randomUUID().toString();

    /**
     * A data de expiração do token de status de presença.
     */
    @Column(nullable = false)
    private LocalDate expiration;

    /**
     * O convidado vinculado ao token de status de presença.
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "guest_id", nullable = false, foreignKey = @ForeignKey(name = "sptoken_guest_fkey"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Guest guest;

    public StatusPresenceToken(LocalDate expiration, Guest guest) {
        this.expiration = expiration;
        this.guest = guest;
    }

}
