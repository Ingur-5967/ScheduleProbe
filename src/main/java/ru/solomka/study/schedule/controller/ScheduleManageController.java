package ru.solomka.study.schedule.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.solomka.study.schedule.api.model.lesson.Lesson;
import ru.solomka.study.schedule.api.model.lesson.LessonTimeTag;
import ru.solomka.study.schedule.controller.request.ScheduleEditRequest;
import ru.solomka.study.schedule.controller.request.ScheduleTagSetRequest;
import ru.solomka.study.schedule.security.annotation.TeacherPreAuthorize;
import ru.solomka.study.schedule.service.LessonTimeTagService;
import ru.solomka.study.schedule.service.ScheduleService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teacher/schedule")
@TeacherPreAuthorize
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScheduleManageController {

    ScheduleService scheduleService;
    LessonTimeTagService lessonTimeTagService;

    public ScheduleManageController(ScheduleService scheduleService, LessonTimeTagService lessonTimeTagService) {
        this.scheduleService = scheduleService;
        this.lessonTimeTagService = lessonTimeTagService;
    }

    @PostMapping(value = "/edit", produces = "application/json")
    public ResponseEntity<List<Lesson>> editScheduleForGroup(@RequestParam("groupId") String groupId,
                                                             @RequestBody ScheduleEditRequest scheduleEditRequest) {
        return ResponseEntity.ok(scheduleService.updateAllScheduleForGroup(groupId, scheduleEditRequest.scheduleInfo()));
    }

    @PostMapping(value = "/tag", produces = "application/json")
    public ResponseEntity<List<LessonTimeTag>> setTimeTagsForGroup(@RequestBody ScheduleTagSetRequest scheduleTagSetRequest) {
        return ResponseEntity.ok(lessonTimeTagService.assignLessonTimeTags(scheduleTagSetRequest.timeTags()));
    }

}
