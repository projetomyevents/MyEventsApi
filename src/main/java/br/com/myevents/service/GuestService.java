package br.com.myevents.service;

import br.com.myevents.exception.EventNotFoundException;
import br.com.myevents.model.Guest;
import br.com.myevents.model.dto.GuestDTO;
import br.com.myevents.model.dto.SimpleGuestDTO;
import br.com.myevents.repository.EventRepository;
import br.com.myevents.repository.GuestRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementa a lógica de serviços de {@link Guest}.
 */
@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class GuestService {

    private final GuestRepository guestRepository;
    private final EventRepository eventRepository;

    /**
     * Retorna a lista de convidados de um evento.
     *
     * @param id o identificador do evento
     * @return a lista de convidados do evento
     */
    public List<SimpleGuestDTO> retrieveGuests(Long id) {
        return guestRepository.findGuestsByEvent_Id(
                eventRepository.findById(id).orElseThrow(
                        () -> new EventNotFoundException("Evento não existe.")).getId()).stream()
                .map(guest -> SimpleGuestDTO.builder()
                        .name(guest.getName())
                        .presenceStatus(guest.getPresenceStatus())
                        .confirmedCompanions(guest.getConfirmedCompanions())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * Retorna a lista de convidados de um evento para ser atualizada.
     *
     * @param email o email do usuário
     * @param id o identificador do evento
     * @return a lista de convidados do evento
     */
    public List<GuestDTO> retrieveGuests(String email, Long id) {
        return guestRepository.findGuestsByEvent_Id(
                eventRepository.findByIdAndUser_Email(id, email).orElseThrow(
                        () -> new EventNotFoundException(
                                "Evento não encontrado ou você não é dono do evento.")).getId()).stream()
                .map(guest -> GuestDTO.builder()
                        .id(guest.getId())
                        .name(guest.getName())
                        .email(guest.getEmail())
                        .presenceStatus(guest.getPresenceStatus())
                        .companionLimit(guest.getCompanionLimit())
                        .confirmedCompanions(guest.getConfirmedCompanions())
                        .build())
                .collect(Collectors.toList());
    }

}
