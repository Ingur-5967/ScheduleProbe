package ru.solomka.study.schedule.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.solomka.study.schedule.api.model.Assessment;
import ru.solomka.study.schedule.api.model.AssessmentType;
import ru.solomka.study.schedule.api.model.Lesson;
import ru.solomka.study.schedule.api.model.ScheduleInfo;
import ru.solomka.study.schedule.security.annotation.GhostPreAuthorize;
import ru.solomka.study.schedule.security.annotation.StudentPreAuthorize;
import ru.solomka.study.schedule.service.AssessmentService;
import ru.solomka.study.schedule.service.LessonService;
import ru.solomka.study.schedule.service.helper.ScheduleHelper;

import java.util.List;

@RestController
@RequestMapping("/view/schedule")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScheduleController {

    LessonService lessonService;
    ScheduleHelper scheduleHelper;

    private final AssessmentService assessmentService;

    public ScheduleController(LessonService lessonService, ScheduleHelper scheduleHelper,
                              AssessmentService assessmentService) {
        this.lessonService = lessonService;
        this.scheduleHelper = scheduleHelper;
        this.assessmentService = assessmentService;
    }

    @GetMapping(value = "/class", produces = "application/json")
    @StudentPreAuthorize
    public ResponseEntity<List<ScheduleInfo>> getScheduleForGroup(@RequestParam(value = "groupId", required = false) Long groupId) {
        List<Lesson> lessons = lessonService.findAllLessonByGroupId(groupId);
        return ResponseEntity.ok(scheduleHelper.buildScheduleInfo(lessons));
    }

    @GetMapping(value = "/assessment", produces = "application/json")
    @StudentPreAuthorize
    public ResponseEntity<List<Assessment>> getAllAssessmentForGroup(@RequestParam(value = "groupId", required = false) String groupId,
                                                                     @RequestParam(value = "types", required = false) List<AssessmentType> types) {
        return ResponseEntity.ok(assessmentService.findAllAssessmentByGroupId(groupId, types));
    }
}
