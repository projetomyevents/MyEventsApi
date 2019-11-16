package br.com.myevents;

import br.com.myevents.model.City;
import br.com.myevents.model.Event;
import br.com.myevents.model.Guest;
import br.com.myevents.model.State;
import br.com.myevents.model.dto.CityDTO;
import br.com.myevents.model.dto.StateDTO;
import br.com.myevents.model.enums.PresenceStatus;
import br.com.myevents.repository.CityRepository;
import br.com.myevents.repository.EventRepository;
import br.com.myevents.repository.StateRepository;
import br.com.myevents.service.MailSenderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Configuração de uma execução do aplicativo.
 */
@Configuration
@EnableScheduling // habilita o Scheduling ao iniciar as configurações iniciais
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class InitConfig {

    private final StateRepository stateRepository;
    private final CityRepository cityRepository;
    private final EventRepository eventRepository;
    private final MailSenderService mailSenderService;

    /**
     * Registra todos os estados do Brasil.
     */
    @Bean
    protected void registerBrazilStates() throws Exception {
        stateRepository.saveAll(Arrays.stream(
                new ObjectMapper()
                        .readValue(new ClassPathResource("br_states.json").getInputStream(), StateDTO[].class))
                .map(state -> State.builder()
                        .id(state.getId())
                        .name(state.getName())
                        .build())
                .collect(Collectors.toList()));
    }

    /**
     * Registra todos as cidades do Brasil.
     */
    @Bean
    protected void registerBrazilCities() throws Exception {
        cityRepository.saveAll(Arrays.stream(
                new ObjectMapper()
                        .readValue(new ClassPathResource("br_cities.json").getInputStream(), CityDTO[].class))
                .map(city -> City.builder()
                        .id(city.getId())
                        .name(city.getName())
                        .state(new State(city.getStateId()))
                        .build())
                .collect(Collectors.toList()));
    }

    @Scheduled(cron = "0 0 0,21 ? * * *")
    public void resendEmailFromGuestPending() {
        LocalDate today = LocalDate.now();
        for (Event event : eventRepository.findAll()) {
            if (ChronoUnit.WEEKS.between(event.getCreatedAt(), today) > 0) {
                for (Guest guest : event.getGuests()) {
                    if (guest.getPresenceStatus().equals(PresenceStatus.PENDING)) {
                        mailSenderService.sendHtml(
                                guest.getEmail(),
                                "Lembrete de Evento MyEvents",
                               String.format("Olá "+guest.getName())
                                       +" este email indica que você tem um convite pendente para o evento de "
                                       +event.getUser().getName()
                                       +", por gentileza informe seu status de presença no evento para que dessa forma o evento possa ser organizado da melhor forma. :)");
                    }
                }
            }
        }
    }
}
