package br.com.myevents.service;

import br.com.myevents.exception.EventNotFoundException;
import br.com.myevents.exception.TokenExpiredException;
import br.com.myevents.exception.TokenNotFoundException;
import br.com.myevents.model.Event;
import br.com.myevents.model.Guest;
import br.com.myevents.model.StatusPresenceToken;
import br.com.myevents.model.dto.GuestDTO;
import br.com.myevents.model.dto.GuestEditDTO;
import br.com.myevents.model.dto.GuestUpdateDTO;
import br.com.myevents.model.dto.SimpleGuestDTO;
import br.com.myevents.repository.EventRepository;
import br.com.myevents.repository.GuestRepository;
import br.com.myevents.repository.StatusPresenceTokenRepository;
import br.com.myevents.utils.SimpleMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementa a lógica de serviços de {@link Guest}.
 */
@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class GuestService {

    private final GuestRepository guestRepository;
    private final EventRepository eventRepository;
    private final StatusPresenceTokenRepository statusPresenceTokenRepository;
    private final MailSenderService mailSenderService;

    @Value("${website.url}")
    private String WEBSITE_URL;

    /**
     * Retorna os dados do convidado que possui o token de status de presença especificado.
     *
     * @param token o token de status de presença
     * @return os dados do convidado
     */
    public GuestDTO retrieveGuest(String token) {
        StatusPresenceToken statusPresenceToken = statusPresenceTokenRepository.findByToken(token).orElseThrow(
                () -> new TokenNotFoundException("Token de status de presença inválido."));

        if (statusPresenceToken.getExpiration().isAfter(statusPresenceToken.getGuest().getEvent().getStartDate())) {
            throw new TokenExpiredException("Token de status de presença expirado.");
        }

        Guest guest = statusPresenceToken.getGuest();
        return GuestDTO.builder()
                .name(guest.getName())
                .email(guest.getEmail())
                .presenceStatus(guest.getPresenceStatus())
                .companionLimit(guest.getCompanionLimit())
                .confirmedCompanions(guest.getConfirmedCompanions())
                .build();
    }

    /**
     * Atualiza os dados do convidado que possui o token de status de presença especificado.
     *
     * @param token o token de status de presença
     * @param guest o convidado com informações atualizadas
     * @return o convidado com informaçõs atualizadas
     */
    public SimpleMessage updateGuest(String token, GuestUpdateDTO guest) {
        StatusPresenceToken statusPresenceToken = statusPresenceTokenRepository.findByToken(token).orElseThrow(
                () -> new TokenNotFoundException("Token de status de presença inválido."));

        if (statusPresenceToken.getExpiration().isAfter(statusPresenceToken.getGuest().getEvent().getStartDate())) {
            throw new TokenExpiredException("Token de status de presença expirado.");
        }

        Guest updatableGuest = statusPresenceToken.getGuest();

        // se houver modificações atualizar na base de dados
        if (!updatableGuest.getPresenceStatus().equals(guest.getPresenceStatus())
                || !updatableGuest.getConfirmedCompanions().equals(guest.getConfirmedCompanions())) {
            if (guest.getConfirmedCompanions().compareTo(updatableGuest.getCompanionLimit()) > 0) {
                throw new RuntimeException("Número de acompanhantes confirmados inválido, " +
                        "pois seu valor é maior do que o seu limite de acompanhantes.");
            }
            guestRepository.save(updatableGuest.setPresenceStatus(guest.getPresenceStatus())
                    .setConfirmedCompanions(guest.getConfirmedCompanions()));
        }

        return new SimpleMessage("Seus dados foram atualizados.");
    }

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
                        .name(guest.getName())
                        .email(guest.getEmail())
                        .presenceStatus(guest.getPresenceStatus())
                        .companionLimit(guest.getCompanionLimit())
                        .confirmedCompanions(guest.getConfirmedCompanions())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * Atualiza a lista de convidados de um evento de um usuário.
     *
     * @param email o email do usuário
     * @param id o identificador do evento
     * @param guests a lista de convidados
     * @return o resultado
     */
    public SimpleMessage updateGuests(String email, Long id, Set<GuestEditDTO> guests) {
        Event event = eventRepository.findByIdAndUser_Email(id, email).orElseThrow(
                () -> new EventNotFoundException("Evento não encontrado ou você não é dono do evento."));

        // percorrer a nova lista de convidados e adicionar novos convidados ou atualizar convidados existentes
        guests.forEach(updatedGuest -> event.getGuests().stream()
                .filter(originalGuest -> originalGuest.getEmail().equals(updatedGuest.getEmail()))
                .findAny()
                .ifPresentOrElse(
                        // atualizar convidado existente (preservar id, email e confirmedCompanions), enviar notificação
                        updatableGuest -> {
                            // ignorar caso os dados do convidado forem idênticos
                            if (updatedGuest.getName().equals(updatableGuest.getName()) && updatedGuest.getCompanionLimit().equals(updatableGuest.getCompanionLimit())) {
                                return;
                            }

                            guestRepository.save(updatableGuest.setName(updatedGuest.getName())
                                    .setCompanionLimit(updatedGuest.getCompanionLimit()));

                            mailSenderService.sendHtml(
                                    updatedGuest.getEmail(),
                                    "Atualização de Dados MyEvents",
                                    String.format(
                                            "Olá %1$s, seus dados de convidados foram atualizados pelo dono " +
                                                    "do evento (%2$s) no evento <a href='%3$sevent/%4$d'>%5$s</a>. " +
                                                    "Para atualizar sua presença no evento siga este " +
                                                    "<a href='%3$sguest;token=%6$s'>link</a>.",
                                            updatedGuest.getName(),
                                            event.getUser().getName(),
                                            WEBSITE_URL,
                                            event.getId(),
                                            event.getName(),
                                            statusPresenceTokenRepository
                                                    .findByGuest_Id(updatableGuest.getId()).orElseThrow(
                                                            () -> new TokenNotFoundException(String.format(
                                                                    "Token do convidado '%s' não existe.",
                                                                    updatableGuest.getEmail()))).getToken()));
                        },
                        // adicionar novo convidado, enviar convite
                        () -> {
                            Guest newGuest = guestRepository.save(Guest.builder()
                                    .name(updatedGuest.getName())
                                    .email(updatedGuest.getEmail())
                                    .companionLimit(Optional.ofNullable(updatedGuest.getCompanionLimit())
                                            .orElse(event.getCompanionLimit()))
                                    .event(event)
                                    .build());

                            mailSenderService.sendHtml(
                                    updatedGuest.getEmail(),
                                    "Convite de Evento MyEvents",
                                    String.format(
                                            "Olá %1$s, você foi convidado para o evento " +
                                                    "<a href='%2$sevent/%3$d'>%4$s</a> organizado por %5$s. " +
                                                    "Para atualizar sua presença no evento siga este " +
                                                    "<a href='%2$sguest;token=%6$s'>link</a>. " +
                                                    "<strong>Este link dura até o início do evento.</strong>",
                                            updatedGuest.getName(),
                                            WEBSITE_URL,
                                            event.getId(),
                                            event.getName(),
                                            event.getUser().getName(),
                                            statusPresenceTokenRepository.save(
                                                    new StatusPresenceToken(
                                                            event.getStartDate(), newGuest)).getToken()));
                        }));

        // remover convidados ausentes na nova lista de convidados
        event.getGuests().stream()
                .filter(originalGuest -> guests.stream()
                        .noneMatch(updatedGuest -> updatedGuest.getEmail().equals(originalGuest.getEmail())))
                // para cada convidados ausente na nova lista de convidados, enviar notificação sobre a sua remoção
                .forEach(removedGuest -> {
                    guestRepository.deleteById(removedGuest.getId());

                    mailSenderService.sendHtml(
                            removedGuest.getEmail(),
                            "Removido do Evento MyEvents",
                            String.format(
                                    "Olá %s, você foi removido do evento <a href='%sevent/%d'>%s</a> pelo dono " +
                                            "do evento (%s).",
                                    removedGuest.getName(),
                                    WEBSITE_URL,
                                    event.getId(),
                                    event.getName(),
                                    event.getUser().getName()));
                });

        return new SimpleMessage("Lista de convidados atualizada.");
    }

}
