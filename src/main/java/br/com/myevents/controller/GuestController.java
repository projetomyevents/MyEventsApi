package br.com.myevents.controller;

import br.com.myevents.model.Guest;
import br.com.myevents.model.dto.NewGuestDTO;
import br.com.myevents.model.dto.SimpleEventDTO;
import br.com.myevents.model.dto.SimpleGuestDTO;
import br.com.myevents.model.dto.SimpleMessage;
import br.com.myevents.security.UserAccountDetails;
import br.com.myevents.service.GuestService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * A classe responsável pelo tratamento de requisições de {@link Guest}.
 */
@RestController
@RequestMapping("/guest")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class GuestController {

    private final GuestService guestService;

    @PostMapping("/register")
    public ResponseEntity<Object> registerEvent(
            @AuthenticationPrincipal UserAccountDetails userAccountDetails,
            @Validated @RequestBody List<NewGuestDTO> newGuestsDTO
    ) {
        guestService.registerGuests(userAccountDetails, newGuestsDTO);
        return ResponseEntity.ok(SimpleMessage.builder().message("Registrado com sucesso!").build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<SimpleGuestDTO>> getGuests(
            Long id
    ) {
        return ResponseEntity.ok(guestService.retrieveGuests(id));
    }

}
