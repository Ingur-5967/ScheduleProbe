package ru.solomka.study.schedule.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.solomka.study.schedule.api.model.security.User;
import ru.solomka.study.schedule.model.mapper.Mapper;
import ru.solomka.study.schedule.security.ScheduleUserDetail;
import ru.solomka.study.schedule.security.annotation.GuestPreAuthorize;
import ru.solomka.study.schedule.service.UserService;

@RestController
@RequestMapping("/api/v1/lk")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;
    Mapper<User, ScheduleUserDetail> mapper;

    public UserController(UserService userService, Mapper<User, ScheduleUserDetail> mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping(value = "/me", produces = "application/json")
    @GuestPreAuthorize
    public ResponseEntity<User> me(@AuthenticationPrincipal ScheduleUserDetail userDetail) {
        User mappedScheduledUser = mapper.mapToDomain(userDetail);
        User enrichedUser = userService.getEnrichedUserAdditionInfo(mappedScheduledUser);
        return ResponseEntity.ok(enrichedUser);
    }
}
