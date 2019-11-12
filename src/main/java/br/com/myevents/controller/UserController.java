package br.com.myevents.controller;

import br.com.myevents.model.User;
import br.com.myevents.model.dto.NewPasswordDTO;
import br.com.myevents.model.dto.NewUserDTO;
import br.com.myevents.model.dto.SimpleUserDTO;
import br.com.myevents.security.TokenService;
import br.com.myevents.service.UserService;
import br.com.myevents.utils.SimpleMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Trata requisições de {@link User}.
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;

    @GetMapping("/sucessful-authentication/{token}")
    public ResponseEntity<SimpleUserDTO> sucessfulAuthentication(@PathVariable String token) {
        return ResponseEntity.ok()
                .header("Authorization", String.format("Bearer %s", token))
                .header("Access-Control-Expose-Headers", "Authorization")
                .body(userService.retrieveSimpleUser(tokenService.getEmail(token)));
    }

    @PostMapping("/register")
    public ResponseEntity<SimpleMessage> registerUser(@Validated @RequestBody NewUserDTO newUser) {
        return ResponseEntity.ok(userService.registerUser(newUser));
    }

    @GetMapping("/activate")
    public ResponseEntity<Object> activateUserAccount(@RequestParam("token") String token) {
        return ResponseEntity.ok(userService.activateUserAccount(token));
    }

    @GetMapping("/resend-activation/{email}")
    public ResponseEntity<Object> resendUserAccountActivation(@PathVariable String email) {
        return ResponseEntity.ok(userService.resendUserAccountActivation(email));
    }

    @PostMapping("/password-reset")
    public ResponseEntity<Object> resetUserAccountPassword(
            @RequestParam("token") String token,
            @Validated @RequestBody NewPasswordDTO newPassword
    ) {
        return ResponseEntity.ok(userService.resetUserAccountPassword(token, newPassword));
    }

    @GetMapping("/send-password-reset/{email}")
    public ResponseEntity<Object> sendUserAccountPasswordReset(@PathVariable String email) {
        return ResponseEntity.ok(userService.sendUserAccountPasswordReset(email));
    }

}
