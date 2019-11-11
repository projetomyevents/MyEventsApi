package br.com.myevents.controller;

import br.com.myevents.model.Guest;
import br.com.myevents.service.GuestService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A classe responsável pelo tratamento de requisições de {@link Guest}.
 */
@RestController
@RequestMapping("/guest")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class GuestController {

    private final GuestService guestService;

}
