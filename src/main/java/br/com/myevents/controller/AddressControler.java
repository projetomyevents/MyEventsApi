package br.com.myevents.controller;

import br.com.myevents.model.dto.CityDTO;
import br.com.myevents.model.dto.StateDTO;
import br.com.myevents.service.AddressService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Trata requisições de {@link br.com.myevents.model.City} e {@link br.com.myevents.model.State}.
 */
@RestController
@RequestMapping("/address")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AddressControler {

    private final AddressService addressService;

    @GetMapping("/cities")
    public ResponseEntity<List<CityDTO>> getCities() {
        return ResponseEntity.ok(addressService.retrieveCities());
    }

    @GetMapping("/states")
    public ResponseEntity<List<StateDTO>> getStates() {
        return ResponseEntity.ok(addressService.retrieveStates());
    }

}
