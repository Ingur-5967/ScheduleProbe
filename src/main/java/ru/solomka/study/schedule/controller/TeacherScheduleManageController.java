package ru.solomka.study.schedule.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.solomka.study.schedule.api.model.ScheduleInfo;
import ru.solomka.study.schedule.security.annotation.TeacherPreAuthorize;

@RestController
@RequestMapping("/teacher/schedule")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TeacherScheduleManageController {

    @PostMapping(value = "/edit", produces = "application/json")
    @TeacherPreAuthorize
    public ResponseEntity<ScheduleInfo> editScheduleForGroup() {
        return ResponseEntity.ok(null);
    }
}
