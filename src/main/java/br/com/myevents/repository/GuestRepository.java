package br.com.myevents.repository;

import br.com.myevents.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Responsável pela persistência de {@link Guest}.
 */
@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {

    @Transactional(readOnly = true)
    Optional<Guest> findGuestByEmail(String guestEmail);

    @Transactional(readOnly = true)
    List<Guest> findGuestsByEvent_Id(Long eventId);

}
