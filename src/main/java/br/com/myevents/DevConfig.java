package br.com.myevents;

import br.com.myevents.model.Address;
import br.com.myevents.model.City;
import br.com.myevents.model.Event;
import br.com.myevents.model.Guest;
import br.com.myevents.model.User;
import br.com.myevents.model.enums.PresenceStatus;
import br.com.myevents.repository.EventRepository;
import br.com.myevents.repository.GuestRepository;
import br.com.myevents.repository.UserRepository;
import br.com.myevents.security.enums.Role;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

/**
 * Configuração de uma execução do aplicativo em modo dev.
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.jpa.hibernate", name = "ddl-auto", havingValue = "create-drop")
@DependsOn({"registerBrazilStates", "registerBrazilCities"})
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class DevConfig {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final GuestRepository guestRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    /**
     * Preencher a base de dados com dados de amostra para testes.
     */
    @Bean
    protected void populateDatabaseForTesting() throws IOException {
        User adminUser = User.builder()
                .email("admin@admin")
                .password(passwordEncoder.encode("123456"))
                .name("admin")
                .CPF("52829612884")
                .phone("8888888888")
                .role(Role.ADMIN)
                .enabled(true)
                .build();

        User user1 = User.builder()
                .email("user1@user")
                .password(passwordEncoder.encode("123456"))
                .name("user1")
                .CPF("02115206835")
                .phone("8888888888")
                .role(Role.USER)
                .enabled(true)
                .build();

        User user2 = User.builder()
                .email("user2@user")
                .password(passwordEncoder.encode("123456"))
                .name("user2")
                .CPF("01771591200")
                .phone("8888888888")
                .role(Role.USER)
                .enabled(true)
                .build();

        userRepository.saveAll(Arrays.asList(adminUser, user1, user2));

        Event event1 = Event.builder()
                .name("Evento teste 1 [com imagem] [sem anexos]")
                .startDate(LocalDate.now().plus(3, ChronoUnit.DAYS))
                .companionLimit(6)
                .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor " +
                        "incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud " +
                        "exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.")
                .schedule("18:00 -> Começa")
                .address(Address.builder()
                        .CEP("12312122")
                        .neighborhood("Bairro fictício 21")
                        .street("Rua fictícia 15")
                        .city(new City(12))
                        .build())
                .image(new ClassPathResource("event-image-1.png").getInputStream().readAllBytes())
                .user(user1)
                .build();

        Event event2 = Event.builder()
                .name("Evento teste 2 [com imagem] [com anexos]")
                .startDate(LocalDate.now().plus(5, ChronoUnit.DAYS))
                .companionLimit(13)
                .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor " +
                        "incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud " +
                        "exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure " +
                        "dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. " +
                        "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit " +
                        "anim id est laborum.\n" +
                        "Curabitur pretium tincidunt lacus. Nulla gravida orci a odio. Nullam varius, turpis et " +
                        "commodo pharetra, est eros bibendum elit, nec luctus magna felis sollicitudin mauris. " +
                        "Integer in mauris eu nibh euismod gravida. Duis ac tellus et risus vulputate vehicula. " +
                        "Donec lobortis risus a elit. Etiam tempor. Ut ullamcorper, ligula eu tempor congue, eros " +
                        "est euismod turpis, id tincidunt sapien risus a quam. Maecenas fermentum consequat mi. " +
                        "Donec fermentum. Pellentesque malesuada nulla a mi. Duis sapien sem, aliquet nec, commodo " +
                        "eget, consequat quis, neque. Aliquam faucibus, elit ut dictum aliquet, felis nisl " +
                        "adipiscing sapien, sed malesuada diam lacus eget erat. Cras mollis scelerisque nunc. " +
                        "Nullam arcu. Aliquam consequat. Curabitur augue lorem, dapibus quis, laoreet et, " +
                        "pretium ac, nisi. Aenean magna nisl, mollis quis, molestie eu, feugiat in, orci. " +
                        "In hac habitasse platea dictumst.")
                .schedule("16:00 -> Começa")
                .address(Address.builder()
                        .CEP("12313123")
                        .neighborhood("Bairro fictício 4561")
                        .street("Rua fictícia 87")
                        .city(new City(512))
                        .build())
                .image(new ClassPathResource("event-image-1.png").getInputStream().readAllBytes())
                .attachment(new ClassPathResource("event-attachment-1.txt").getInputStream().readAllBytes())
                .attachment(new ClassPathResource("event-attachment-2.txt").getInputStream().readAllBytes())
                .attachment(new ClassPathResource("event-attachment-3.txt").getInputStream().readAllBytes())
                .user(user2)
                .build();

        Event event3 = Event.builder()
                .name("Evento teste 3 [com imagem] [sem anexos]")
                .startDate(LocalDate.now().plus(7, ChronoUnit.DAYS))
                .companionLimit(4)
                .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor " +
                        "incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud " +
                        "exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure " +
                        "dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. " +
                        "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit " +
                        "anim id est laborum.")
                .schedule("07:00 -> Começa")
                .address(Address.builder()
                        .CEP("53223411")
                        .neighborhood("Bairro fictício 75")
                        .street("Rua fictícia 234")
                        .city(new City(1231))
                        .build())
                .image(new ClassPathResource("event-image-2.jpeg").getInputStream().readAllBytes())
                .user(user2)
                .build();

        Event event4 = Event.builder()
                .name("Evento teste 4 [sem imagem] [sem anexos]")
                .startDate(LocalDate.now().plus(6, ChronoUnit.DAYS))
                .companionLimit(19)
                .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor " +
                        "incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud " +
                        "exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure " +
                        "dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. " +
                        "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit " +
                        "anim id est laborum.\n" +
                        "Curabitur pretium tincidunt lacus. Nulla gravida orci a odio. Nullam varius, turpis et " +
                        "commodo pharetra, est eros bibendum elit, nec luctus magna felis sollicitudin mauris. " +
                        "Integer in mauris eu nibh euismod gravida.")
                .schedule("23:00 -> Começa")
                .address(Address.builder()
                        .CEP("53223411")
                        .neighborhood("Bairro fictício 124")
                        .street("Rua fictícia 568")
                        .city(new City(1231))
                        .build())
                .user(user2)
                .build();

        Event event5 = Event.builder()
                .name("Evento teste 5 [sem imagem] [com anexos]")
                .startDate(LocalDate.now().plus(42, ChronoUnit.DAYS))
                .companionLimit(35)
                .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor " +
                        "incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud " +
                        "exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure " +
                        "dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. " +
                        "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit " +
                        "anim id est laborum.\n" +
                        "Curabitur pretium tincidunt lacus.")
                .schedule("22:00 -> Começa")
                .address(Address.builder()
                        .CEP("53223411")
                        .neighborhood("Bairro fictício 74")
                        .street("Rua fictícia 724")
                        .city(new City(1231))
                        .build())
                .attachment(new ClassPathResource("event-attachment-1.txt").getInputStream().readAllBytes())
                .attachment(new ClassPathResource("event-attachment-2.txt").getInputStream().readAllBytes())
                .user(user2)
                .build();

        Event event6 = Event.builder()
                .name("Evento teste 6 [com imagem] [com anexos]")
                .startDate(LocalDate.now().plus(72, ChronoUnit.DAYS))
                .companionLimit(15)
                .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor " +
                        "incididunt ut labore et dolore magna aliqua.")
                .schedule("11:00 -> Começa")
                .address(Address.builder()
                        .CEP("53223411")
                        .neighborhood("Bairro fictício 568")
                        .street("Rua fictícia 689")
                        .city(new City(1231))
                        .build())
                .image(new ClassPathResource("event-image-3.jpeg").getInputStream().readAllBytes())
                .attachment(new ClassPathResource("event-attachment-1.txt").getInputStream().readAllBytes())
                .user(user2)
                .build();

        eventRepository.saveAll(Arrays.asList(event1, event2, event3, event4, event5, event6));

        Guest guest1 = Guest.builder()
                .name("guest1")
                .email("guest1@guest")
                .companionLimit(0)
                .presenceStatus(PresenceStatus.ACCEPTED)
                .event(event2)
                .build();

        Guest guest2 = Guest.builder()
                .name("guest2")
                .email("guest2@guest")
                .companionLimit(5)
                .confirmedCompanions(3)
                .presenceStatus(PresenceStatus.PENDING)
                .event(event2)
                .build();

        Guest guest3 = Guest.builder()
                .name("guest3")
                .email("guest3@guest")
                .companionLimit(5)
                .presenceStatus(PresenceStatus.DENIED)
                .event(event2)
                .build();

        guestRepository.saveAll(Arrays.asList(guest1, guest2, guest3));
    }

}
