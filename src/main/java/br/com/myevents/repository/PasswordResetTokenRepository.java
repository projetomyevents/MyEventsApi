package br.com.myevents.repository;

import br.com.myevents.model.PasswordResetToken;
import br.com.myevents.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Responsável pela persistência de {@link PasswordResetToken}.
 */
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    @Transactional(readOnly = true)
    Optional<PasswordResetToken> findByToken(String token);

    @Transactional(readOnly = true)
    List<PasswordResetToken> findAllByUser(User user);

}
