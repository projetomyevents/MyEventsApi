package br.com.myevents.service;

import br.com.myevents.exception.EventNotFoundException;
import br.com.myevents.exception.TokenExpiredException;
import br.com.myevents.exception.TokenGuestNotFoundException;
import br.com.myevents.exception.TokenNotFoundException;
import br.com.myevents.model.Event;
import br.com.myevents.model.Guest;
import br.com.myevents.model.StatusPresenceToken;
import br.com.myevents.model.dto.GuestDTO;
import br.com.myevents.model.dto.NewGuestDTO;
import br.com.myevents.model.dto.SimpleGuestDTO;
import br.com.myevents.repository.EventRepository;
import br.com.myevents.repository.GuestRepository;
import br.com.myevents.repository.StatusPresenceTokenRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementa a lógica de serviços de {@link Guest}.
 */
@Service
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class GuestService {

    private final GuestRepository guestRepository;
    private final EventRepository eventRepository;
    private final MailSenderService mailSenderService;
    private final StatusPresenceTokenRepository statusPresenceTokenRepository;

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
                            () -> {
                                mailSenderService.sendHtml(
                                        updatedGuest.getEmail(),
                                        "Você foi convidado para um evento",
                                        String.format(
                                                "Você foi convidado para o evento " +
                                                        "<a href='http://localhost:4200/event/%d'>%s</a> " +
                                                        "organizado por %s. Para confirmar ou recusar sua presença " +
                                                        "acesse esse <a href='bruh'>link</a>.",
                                                event.getId(),
                                                event.getName(),
                                                event.getUser().getName()));
                            });

                    return updatedGuest;
                })
                .collect(Collectors.toList()));
    }

    /**
     * Atualiza os dados do convidado.
     *
     * @param token o token do convidado
     * @param guest o convidado com novos dados
     */
    public void updateGuest(String token, Object guest) {
        StatusPresenceToken statusPresenceToken = statusPresenceTokenRepository.findByValue(token).orElseThrow(
                () -> new TokenNotFoundException("O token de status de presença não existe."));

        if (statusPresenceToken.getExpiration().isBefore(Instant.now())) {
            throw new TokenExpiredException("O token de status de presença expirou.");
        }

        Guest tokenGuest = Optional.of(statusPresenceToken.getGuest()).orElseThrow(
                () -> new TokenGuestNotFoundException(
                        "O token de status de presença não está vinculado a nenhum convidado."));

        guestRepository.save(tokenGuest);
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
