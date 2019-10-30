package br.com.myevents;

import br.com.myevents.model.User;
import br.com.myevents.repository.UserRepository;
import br.com.myevents.security.enums.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Configuração de uma execução do aplicativo em modo dev.
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.jpa.hibernate", name = "ddl-auto",
        havingValue = "create-drop")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class DevConfig {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

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
        userRepository.save(adminUser);
    }

}
