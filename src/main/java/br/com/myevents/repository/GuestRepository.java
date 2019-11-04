package br.com.myevents.repository;

import br.com.myevents.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Responsável pela persistência de {@link Guest}.
 */
@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> { }
