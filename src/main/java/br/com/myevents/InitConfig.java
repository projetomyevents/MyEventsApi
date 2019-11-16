package br.com.myevents;

import br.com.myevents.model.City;
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
import org.springframework.beans.factory.annotation.Value;
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
@EnableScheduling
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class InitConfig {

    private final StateRepository stateRepository;
    private final CityRepository cityRepository;
    private final EventRepository eventRepository;
    private final MailSenderService mailSenderService;

    @Value("${website.url}")
    private String WEBSITE_URL;

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

    @Scheduled(cron = "0 0 3 * * *")
    public void resendInviteMessageForPendingGuests() {
        LocalDate today = LocalDate.now();
        eventRepository.findAll().stream()
                .filter(event -> ChronoUnit.WEEKS.between(event.getCreatedAt(), today) > 0)
                .forEach(event -> event.getGuests().stream()
                        .filter(guest -> guest.getPresenceStatus().equals(PresenceStatus.PENDING))
                        .forEach(System.out::println));
//                        .forEach(guest -> mailSenderService.sendHtml(
//                                guest.getEmail(),
//                                "Lembrete de Evento MyEvents",
//                                String.format(
//                                        "Olá %s, você foi convidado por %s para o evento " +
//                                                "<a href='%sevent/%d'>%s</a>, por favor confirme ou recuse " +
//                                                "sua presença neste evento. <strong>Você recebeu este email porque " +
//                                                "sua presença neste evento está pendente.</strong>",
//                                        guest.getName(),
//                                        event.getUser().getName(),
//                                        WEBSITE_URL,
//                                        event.getId(),
//                                        event.getName()))));
    }

}
