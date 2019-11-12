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
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * Representa um token de status de presença de um convidado.
 */
@Entity
@Table(name = "sptoken")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
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
    @Setter(AccessLevel.NONE)
    @Builder.Default
    private final String value = UUID.randomUUID().toString();

    /**
     * A data de expiração do token de status de presença.
     */
    @Column(nullable = false)
    private Instant expiration;

    /**
     * O convidado vinculado ao token de status de presença.
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "guest_id", nullable = false, foreignKey = @ForeignKey(name = "sptoken_guest_fkey"))
    private Guest guest;

}
