package ru.solomka.study.schedule.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.solomka.study.schedule.api.model.lesson.Lesson;
import ru.solomka.study.schedule.api.model.ScheduleInfo;
import ru.solomka.study.schedule.api.model.security.UserRole;
import ru.solomka.study.schedule.api.repository.LessonRepository;
import ru.solomka.study.schedule.exception.BadRequestClientExceptiom;
import ru.solomka.study.schedule.security.AuthenticationProvider;
import ru.solomka.study.schedule.security.ScheduleUserDetail;
import ru.solomka.study.schedule.service.helper.ScheduleHelper;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScheduleService {

    LessonRepository lessonRepository;
    ScheduleHelper scheduleHelper;
    AuthenticationProvider authenticationProvider;

    public ScheduleService(LessonRepository lessonRepository, ScheduleHelper scheduleHelper, AuthenticationProvider authenticationProvider) {
        this.lessonRepository = lessonRepository;
        this.scheduleHelper = scheduleHelper;
        this.authenticationProvider = authenticationProvider;
    }

    @Transactional
    public List<Lesson> updateAllScheduleForGroup(String groupId, List<ScheduleInfo> items) {
        ScheduleUserDetail userDetail = authenticationProvider.getCurrentAuthenticatedUser();

        if (groupId == null || groupId.isEmpty()) {
            throw new BadRequestClientExceptiom("Empty groupId");
        }
        if (items == null || items.isEmpty()) {
            throw new BadRequestClientExceptiom("Empty schedule info");
        }

        List<Lesson> lessons = scheduleHelper.buildLessonByScheduleInfo(groupId, items);

        if (userDetail.getRole() == UserRole.TEACHER) {
            Long currentTeacherId = userDetail.getId();

            boolean allLessonsBelongToTeacher = lessons.stream()
                    .allMatch(lesson -> currentTeacherId.equals(lesson.teacherId()));

            if (!allLessonsBelongToTeacher)
                throw new BadRequestClientExceptiom("The teacher can only edit their own classes");
        }

        List<Integer> daysOfWeekToUpdate = lessons.stream()
                .map(Lesson::dayOfWeek)
                .distinct()
                .toList();

        lessonRepository.deleteLessonsInDaysOfWeek(daysOfWeekToUpdate);

        return lessonRepository.createAll(lessons);
    }
}