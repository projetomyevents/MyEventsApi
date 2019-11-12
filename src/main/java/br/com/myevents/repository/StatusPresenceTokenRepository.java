package br.com.myevents.repository;

import br.com.myevents.model.Guest;
import br.com.myevents.model.StatusPresenceToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Responsável pela persistência de {@link StatusPresenceToken}.
 */
@Repository
public interface StatusPresenceTokenRepository extends JpaRepository<StatusPresenceToken, Long> {

    @Transactional(readOnly = true)
    Optional<StatusPresenceToken> findByValue(String value);

    @Transactional(readOnly = true)
    List<StatusPresenceToken> findAllByGuest(Guest guest);

}
