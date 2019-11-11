package br.com.myevents.service;

import br.com.myevents.exception.EmailNotFoundException;
import br.com.myevents.exception.EventNotFoundException;
import br.com.myevents.model.Event;
import br.com.myevents.model.Guest;
import br.com.myevents.model.User;
import br.com.myevents.model.dto.EventDTO;
import br.com.myevents.model.dto.GuestDTO;
import br.com.myevents.model.dto.NewGuestDTO;
import br.com.myevents.model.dto.SimpleGuestDTO;
import br.com.myevents.model.dto.SimpleUserDTO;
import br.com.myevents.model.enums.PresenceStatus;
import br.com.myevents.repository.EventRepository;
import br.com.myevents.repository.GuestRepository;
import br.com.myevents.repository.UserRepository;
import br.com.myevents.security.UserAccountDetails;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementa a lógica de serviços de {@link Guest}.
 */
@Service
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class GuestService {

    private final GuestRepository guestRepository;

    private final EventRepository eventRepository;

    private final UserRepository userRepository;

    public Guest registerGuests(UserAccountDetails userAccountDetails, List<NewGuestDTO> newGuestsDTO) {
        User user = userRepository.findByEmail(userAccountDetails.getEmail()).orElseThrow(
                () -> new EmailNotFoundException(
                        String.format("Nenhum usuário encontrado com o email '%s'.", userAccountDetails.getEmail())));

        //corrigir esta parte dps
        for (NewGuestDTO newGuestDTO: newGuestsDTO) {
            Event event = eventRepository.findById(newGuestDTO.getEventId()).orElseThrow(
                    () -> new EventNotFoundException(
                            String.format("Nenhum evento encontrado com o id '%s'.", newGuestDTO.getEventId())));
            Guest guest = Guest.builder().name(newGuestDTO.getName())
                    .email(newGuestDTO.getEmail())
                    .companionLimit(newGuestDTO.getCompanionLimit())
                    .event(event).build();
            guestRepository.save(guest);
        }
    }

    public List<SimpleGuestDTO> retrieveGuests(Long id) {
        Event event = eventRepository.findById(id).orElseThrow(
                () -> new EventNotFoundException(
                        String.format("Nenhum evento encontrado com o id '%s'.", id)));
        List<Guest> guests = guestRepository.findGuestsByEvent_Id(event.getId());
        List<SimpleGuestDTO> guestDTOS = null;
        for (Guest guest : guests) {
           guestDTOS.add(SimpleGuestDTO.builder()
                    .name(guest.getName())
                    .confirmedCompanions(guest.getConfirmedCompanions())
                    .presenceStatus(PresenceStatus.PENDING)
                    .build());
        }
        return guestDTOS;
    }

}
