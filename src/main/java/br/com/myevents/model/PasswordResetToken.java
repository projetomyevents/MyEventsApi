package br.com.myevents.model;

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
 * Representa um token de redefinição de senha de um usuário.
 */
@Entity
@Table(name = "rtoken")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class PasswordResetToken implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * A chave primária do token de redefinição de senha.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * O valor do token de redefinição de senha. (Uma sequência de caractéres gerada aleatóriamente.)
     */
    @Column(nullable = false)
    private final String token = UUID.randomUUID().toString();

    /**
     * O instante de expiração do token de redefinição de senha. (Cada token dura 24 horas.)
     */
    @Column(nullable = false)
    private final Instant expiration = Instant.now().plus(1, ChronoUnit.DAYS);

    /**
     * O usuário vinculado ao token de redefinição de senha.
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "rtoken_user_fkey"))
    private User user;

    public PasswordResetToken(User user) {
        this.user = user;
    }

}
