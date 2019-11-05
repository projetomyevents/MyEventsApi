package br.com.myevents.service;

import br.com.myevents.model.Address;
import br.com.myevents.model.City;
import br.com.myevents.model.State;
import br.com.myevents.model.dto.CityDTO;
import br.com.myevents.model.dto.StateDTO;
import br.com.myevents.repository.CityRepository;
import br.com.myevents.repository.StateRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementa a lógica de serviços de {@link Address}, {@link City} e {@link State}.
 */
@Service
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class AddressService {

    private final CityRepository cityRepository;
    private final StateRepository stateRepository;

    public List<CityDTO> retrieveAllCities() {
        return cityRepository.findAll().stream()
                .map(city -> CityDTO.builder()
                        .id(city.getId())
                        .name(city.getName())
                        .stateId(city.getState().getId())
                        .build())
                .collect(Collectors.toList());
    }

    public List<StateDTO> retrieveAllStates() {
        return stateRepository.findAll().stream()
                .map(state -> StateDTO.builder()
                        .id(state.getId())
                        .name(state.getName())
                        .build())
                .collect(Collectors.toList());
    }

}
