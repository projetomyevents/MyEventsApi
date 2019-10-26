package br.com.myevents.security;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Representa os detalhes de uma conta de usuário.
 */
@Builder
@Getter
public class UserAccountDetails {

    private static final long serialVersionUID = 1L;

    /**
     * O identificador da conta de usuário.
     */
    @Getter private Integer id;

    /**
     * O email usado para autênticar a conta de usuário.
     */
    private String email;

    /**
     * A senha usada para autênticar a conta de um usuário.
     */
    private String password;

    /**
     * As permissões da conta de um usuário.
     */
    @Singular private Collection<? extends GrantedAuthority> authorities;

    /**
     * Indica se a conta de usuário está ativada.
     * Uma conta de usuário desativada não pode ser autênticada.
     */
    private boolean enabled;

    /**
     * Indica se a conta de usuário está expirada.
     * Uma conta de usuário expirada não pode ser autênticada.
     */
    private boolean accountNonExpired;

    /**
     * Indica se a conta de usuário está bloqueada.
     * Uma conta de usuário bloqueada não pode ser autênticada.
     */
    private boolean accountNonLocked;

    /**
     * Indica se as credenciais de um usuário (senha) está expirada
     * Credenciais expiradas não deixam a conta de usuário ser autênticada.
     */
    private boolean credentialsNonExpired;

}
