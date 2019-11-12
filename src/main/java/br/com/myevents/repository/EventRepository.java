package br.com.myevents.repository;

import br.com.myevents.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Responsável pela persistência de {@link Event}.
 */
@Repository
@Transactional(readOnly = true)
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByUser_Email(String email);

    Optional<Event> findByIdAndUser_Email(Long id, String email);

}
