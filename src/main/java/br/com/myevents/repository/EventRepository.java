package br.com.myevents.repository;

import br.com.myevents.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Responsável pela persistência de {@link Event}.
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> { }
