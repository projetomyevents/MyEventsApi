package br.com.myevents.service;

import br.com.myevents.model.Address;
import br.com.myevents.model.City;
import br.com.myevents.model.State;
import br.com.myevents.model.dto.CityDTO;
import br.com.myevents.model.dto.StateDTO;
import br.com.myevents.repository.CityRepository;
import br.com.myevents.repository.StateRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementa a lógica de serviços de {@link Address}, {@link City} e {@link State}.
 */
@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AddressService {

    private final CityRepository cityRepository;
    private final StateRepository stateRepository;

    /**
     * Retorna todos as cidades do Brasil.
     *
     * @return as cidades do Brasil
     */
    public List<CityDTO> retrieveCities() {
        return cityRepository.findAll().stream()
                .map(city -> new CityDTO(city.getId(), city.getName(), city.getState().getId()))
                .collect(Collectors.toList());
    }

    /**
     * Retorna todos os estados do Brasil.
     *
     * @return os estados do Brasil
     */
    public List<StateDTO> retrieveStates() {
        return stateRepository.findAll().stream()
                .map(state -> new StateDTO(state.getId(), state.getName()))
                .collect(Collectors.toList());
    }

}
