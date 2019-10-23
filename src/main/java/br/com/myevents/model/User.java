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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Representa um usuário.
 */
@Entity
@Table(name = "user_account")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * A chave primária de um usuário.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * O email de um usuário.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * A senha de um usuário.
     */
    @Column(nullable = false, length = 127)
    private String password;

    /**
     * O nome completo de um usuário.
     */
    @Column(nullable = false)
    private String fullName;

    /**
     * O CPF de um usuário.
     */
    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    /**
     * O número de celular de um usuário.
     */
    @Column(nullable = false, length = 11)
    private String phoneNumber;

}
