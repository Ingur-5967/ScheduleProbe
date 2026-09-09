package ru.solomka.study.schedule.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.solomka.study.schedule.api.model.user.User;
import ru.solomka.study.schedule.controller.request.AuthenticationRequest;
import ru.solomka.study.schedule.security.AuthenticationType;
import ru.solomka.study.schedule.security.ScheduleUserDetail;
import ru.solomka.study.schedule.security.annotation.GuestPreAuthorize;
import ru.solomka.study.schedule.security.jwt.TokenPair;
import ru.solomka.study.schedule.service.RefreshTokenService;
import ru.solomka.study.schedule.service.SecurityService;

@RestController
@RequestMapping("/api/v1/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SecurityController {

    SecurityService securityService;
    RefreshTokenService refreshTokenService;

    public SecurityController(SecurityService securityService, RefreshTokenService refreshTokenService) {
        this.securityService = securityService;
        this.refreshTokenService = refreshTokenService;
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

    @PostMapping(value = "/logout", produces = "application/json")
    @GuestPreAuthorize
    public ResponseEntity<Boolean> logout(@AuthenticationPrincipal ScheduleUserDetail scheduleUserDetail) {
        return ResponseEntity.ok(refreshTokenService.revokeToken(scheduleUserDetail.getId()));
    }
}
