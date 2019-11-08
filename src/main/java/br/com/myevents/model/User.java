package br.com.myevents.model;

import br.com.myevents.security.enums.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

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
     * A chave primária do usuário.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * O email do usuário.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * A senha do usuário.
     */
    @Column(nullable = false, length = 60)
    private String password;

    /**
     * O nome completo do usuário.
     */
    @Column(nullable = false)
    private String name;

    /**
     * O CPF do usuário.
     */
    @Column(nullable = false, unique = true, length = 11)
    private String CPF;

    /**
     * O número de celular do usuário.
     */
    @Column(nullable = false, length = 11)
    private String phone;

    /**
     * O estado de confirmação do usuário.
     */
    private boolean enabled;

    /**
     * Os cargos do usuário.
     */
    @ElementCollection(fetch= FetchType.EAGER)
    @CollectionTable(name="ROLE", foreignKey = @ForeignKey(name = "user_role_fkey"))
    @Column(name = "role_name")
    @Singular private Set<Role> roles;

    /**
     * Os eventos de um usuário.
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", orphanRemoval = true)
    @Singular private Set<Event> events;

}
