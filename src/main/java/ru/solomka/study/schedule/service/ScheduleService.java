package ru.solomka.study.schedule.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.solomka.study.schedule.api.model.Lesson;
import ru.solomka.study.schedule.api.model.ScheduleInfo;
import ru.solomka.study.schedule.api.repository.LessonRepository;
import ru.solomka.study.schedule.exception.BadRequestClientExceptiom;
import ru.solomka.study.schedule.service.helper.ScheduleHelper;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScheduleService {

    LessonRepository lessonRepository;
    ScheduleHelper scheduleHelper;

    public ScheduleService(LessonRepository lessonRepository, ScheduleHelper scheduleHelper) {
        this.lessonRepository = lessonRepository;
        this.scheduleHelper = scheduleHelper;
    }

    @Transactional
    public List<Lesson> updateAllScheduleForGroup(String groupId, List<ScheduleInfo> items) {
        if(groupId.isEmpty())
            throw new BadRequestClientExceptiom("Empty groupId");

        if(items.isEmpty())
            throw new BadRequestClientExceptiom("Empty schedule info");

        // Max counnt iter - 7
        List<Lesson> lessons = scheduleHelper.buildLessonByScheduleInfo(groupId, items);

        lessonRepository.deleteLessonsInDaysOfWeek(
                lessons.stream()
                        .map(Lesson::dayOfWeek)
                        .toList()
        );

        // Единой пачкой загрузка, так что все +- окей по скорости будет
        return lessonRepository.createAll(lessons);
    }
}
