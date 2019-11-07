package br.com.myevents.controller;

import br.com.myevents.model.Event;
import br.com.myevents.model.dto.EventDTO;
import br.com.myevents.model.dto.NewEventDTO;
import br.com.myevents.model.dto.SimpleEventDTO;
import br.com.myevents.security.UserAccountDetails;
import br.com.myevents.service.EventService;
import br.com.myevents.utils.SimpleMessage;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * A classe responsável pelo tratamento de requisições de {@link Event}.
 */
@RestController
@RequestMapping("/event")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class EventController {

    private final EventService eventService;

    @PostMapping("/register")
    public ResponseEntity<Object> registerEvent(
            @AuthenticationPrincipal UserAccountDetails userAccountDetails,
            @Validated @RequestBody NewEventDTO newEvent
    ) {
        eventService.registerEvent(userAccountDetails, newEvent);
        return ResponseEntity.ok(SimpleMessage.builder().message("Registrado com sucesso!").build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEvent(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.retrieveEvent(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<SimpleEventDTO>> getEvents(
            @AuthenticationPrincipal UserAccountDetails userAccountDetails
    ) {
        return ResponseEntity.ok(eventService.retrieveEvents(userAccountDetails));
    }

}
