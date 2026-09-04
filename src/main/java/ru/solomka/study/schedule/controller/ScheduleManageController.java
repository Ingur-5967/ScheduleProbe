package ru.solomka.study.schedule.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.solomka.study.schedule.api.model.Lesson;
import ru.solomka.study.schedule.controller.request.ScheduleEditRequest;
import ru.solomka.study.schedule.security.ScheduleUserDetail;
import ru.solomka.study.schedule.security.annotation.TeacherPreAuthorize;
import ru.solomka.study.schedule.service.ScheduleService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teacher/schedule")
@TeacherPreAuthorize
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScheduleManageController {

    ScheduleService scheduleService;

    public ScheduleManageController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // todo: Редактирование исключительно своих групп, за которыми преподаватель закреплен.
    //  Если не преподаватель, предоставлен полный доступ
    @PostMapping(value = "/edit", produces = "application/json")
    public ResponseEntity<List<Lesson>> editScheduleForGroup(@AuthenticationPrincipal ScheduleUserDetail scheduleUserDetail,
                                                             @RequestParam("groupId") String groupId,
                                                             @RequestBody ScheduleEditRequest scheduleEditRequest) {
        System.out.println(scheduleUserDetail.getUsername());
        return ResponseEntity.ok(
                scheduleService.updateAllScheduleForGroup(
                        groupId, scheduleEditRequest.getScheduleInfo()
                )
        );
    }
}
