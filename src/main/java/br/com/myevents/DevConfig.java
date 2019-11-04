package br.com.myevents;

import br.com.myevents.model.Event;
import br.com.myevents.model.Guest;
import br.com.myevents.model.User;
import br.com.myevents.model.enums.PresenceStatus;
import br.com.myevents.repository.EventRepository;
import br.com.myevents.repository.GuestRepository;
import br.com.myevents.repository.UserRepository;
import br.com.myevents.security.enums.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

/**
 * Configuração de uma execução do aplicativo em modo dev.
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.jpa.hibernate", name = "ddl-auto",
        havingValue = "create-drop")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class DevConfig {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final GuestRepository guestRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Preencher a base de dados com dados de amostra para testes.
     */
    @Bean
    protected void populateDatabaseForTesting() {
        User adminUser = User.builder()
                .email("admin@admin")
                .password(passwordEncoder.encode("123456"))
                .name("admin")
                .CPF("52829612884")
                .phone("8888888888")
                .role(Role.ADMIN.getId())
                .enabled(true)
                .build();

        User user1 = User.builder()
                .email("user1@user")
                .password(passwordEncoder.encode("123456"))
                .name("user1")
                .CPF("02115206835")
                .phone("8888888888")
                .role(Role.USER.getId())
                .enabled(true)
                .build();

        User user2 = User.builder()
                .email("user2@user")
                .password(passwordEncoder.encode("123456"))
                .name("user2")
                .CPF("01771591200")
                .phone("8888888888")
                .role(Role.USER.getId())
                .enabled(true)
                .build();

        userRepository.saveAll(Arrays.asList(adminUser, user1, user2));

        Event event1 = Event.builder()
                .name("Pagodinho")
                .startDate(LocalDate.now().plus(8, ChronoUnit.DAYS))
                .description("bruh")
                .schedule("18:00 -> Começa")
                .user(user1)
                .build();

        Event event2 = Event.builder()
                .name("Reunião")
                .startDate(LocalDate.now().plus(9, ChronoUnit.DAYS))
                .description("?")
                .schedule("16:00 -> Começa")
                .user(user2)
                .build();

        eventRepository.saveAll(Arrays.asList(event1, event2));

        Guest guest1 = Guest.builder()
                .name("guest1")
                .email("guest1@guest")
                .confirmedCompanions((byte) 0)
                .presenceStatus(PresenceStatus.ACCEPTED)
                .event(event1)
                .build();

        Guest guest2 = Guest.builder()
                .name("guest2")
                .email("guest2@guest")
                .confirmedCompanions((byte) 0)
                .presenceStatus(PresenceStatus.PENDING)
                .event(event1)
                .build();

        Guest guest3 = Guest.builder()
                .name("guest3")
                .email("guest3@guest")
                .confirmedCompanions((byte) 0)
                .presenceStatus(PresenceStatus.DENIED)
                .event(event2)
                .build();

        guestRepository.saveAll(Arrays.asList(guest1, guest2, guest3));
    }

}
