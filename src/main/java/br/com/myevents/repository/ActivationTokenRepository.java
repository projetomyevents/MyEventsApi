package br.com.myevents.repository;

import br.com.myevents.model.ActivationToken;
import br.com.myevents.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Responsável pela persistência de {@link ActivationToken}.
 */
@Repository
public interface ActivationTokenRepository extends JpaRepository<ActivationToken, Long> {

    @Transactional(readOnly = true)
    Optional<ActivationToken> findByValue(String value);

    @Transactional(readOnly = true)
    List<ActivationToken> findAllByUser(User user);

}
