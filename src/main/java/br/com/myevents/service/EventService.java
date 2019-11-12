package br.com.myevents.service;

import br.com.myevents.exception.CityNotFoundException;
import br.com.myevents.exception.EventNotFoundException;
import br.com.myevents.exception.UserNotFoundException;
import br.com.myevents.model.Address;
import br.com.myevents.model.Event;
import br.com.myevents.model.dto.EventDTO;
import br.com.myevents.model.dto.NewEventDTO;
import br.com.myevents.model.dto.SimpleEventDTO;
import br.com.myevents.model.dto.SimpleUserDTO;
import br.com.myevents.repository.CityRepository;
import br.com.myevents.repository.EventRepository;
import br.com.myevents.repository.UserRepository;
import br.com.myevents.utils.SimpleMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
                .image(newEvent.getImage())
                .attachments(newEvent.getAttachments())
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
                .image(event.getImage())
                .attachments(event.getAttachments())
                .user(new SimpleUserDTO(
                        event.getUser().getEmail(), event.getUser().getName(), event.getUser().getPhone()))
                .build();
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
                        .image(event.getImage())
                        .build())
                .collect(Collectors.toList());
    }

}
