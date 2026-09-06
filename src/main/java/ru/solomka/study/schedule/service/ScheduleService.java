package ru.solomka.study.schedule.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.solomka.study.schedule.api.model.lesson.Lesson;
import ru.solomka.study.schedule.api.model.lesson.ScheduleInfo;
import ru.solomka.study.schedule.api.model.user.UserRole;
import ru.solomka.study.schedule.api.repository.LessonRepository;
import ru.solomka.study.schedule.exception.BadRequestClientException;
import ru.solomka.study.schedule.security.AuthenticationProvider;
import ru.solomka.study.schedule.security.ScheduleUserDetail;
import ru.solomka.study.schedule.service.helper.ScheduleHelper;

import java.util.List;
import java.util.Set;

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
            throw new BadRequestClientException("Empty groupId");
        }
        if (items == null || items.isEmpty()) {
            throw new BadRequestClientException("Empty schedule info");
        }

        List<Lesson> lessons = scheduleHelper.buildLessonByScheduleInfo(groupId, items);

        scheduleHelper.validateNoTimeOverlap(lessons);

        if (userDetail.getRole() == UserRole.TEACHER) {
            Long currentTeacherId = userDetail.getId();

            if (lessons.stream().anyMatch(lesson -> lesson.id() == null)) {
                throw new BadRequestClientException("Teacher can only edit existing classes (id is required for all items)");
            }
            if (!lessons.stream().allMatch(lesson -> currentTeacherId.equals(lesson.teacherId()))) {
                throw new BadRequestClientException("The teacher can only edit their own classes");
            }

            scheduleHelper.validateNoTimeOverlapWithDatabase(groupId, lessons, Set.of());

            return lessonRepository.updateAll(lessons);
        }

        List<Lesson> spotEditLessons = lessons.stream().filter(lesson -> lesson.id() != null).toList();
        List<Lesson> newLessons = lessons.stream().filter(lesson -> lesson.id() == null).toList();

        if (!newLessons.isEmpty()) {
            List<Integer> daysToReplace = newLessons.stream()
                    .map(Lesson::dayOfWeek)
                    .distinct()
                    .toList();

            List<Long> idsToExclude = spotEditLessons.stream().map(Lesson::id).toList();

            scheduleHelper.validateNoTimeOverlapWithDatabase(
                    groupId,
                    spotEditLessons,
                    Set.copyOf(daysToReplace)
            );

            lessonRepository.deleteLessonsInDaysOfWeekExcludingIds(groupId, daysToReplace, idsToExclude);
            return lessonRepository.createAll(newLessons);
        }

        if (!spotEditLessons.isEmpty()) {
            scheduleHelper.validateNoTimeOverlapWithDatabase(groupId, spotEditLessons, Set.of());
            return lessonRepository.updateAll(spotEditLessons);
        }

        return lessons;
    }

}