package br.com.myevents.repository;

import br.com.myevents.model.ConfirmationToken;
import br.com.myevents.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

/**
 * Responsável pela persistência de {@link ConfirmationToken}.
 */
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    @Transactional(readOnly = true)
    Optional<ConfirmationToken> findByToken(String token);

    @Transactional(readOnly = true)
    List<ConfirmationToken> findAllByUser(User user);

}
