package ru.solomka.study.schedule.service.helper;

import org.springframework.stereotype.Component;
import ru.solomka.study.schedule.api.model.Lesson;
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

    private List<ScheduleItem> buildScheduleItem(List<Lesson> lessons) {
        return lessons.stream().map(lesson -> new ScheduleItem(
                lesson.name(),
                lesson.roomId(),
                lesson.teacherName(),
                lesson.startTime(),
                lesson.endTime()
        )).toList();
    }
}
