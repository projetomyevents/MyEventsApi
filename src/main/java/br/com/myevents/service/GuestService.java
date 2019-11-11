package br.com.myevents.service;

import br.com.myevents.exception.EventNotFoundException;
import br.com.myevents.model.Event;
import br.com.myevents.model.Guest;
import br.com.myevents.model.dto.GuestDTO;
import br.com.myevents.model.dto.NewGuestDTO;
import br.com.myevents.model.dto.SimpleGuestDTO;
import br.com.myevents.repository.EventRepository;
import br.com.myevents.repository.GuestRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementa a lógica de serviços de {@link Guest}.
 */
@Service
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class GuestService {

    private final GuestRepository guestRepository;
    private final EventRepository eventRepository;

    /**
     * Atualiza a lista de convidados de um evento.
     *
     * @param userEmail o email do usuário
     * @param guestList a lista de convidados atualizada
     */
    public void updateEventGuestList(String userEmail, List<NewGuestDTO> guestList, Long eventId) {
        Event event = eventRepository.findByUser_EmailAndId(userEmail, eventId).orElseThrow(
                () -> new EventNotFoundException("Evento não encontrado ou o usuário não é dono do evento."));

        guestRepository.saveAll(guestList.stream()
                .map(guest -> {
                    Guest updatedGuest = Guest.builder()
                            .name(guest.getName())
                            .email(guest.getEmail())
                            .companionLimit(guest.getCompanionLimit())  // TODO: tratar valores nulos
                            .event(event)
                            .build();

                    guestRepository.findGuestByEmail(guest.getEmail()).ifPresentOrElse(
                            // caso o usuário já exista definir os dados atualizados antes de sua atualização na db
                            (foundGuest) -> {
                                updatedGuest.setId(foundGuest.getId());
                                updatedGuest.setConfirmedCompanions(foundGuest.getConfirmedCompanions());
                                updatedGuest.setPresenceStatus(foundGuest.getPresenceStatus());
                            },
                            // TODO: caso seja um convidado novo enviar email de convite
                            () -> System.out.println("bruh"));

                    return updatedGuest;
                })
                .collect(Collectors.toList()));
    }

    /**
     * Retorna a lista de convidados de um evento.
     *
     * @param eventId o identificador do evento
     * @return a lista de convidados do evento
     */
    public List<SimpleGuestDTO> retrieveGuestList(Long eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new EventNotFoundException("Evento não encontrado.");
        }

        return guestRepository.findGuestsByEvent_Id(eventId).stream()
                .map(guest -> SimpleGuestDTO.builder()
                        .name(guest.getName())
                        .presenceStatus(guest.getPresenceStatus().getId())
                        .confirmedCompanions(guest.getConfirmedCompanions())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * Retorna a lista de convidados de um evento para ser atualizada.
     *
     * @param userEmail o email do usuário
     * @param eventId o identificador do evento
     * @return a lista de convidados do evento
     */
    public List<GuestDTO> retrieveGuestList(String userEmail, Long eventId) {
        eventRepository.findByUser_EmailAndId(userEmail, eventId).orElseThrow(
                () -> new EventNotFoundException("Evento não encontrado ou o usuário não é dono do evento."));

        return guestRepository.findGuestsByEvent_Id(eventId).stream()
                .map(guest -> GuestDTO.builder()
                        .id(guest.getId())
                        .name(guest.getName())
                        .email(guest.getEmail())
                        .presenceStatus(guest.getPresenceStatus().getId())
                        .companionLimit(guest.getCompanionLimit())
                        .confirmedCompanions(guest.getConfirmedCompanions())
                        .build())
                .collect(Collectors.toList());
    }

}
