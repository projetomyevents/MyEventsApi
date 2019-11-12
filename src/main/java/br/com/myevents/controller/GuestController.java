package br.com.myevents.controller;

import br.com.myevents.model.Guest;
import br.com.myevents.model.dto.NewGuestDTO;
import br.com.myevents.model.dto.SimpleMessage;
import br.com.myevents.security.UserAccountDetails;
import br.com.myevents.service.GuestService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @PostMapping("/update/{eventId}")
    public ResponseEntity<Object> updateEventGuestList(
            @AuthenticationPrincipal UserAccountDetails userAccountDetails,
            @Validated @RequestBody List<NewGuestDTO> guestList,
            @PathVariable Long eventId
    ) {
        guestService.updateEventGuestList(userAccountDetails.getEmail(), guestList, eventId);
        return ResponseEntity.ok(SimpleMessage.builder()
                .message("Lista de convidados atualizada com sucesso!")
                .build());
    }

    @PostMapping("/update-guest")
    public ResponseEntity<Object> updateGuest(
            @RequestParam("token") String token,
            @Validated @RequestBody Object guest
    ) {
        guestService.updateGuest(token, guest);
        return ResponseEntity.ok(SimpleMessage.builder()
                .message("Seus dados foram atualizados.")
                .build());
    }

    @GetMapping("/list/{eventId}")
    public ResponseEntity<List<?>> getGuestList(
            @PathVariable Long eventId
    ) {
        return ResponseEntity.ok(guestService.retrieveGuestList(eventId));
    }



    @GetMapping("/list/detailed/{eventId}")
    public ResponseEntity<List<?>> getGuestList(
            @AuthenticationPrincipal UserAccountDetails userAccountDetails,
            @PathVariable Long eventId
    ) {
        return ResponseEntity.ok(guestService.retrieveGuestList(userAccountDetails.getEmail(), eventId));
    }

}
