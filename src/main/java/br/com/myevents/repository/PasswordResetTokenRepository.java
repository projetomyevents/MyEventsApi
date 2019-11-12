package br.com.myevents.repository;

import br.com.myevents.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Responsável pela persistência de {@link PasswordResetToken}.
 */
@Repository
@Transactional(readOnly = true)
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByToken(String token);

    @Transactional
    void deleteAllByUser_Id(Integer id);

}
