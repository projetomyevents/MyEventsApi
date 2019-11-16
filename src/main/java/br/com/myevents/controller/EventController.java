package br.com.myevents.controller;

import br.com.myevents.model.Event;
import br.com.myevents.model.dto.EventDTO;
import br.com.myevents.model.dto.GuestDTO;
import br.com.myevents.model.dto.NewEventDTO;
import br.com.myevents.model.dto.SimpleEventDTO;
import br.com.myevents.model.dto.SimpleGuestDTO;
import br.com.myevents.security.UserAccountDetails;
import br.com.myevents.service.EventService;
import br.com.myevents.service.GuestService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Trata requisições de {@link Event}.
 */
@RestController
@RequestMapping("/event")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class EventController {

    private final EventService eventService;
    private final GuestService guestService;

    @PostMapping("/create")
    public ResponseEntity<Object> postEvent(
            @AuthenticationPrincipal UserAccountDetails userAccountDetails,
            @Validated @RequestBody NewEventDTO newEvent
    ) {
        return ResponseEntity.ok(eventService.createEvent(userAccountDetails.getEmail(), newEvent));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEvent(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.retrieveEvent(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEvent(
            @AuthenticationPrincipal UserAccountDetails userAccountDetails,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(eventService.removeEvent(userAccountDetails.getEmail(), id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<SimpleEventDTO>> getEvents(
            @AuthenticationPrincipal UserAccountDetails userAccountDetails
    ) {
        return ResponseEntity.ok(eventService.retrieveEvents(userAccountDetails.getEmail()));
    }

    @GetMapping("/{id}/guests")
    public ResponseEntity<List<SimpleGuestDTO>> getGuests(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(guestService.retrieveGuests(id));
    }

    @GetMapping("/{id}/guests/edit")
    public ResponseEntity<List<GuestDTO>> getGuests(
            @AuthenticationPrincipal UserAccountDetails userAccountDetails,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(guestService.retrieveGuests(userAccountDetails.getEmail(), id));
    }

}
