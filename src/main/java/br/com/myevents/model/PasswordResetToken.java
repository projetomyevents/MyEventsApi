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
 * Representa um token de redefinição da senha de um usuário.
 */
@Entity
@Table(name = "rtoken")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class PasswordResetToken implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * A chave primária do token de redefinição da senha.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * O valor do token de redefinição da senha.
     */
    @Column(nullable = false)
    @Setter(AccessLevel.NONE) @Builder.Default private final String token = UUID.randomUUID().toString();

    /**
     * O usuário vinculado ao token de redefinição da senha.
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id", foreignKey = @ForeignKey(name = "rtoken_user_fkey"))
    private User user;

    /**
     * A data de expiração do token de redefinição da senha.
     */
    @Setter(AccessLevel.NONE) @Builder.Default private final Instant expiration =
            Instant.now().plus(1, ChronoUnit.DAYS);

}
