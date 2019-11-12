package br.com.myevents.repository;

import br.com.myevents.model.ActivationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Responsável pela persistência de {@link ActivationToken}.
 */
@Repository
@Transactional(readOnly = true)
public interface ActivationTokenRepository extends JpaRepository<ActivationToken, Long> {

    Optional<ActivationToken> findByToken(String token);

    @Transactional
    void deleteAllByUser_Id(Integer id);

}
