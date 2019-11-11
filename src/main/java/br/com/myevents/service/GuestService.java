package br.com.myevents.service;

import br.com.myevents.model.Guest;
import br.com.myevents.repository.GuestRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementa a lógica de serviços de {@link Guest}.
 */
@Service
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class GuestService {

    private final GuestRepository guestRepository;

}
