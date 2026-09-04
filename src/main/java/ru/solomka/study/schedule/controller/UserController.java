package ru.solomka.study.schedule.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.solomka.study.schedule.api.model.security.User;
import ru.solomka.study.schedule.model.mapper.Mapper;
import ru.solomka.study.schedule.security.AuthenticationProvider;
import ru.solomka.study.schedule.security.ScheduleUserDetail;
import ru.solomka.study.schedule.security.annotation.GhostPreAuthorize;

@RestController
@RequestMapping("/api/v1/lk")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    AuthenticationProvider authenticationProvider;
    Mapper<User, ScheduleUserDetail> mapper;

    public UserController(AuthenticationProvider authenticationProvider, Mapper<User, ScheduleUserDetail> mapper) {
        this.authenticationProvider = authenticationProvider;
        this.mapper = mapper;
    }

    @GetMapping(value = "/me", produces = "application/json")
    @GhostPreAuthorize
    public ResponseEntity<User> me() {
        User mappedUser = mapper.mapToDomain(authenticationProvider.getCurrentAuthenticatedUser());
        return ResponseEntity.ok(mappedUser);
    }
}
