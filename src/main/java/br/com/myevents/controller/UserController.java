package br.com.myevents.controller;

import br.com.myevents.model.User;
import br.com.myevents.model.dto.NewUserDTO;
import br.com.myevents.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * A classe responsável pelo tratamento de requisições de {@link User}.
 */
@RestController
@RequestMapping(value = "/user")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/register")
    public ResponseEntity<?> registerUser(@Validated @RequestBody NewUserDTO newUser) {
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(userService.registerUser(newUser)).toUri()
        ).build();
    }

}
