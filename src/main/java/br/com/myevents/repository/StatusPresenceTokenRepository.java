package br.com.myevents.repository;

import br.com.myevents.model.StatusPresenceToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Responsável pela persistência de {@link StatusPresenceToken}.
 */
@Repository
@Transactional(readOnly = true)
public interface StatusPresenceTokenRepository extends JpaRepository<StatusPresenceToken, Long> {

    Optional<StatusPresenceToken> findByGuest_Id(Long id);

    Optional<StatusPresenceToken> findByToken(String token);

}
