package br.com.myevents;

import br.com.myevents.model.City;
import br.com.myevents.model.State;
import br.com.myevents.model.dto.CityDTO;
import br.com.myevents.model.dto.StateDTO;
import br.com.myevents.repository.CityRepository;
import br.com.myevents.repository.StateRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * Configuração de uma execução do aplicativo.
 */
@Configuration
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class InitConfig {

    private final StateRepository stateRepository;
    private final CityRepository cityRepository;

    /**
     * Registra todos os estados do Brasil.
     */
    @Bean
    protected void registerBrazilStates() throws Exception {
        for (StateDTO state :new ObjectMapper().readValue(
                new ClassPathResource("br_states.json").getInputStream(), StateDTO[].class)) {
            stateRepository.save(State.builder()
                    .id(state.getId())
                    .name(state.getName())
                    .build());
        }
    }

    /**
     * Registra todos as cidades do Brasil.
     */
    @Bean
    protected void registerBrazilCities() throws Exception {
        for (CityDTO city :new ObjectMapper().readValue(
                new ClassPathResource("br_cities.json").getInputStream(), CityDTO[].class)) {
            cityRepository.save(City.builder()
                    .id(city.getId())
                    .name(city.getName())
                    .state(State.builder().id(city.getStateId()).build())  // só precisamos do id do estado
                    .build());
        }
    }

}
