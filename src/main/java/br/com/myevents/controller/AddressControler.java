package br.com.myevents.controller;

import br.com.myevents.model.City;
import br.com.myevents.model.State;
import br.com.myevents.model.dto.CityDTO;
import br.com.myevents.model.dto.StateDTO;
import br.com.myevents.service.AddressService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * A classe responsável pelo tratamento de requisições de {@link City} e {@link State}.
 */
@RestController
@RequestMapping("/address")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class AddressControler {

    private final AddressService addressService;

    @GetMapping("/cities")
    public ResponseEntity<List<CityDTO>> getAllCities() {
        return ResponseEntity.ok(addressService.retrieveAllCities());
    }

    @GetMapping("/states")
    public ResponseEntity<List<StateDTO>> getAllStates() {
        return ResponseEntity.ok(addressService.retrieveAllStates());
    }

}
