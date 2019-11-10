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
     * O identificador da conta do usuário.
     */
    @Getter
    private Integer id;

    /**
     * O email da conta do usuário.
     */
    private String email;

    /**
     * A senha da conta do usuário.
     */
    private String password;

    /**
     * As permissões da conta do usuário.
     */
    @Singular
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * Indica se a conta do usuário está ativada.
     * Uma conta de usuário desativada não pode ser autenticada.
     */
    private boolean enabled;

    /**
     * Indica se a conta do usuário está expirada.
     * Uma conta de usuário expirada não pode ser autenticada.
     */
    private boolean accountNonExpired;

    /**
     * Indica se a conta do usuário está bloqueada.
     * Uma conta de usuário bloqueada não pode ser autenticada.
     */
    private boolean accountNonLocked;

    /**
     * Indica se as credenciais da conta do usuário está expirada.
     * Uma conta de usuário com credenciais expiradas não pode ser autenticada.
     */
    private boolean credentialsNonExpired;

}
