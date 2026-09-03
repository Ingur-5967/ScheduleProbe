package ru.solomka.study.schedule.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.solomka.study.schedule.api.model.security.User;
import ru.solomka.study.schedule.controller.request.AuthenticationRequest;
import ru.solomka.study.schedule.security.AuthenticationType;
import ru.solomka.study.schedule.security.jwt.TokenPair;
import ru.solomka.study.schedule.service.SecurityService;

@RestController
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SecurityController {

    SecurityService securityService;

    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<TokenPair> login(@RequestBody AuthenticationRequest authenticationRequest) {
        TokenPair tokenPair = securityService.login(
                authenticationRequest.getLogin(),
                authenticationRequest.getPassword()
        );
        return ResponseEntity.ok(tokenPair);
    }

    @PostMapping(value = "/registration", produces = "application/json")
    public ResponseEntity<User> registration(@RequestParam("authType") AuthenticationType authenticationType,
                                             @RequestBody AuthenticationRequest authenticationRequest) {
        User user = securityService.registration(
                authenticationRequest.getLogin(),
                authenticationRequest.getPassword(),
                authenticationType
        );
        return ResponseEntity.ok(user);
    }
}
