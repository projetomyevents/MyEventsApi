package br.com.myevents.repository;

import br.com.myevents.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Responsável pela persistência de {@link Guest}.
 */
@Repository
@Transactional(readOnly = true)
public interface GuestRepository extends JpaRepository<Guest, Long> {

    List<Guest> findGuestsByEvent_Id(Long id);

}
