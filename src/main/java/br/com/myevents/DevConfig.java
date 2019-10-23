package br.com.myevents;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração de uma execução do aplicativo em modo dev.
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.jpa.hibernate", name = "ddl-auto",
        havingValue = "create-drop")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class DevConfig {

    /**
     * Preencher a base de dados com dados de amostra para testes.
     */
    @Bean
    protected void populateDatabaseForTesting() { }

}
