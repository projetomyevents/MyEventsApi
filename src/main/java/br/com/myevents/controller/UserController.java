package br.com.myevents.controller;

import br.com.myevents.model.User;
import br.com.myevents.model.dto.NewUserDTO;
import br.com.myevents.model.dto.UserDTO;
import br.com.myevents.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<Void> registerUser(@Validated @RequestBody NewUserDTO newUser) {
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(userService.registerUser(newUser)).toUri()
        ).build();
    }

    @GetMapping(value = "/{email}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String email) {
        User user = userService.getUser(email);
        return ResponseEntity.ok(UserDTO.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .CPF(user.getCPF())
                .phone(user.getPhone())
                .build());
    }

    @PostMapping(value = "/confirm", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> confirmUser(@RequestParam(value = "token") String token) {
        return ResponseEntity.ok(userService.confirmUser(token));
    }

}
