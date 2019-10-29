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
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

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
    private String name;

    /**
     * O CPF de um usuário.
     */
    @Column(nullable = false, unique = true, length = 11)
    private String CPF;

    /**
     * O número de celular de um usuário.
     */
    @Column(nullable = false, length = 11)
    private String phone;

    private boolean enabled;

    /**
     * Os cargos de um usuário.
     */
    @ElementCollection(fetch= FetchType.EAGER)
    @CollectionTable(name="ROLE", foreignKey = @ForeignKey(name = "user_role_fkey"))
    @Column(name = "role_name")
    @Singular private Set<Integer> roles;

    //<editor-fold desc="Custom getters and setters">

    public Set<Role> getRoles() {
        return roles.stream().map(Role::of).collect(Collectors.toSet());
    }

    //</editor-fold>

}
