package br.com.myevents.controller;

import br.com.myevents.model.Guest;
import br.com.myevents.service.GuestService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Trata requisições de {@link Guest}.
 */
@RestController
@RequestMapping("/guest")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class GuestController {

    private final GuestService guestService;

}
