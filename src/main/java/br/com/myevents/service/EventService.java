package br.com.myevents.service;

import br.com.myevents.exception.CityNotFoundException;
import br.com.myevents.exception.EmailNotFoundException;
import br.com.myevents.exception.EventNotFoundException;
import br.com.myevents.model.Address;
import br.com.myevents.model.City;
import br.com.myevents.model.Event;
import br.com.myevents.model.User;
import br.com.myevents.model.dto.EventDTO;
import br.com.myevents.model.dto.NewEventDTO;
import br.com.myevents.model.dto.SimpleEventDTO;
import br.com.myevents.model.dto.SimpleUserDTO;
import br.com.myevents.repository.CityRepository;
import br.com.myevents.repository.EventRepository;
import br.com.myevents.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementa a lógica de serviços de {@link Event}.
 */
@Service
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class EventService {

    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final EventRepository eventRepository;

    /**
     * Registra um novo evento.
     *
     * @param userEmail o email do dono do evento
     * @param newEvent o novo evento
     */
    public void registerEvent(String userEmail, NewEventDTO newEvent) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(
                () -> new EmailNotFoundException(
                        String.format("Nenhum usuário encontrado com o email '%s'.", userEmail)));

        City city = cityRepository.findById(newEvent.getCityId()).orElseThrow(
                () -> new CityNotFoundException(
                        String.format("Nenhuma cidade encontrada com identificador '%d'.", newEvent.getCityId())));

        eventRepository.save(Event.builder()
                .name(newEvent.getName())
                .startDate(newEvent.getStartDate())
                .companionLimit(newEvent.getCompanionLimit())
                .description(newEvent.getDescription())
                .schedule(newEvent.getSchedule())
                .admissionPrice(newEvent.getAdmissionPrice())
                .minAge(newEvent.getMinAge())
                .attire(newEvent.getAttire())
                .address(Address.builder()
                        .CEP(newEvent.getCEP())
                        .city(city)
                        .neighborhood(newEvent.getNeighborhood())
                        .street(newEvent.getStreet())
                        .number(newEvent.getNumber())
                        .complement(newEvent.getComplement())
                        .build())
                .image(newEvent.getImage())
                .attachments(newEvent.getAttachments())
                .user(user)
                .build());
    }

    /**
     * Retorna um evento da base de dados a partir do seu identificador.
     *
     * @param id o identificador
     * @return o evento
     */
    public EventDTO retrieveEvent(Long id) {
        Event event = eventRepository.findById(id).orElseThrow(
                () -> new EventNotFoundException("O evento não existe."));

        return EventDTO.builder()
                .id(id)
                .name(event.getName())
                .startDate(event.getStartDate())
                .description(event.getDescription())
                .schedule(event.getSchedule())
                .admissionPrice(event.getAdmissionPrice())
                .minAge(event.getMinAge())
                .attire(event.getAttire())
                .CEP(event.cepRepr())
                .stateCity(event.stateCityRepr())
                .local(event.localRepr())
                .image(event.getImage())
                .attachments(event.getAttachments())
                .user(SimpleUserDTO.builder()
                        .email(event.getUser().getEmail())
                        .name(event.getUser().getName())
                        .phone(event.getUser().phoneRepr())
                        .build())
                .build();
    }

    /**
     * Retorna todos os eventos de um usuário.
     *
     * @param userEmail o email do usuário
     * @return todos os eventos do usuário
     */
    public List<SimpleEventDTO> retrieveEvents(String userEmail) {
        return eventRepository.findAllByUser_Email(userEmail).stream()
                .map(event -> SimpleEventDTO.builder()
                        .id(event.getId())
                        .name(event.getName())
                        .description(event.getDescription())
                        .image(event.getImage())
                        .build()).collect(Collectors.toList());
    }

}
