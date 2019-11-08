package br.com.myevents.security;

import br.com.myevents.exception.EmailNotFoundException;
import br.com.myevents.model.User;
import br.com.myevents.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

/**
 * Implementa a lógica de serviços de {@link UserAccountDetails}.
 */
@Service
@Transactional
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAccountDetailsService {

    private final UserRepository userRepository;

    /**
     * Retorna os detalhes de uma conta de usuário a partir do seu email.
     *
     * @param email o email do usuário
     * @return os detalhes da conta do usuário
     * @throws EmailNotFoundException se o email não está vinculado a nenhum usuário conhecido
     */
    public UserAccountDetails loadUserAccoutByEmail(String email) throws EmailNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            throw new EmailNotFoundException(
                    String.format("O email '%s' não está vinculado a nenhuma conta de usuário conhecido.", email));
        });

        return UserAccountDetails.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRoles()
                        .stream().map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toSet()))
                .enabled(user.isEnabled())
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();
    }

}
