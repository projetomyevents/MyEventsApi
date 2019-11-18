package br.com.myevents.service;

import br.com.myevents.exception.CityNotFoundException;
import br.com.myevents.exception.EventNotFoundException;
import br.com.myevents.exception.UserNotFoundException;
import br.com.myevents.model.Address;
import br.com.myevents.model.Event;
import br.com.myevents.model.File;
import br.com.myevents.model.dto.EventDTO;
import br.com.myevents.model.dto.FileDTO;
import br.com.myevents.model.dto.NewEventDTO;
import br.com.myevents.model.dto.SimpleEventDTO;
import br.com.myevents.model.dto.SimpleUserDTO;
import br.com.myevents.model.enums.PresenceStatus;
import br.com.myevents.repository.CityRepository;
import br.com.myevents.repository.EventRepository;
import br.com.myevents.repository.UserRepository;
import br.com.myevents.utils.SimpleMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementa a lógica de serviços de {@link Event}.
 */
@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class EventService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CityRepository cityRepository;
    private final MailSenderService mailSenderService;

    /**
     * Cria um novo evento.
     *
     * @param email o email do dono do evento
     * @param newEvent o novo evento
     */
    public SimpleMessage createEvent(String email, NewEventDTO newEvent) {
        eventRepository.save(Event.builder()
                .name(newEvent.getName())
                .startDate(newEvent.getStartDate())
                .companionLimit(newEvent.getCompanionLimit())
                .description(newEvent.getDescription())
                .schedule(newEvent.getSchedule())
                .admissionPrice(newEvent.getAdmissionPrice())
                .minimumAge(newEvent.getMinimumAge())
                .attire(newEvent.getAttire())
                .address(Address.builder()
                        .CEP(newEvent.getCEP())
                        .city(cityRepository.findById(newEvent.getCityId()).orElseThrow(
                                () -> new CityNotFoundException("Cidade não existe.")))
                        .neighborhood(newEvent.getNeighborhood())
                        .street(newEvent.getStreet())
                        .number(newEvent.getNumber())
                        .complement(newEvent.getComplement())
                        .build())
                .image(Optional.ofNullable(newEvent.getImage())
                        .map(image -> File.builder()
                                .name(image.getName())
                                .type(image.getType())
                                .content(image.getContent())
                                .build())
                        .orElse(null))
                .attachments(newEvent.getAttachments().stream().map(attachment -> File.builder()
                        .name(attachment.getName())
                        .type(attachment.getType())
                        .content(attachment.getContent())
                        .build())
                        .collect(Collectors.toSet()))
                .user(userRepository.findByEmail(email).orElseThrow(
                        () -> new UserNotFoundException("O email não está vinculado a nenhum usuário.")))
                .build());

        return new SimpleMessage("Evento criado com sucesso!");
    }

    /**
     * Retorna um evento a partir do seu identificador.
     *
     * @param id o identificador do evento
     * @return o evento
     */
    public EventDTO retrieveEvent(Long id) {
        Event event = eventRepository.findById(id).orElseThrow(
                () -> new EventNotFoundException("Evento não existe."));

        return EventDTO.builder()
                .id(id)
                .name(event.getName())
                .startDate(event.getStartDate())
                .description(event.getDescription())
                .schedule(event.getSchedule())
                .admissionPrice(event.getAdmissionPrice())
                .minimumAge(event.getMinimumAge())
                .attire(event.getAttire())
                .CEP(event.getAddress().getCEP())
                .stateName(event.getAddress().getCity().getState().getName())
                .cityName(event.getAddress().getCity().getName())
                .neighborhood(event.getAddress().getNeighborhood())
                .street(event.getAddress().getStreet())
                .number(event.getAddress().getNumber())
                .complement(event.getAddress().getComplement())
                .image(Optional.ofNullable(event.getImage())
                        .map(image -> FileDTO.builder()
                                .name(image.getName())
                                .type(image.getType())
                                .content(image.getContent())
                                .build())
                        .orElse(null))
                .attachments(event.getAttachments().stream().map(attachment -> FileDTO.builder()
                        .name(attachment.getName())
                        .type(attachment.getType())
                        .content(attachment.getContent())
                        .build())
                        .collect(Collectors.toSet()))
                .user(new SimpleUserDTO(
                        event.getUser().getEmail(), event.getUser().getName(), event.getUser().getPhone()))
                .build();
    }

    /**
     * Cancela um evento de um usuário e notifica cada convidado confirmado ou pendente.
     *
     * @param email o email do usuário
     * @param id o identificador do evento
     * @return o resultado
     */
    public SimpleMessage removeEvent(String email, Long id) {
        Event event = eventRepository.findByIdAndUser_Email(id, email).orElseThrow(
                () -> new EventNotFoundException("Evento não encontrado ou você não é dono do evento."));

        eventRepository.deleteById(event.getId());

        event.getGuests().stream()
                .filter(guest -> !guest.getPresenceStatus().equals(PresenceStatus.DENIED))
                .forEach(guest -> mailSenderService.sendHtml(
                        guest.getEmail(),
                        "Cancelamento de Evento MyEvents",
                        String.format(
                                "Olá %s, o evento %s foi cancelado por %s. <strong>Você recebeu este email porque " +
                                        "sua presença neste evento estava confirmada ou pendente.</strong>",
                                guest.getName(),
                                event.getName(),
                                event.getUser().getName())));

        return new SimpleMessage("Evento excluido e todos os convidados confirmados ou pendentes notificados.");
    }

    /**
     * Retorna todos os eventos de um usuário.
     *
     * @param email o email do usuário
     * @return todos os eventos do usuário
     */
    public List<SimpleEventDTO> retrieveEvents(String email) {
        return eventRepository.findAllByUser_Email(email).stream()
                .map(event -> SimpleEventDTO.builder()
                        .id(event.getId())
                        .name(event.getName())
                        .startDate(event.getStartDate())
                        .description(event.getDescription())
                        .image(Optional.ofNullable(event.getImage())
                                .map(image -> FileDTO.builder()
                                        .name(image.getName())
                                        .type(image.getType())
                                        .content(image.getContent())
                                        .build())
                                .orElse(null))
                        .build())
                .collect(Collectors.toList());
    }

}
