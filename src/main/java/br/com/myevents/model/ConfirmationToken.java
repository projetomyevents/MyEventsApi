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
 * Representa um token de confirmação de uma conta de usuário.
 */
@Entity
@Table(name = "ctoken")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class ConfirmationToken implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * A chave primária de um token de confirmação.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * O token.
     */
    @Column(nullable = false)
    @Builder.Default private String token = UUID.randomUUID().toString();

    /**
     * O usuário vinculado ao token.
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id", foreignKey = @ForeignKey(name = "ctoken_user_fkey"))
    private User user;

    /**
     * A data de expiração do token.
     */
    @Builder.Default private Instant expiration = Instant.now().plus(1, ChronoUnit.DAYS);

}
