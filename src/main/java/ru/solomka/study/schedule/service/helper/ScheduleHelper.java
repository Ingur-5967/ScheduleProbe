package ru.solomka.study.schedule.service.helper;

import org.springframework.stereotype.Component;
import ru.solomka.study.schedule.api.model.lesson.Lesson;
import ru.solomka.study.schedule.api.model.ScheduleInfo;
import ru.solomka.study.schedule.api.model.ScheduleItem;

import java.util.List;

@Component
public class ScheduleHelper {

    public List<ScheduleInfo> buildScheduleInfo(List<Lesson> lessons) {
        List<ScheduleItem> scheduleItems = this.buildScheduleItem(lessons);
        return lessons.stream()
                .map(lesson -> new ScheduleInfo(lesson.dayOfWeek(), scheduleItems))
                .toList();
    }

    public List<Lesson> buildLessonByScheduleInfo(String groupId, List<ScheduleInfo> scheduleInfo) {
        return scheduleInfo.stream().flatMap(info -> info.dayScheduleDetail().stream().map(detail ->
                new Lesson(
                        detail.id(),
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

    private List<ScheduleItem> buildScheduleItem(List<Lesson> lessons) {
        return lessons.stream().map(lesson -> new ScheduleItem(
                lesson.id(),
                lesson.name(),
                lesson.type(),
                lesson.roomId(),
                lesson.teacherId(),
                lesson.startTime(),
                lesson.endTime()
        )).toList();
    }
}
