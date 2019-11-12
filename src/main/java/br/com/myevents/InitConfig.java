package br.com.myevents;

import br.com.myevents.model.City;
import br.com.myevents.model.State;
import br.com.myevents.model.dto.CityDTO;
import br.com.myevents.model.dto.StateDTO;
import br.com.myevents.repository.CityRepository;
import br.com.myevents.repository.StateRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Configuração de uma execução do aplicativo.
 */
@Configuration
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class InitConfig {

    private final StateRepository stateRepository;
    private final CityRepository cityRepository;

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

}
