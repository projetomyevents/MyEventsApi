package br.com.myevents.config;

import br.com.myevents.domain.Usuario;
import br.com.myevents.repository.UsuarioRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@ConditionalOnProperty(prefix = "spring.jpa.hibernate", name = "ddl-auto", havingValue = "create-drop")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class DevConfig {

    private final UsuarioRepository userRepo;
    private final BCryptPasswordEncoder pe;

    /**
     * Populate the database with sample data for testing.
     */
    @Bean
    protected void populateDatabaseForTesting() {
        Usuario usuario1 = Usuario.builder().email("gustavoheidemann@gmail.com").nome("Gustavo Heidemann")
                .cpf("23423424323").celular("82377832").senha(pe.encode("123456")).build();

        usuario1.addPerfil();  // pq eu usei builder

        userRepo.save(usuario1);
    }

}
