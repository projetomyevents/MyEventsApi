package br.com.myevents.controller;

import br.com.myevents.model.Guest;
import br.com.myevents.model.dto.GuestDTO;
import br.com.myevents.model.dto.GuestUpdateDTO;
import br.com.myevents.service.GuestService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Trata requisições de {@link Guest}.
 */
@RestController
@RequestMapping("/guest")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class GuestController {

    private final GuestService guestService;

    @GetMapping("/")
    public ResponseEntity<GuestDTO> getGuest(@RequestParam("token") String token) {
        return ResponseEntity.ok(guestService.retrieveGuest(token));
    }

    @PutMapping("/")
    public ResponseEntity<Object> putGuest(
            @RequestParam("token") String token,
            @Validated @RequestBody GuestUpdateDTO guest
    ) {
        return ResponseEntity.ok(guestService.updateGuest(token, guest));
    }

}
