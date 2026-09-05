package ru.solomka.study.schedule.service.helper;

import org.springframework.stereotype.Component;
import ru.solomka.study.schedule.api.model.lesson.Lesson;
import ru.solomka.study.schedule.api.model.ScheduleInfo;
import ru.solomka.study.schedule.api.model.ScheduleItem;
import ru.solomka.study.schedule.service.UserService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ScheduleHelper {

    public List<ScheduleInfo> buildScheduleInfo(List<Lesson> lessons) {
        if (lessons == null || lessons.isEmpty()) {
            return List.of();
        }

        return lessons.stream()
                .collect(Collectors.groupingBy(Lesson::dayOfWeek))
                .entrySet().stream()
                .map(entry -> new ScheduleInfo(
                        entry.getKey(),
                        entry.getValue().stream()
                                .map(this::mapLessonToScheduleItem)
                                .toList()
                ))
                .sorted(Comparator.comparingInt(ScheduleInfo::dayOfWeek))
                .toList();
    }

    public List<Lesson> buildLessonByScheduleInfo(String groupId, List<ScheduleInfo> scheduleInfo) {
        return scheduleInfo.stream().flatMap(info -> info.dayScheduleDetail().stream().map(detail ->
                new Lesson(
                        detail.lessonName(),
                        detail.lessonType(),
                        detail.teacherId(),
                        detail.roomId(),
                        groupId,
                        info.dayOfWeek(),
                        false,
                        detail.startTime(),
                        detail.endTime()
                )
        )).toList();
    }

    private ScheduleItem mapLessonToScheduleItem(Lesson lesson) {
        return new ScheduleItem(
                lesson.name(),
                lesson.type(),
                lesson.roomId(),
                lesson.teacherId(),
                lesson.startTime(),
                lesson.endTime()
        );
    }
}
