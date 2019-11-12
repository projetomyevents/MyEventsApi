package br.com.myevents.security;

import br.com.myevents.model.User;
import br.com.myevents.repository.UserRepository;
import br.com.myevents.security.exception.UserAccountNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

/**
 * Implementa a lógica de serviços de {@link UserAccountDetails}.
 */
@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAccountDetailsService {

    private final UserRepository userRepository;

    /**
     * Retorna os detalhes de uma conta de usuário a partir do seu email.
     *
     * @param email o email da conta do usuário
     * @return os detalhes da conta do usuário
     * @throws UserAccountNotFoundException se o email não está vinculado a nenhum usuário conhecido
     */
    public UserAccountDetails loadUserAccoutByEmail(String email) throws UserAccountNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserAccountNotFoundException("Conta de usuário não existe."));

        return UserAccountDetails.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toSet()))
                .enabled(user.isEnabled())
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();
    }

}
