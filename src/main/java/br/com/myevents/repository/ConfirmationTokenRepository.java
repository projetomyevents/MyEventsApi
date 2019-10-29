package br.com.myevents.repository;

import br.com.myevents.model.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Responsável pela persistência de {@link ConfirmationToken}.
 */
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    @Transactional(readOnly = true)
    Optional<ConfirmationToken> findByToken(String token);

}
