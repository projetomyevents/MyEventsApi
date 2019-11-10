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
import java.time.temporal.ChronoUnit;
import java.util.UUID;

/**
 * Representa um token de ativação de uma conta de usuário.
 */
@Entity
@Table(name = "atoken")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class ActivationToken implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * A chave primária do token de ativação.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * O valor do token de ativação. (Uma sequência de caractéres gerada aleatóriamente.)
     */
    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    @Builder.Default
    private final String value = UUID.randomUUID().toString();

    /**
     * A data de expiração do token de ativação. (Cada token dura 72 horas.)
     */
    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    @Builder.Default
    private final Instant expiration = Instant.now().plus(3, ChronoUnit.DAYS);

    /**
     * O usuário vinculado ao token de ativação.
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "atoken_user_fkey"))
    private User user;

}
