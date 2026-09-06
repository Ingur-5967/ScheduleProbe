package ru.solomka.study.schedule.service.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.solomka.study.schedule.api.model.lesson.Lesson;
import ru.solomka.study.schedule.api.model.lesson.ScheduleInfo;
import ru.solomka.study.schedule.api.model.lesson.ScheduleItem;
import ru.solomka.study.schedule.api.repository.LessonRepository;
import ru.solomka.study.schedule.exception.BadRequestClientException;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ScheduleHelper {

    private final LessonRepository lessonRepository;

    /**
     * Проверяем на пересечение по времени объекты между друг другом
     *
     * @param lessons Список занятий
     */
    public void validateNoTimeOverlap(List<Lesson> lessons) {
        Map<Integer, List<Lesson>> lessonsByDay = lessons.stream()
                .collect(Collectors.groupingBy(Lesson::dayOfWeek));

        for (Map.Entry<Integer, List<Lesson>> entry : lessonsByDay.entrySet()) {
            List<Lesson> dayLessons = entry.getValue();

            for (int i = 0; i < dayLessons.size(); i++) {
                for (int j = i + 1; j < dayLessons.size(); j++) {
                    Lesson a = dayLessons.get(i);
                    Lesson b = dayLessons.get(j);

                    if (this.isTimeOverlap(a, b)) {
                        throw new BadRequestClientException(
                                String.format("Time overlap on day %d between '%s' (%s-%s) and '%s' (%s-%s)",
                                        a.dayOfWeek(),
                                        a.name(), a.startTime(), a.endTime(),
                                        b.name(), b.startTime(), b.endTime())
                        );
                    }
                }
            }
        }
    }

    /**
     * Проверяем на пересечение по времени объекты с фронта и объекты бд
     *
     * @param groupId Айдишник группы
     * @param incomingLessons Объекты занятий с фронта
     * @param daysBeingReplaced Дни, которые необходимо полностью заменить
     */
    public void validateNoTimeOverlapWithDatabase(String groupId,
                                                   List<Lesson> incomingLessons,
                                                   Set<Integer> daysBeingReplaced) {
        if (incomingLessons.isEmpty()) return;

        List<Integer> daysToCheck = incomingLessons.stream()
                .map(Lesson::dayOfWeek)
                .distinct()
                .filter(day -> !daysBeingReplaced.contains(day))
                .toList();

        if (daysToCheck.isEmpty()) return;

        List<Lesson> existingLessons = lessonRepository.findByGroupIdAndDayOfWeekIn(groupId, daysToCheck);

        Set<Long> incomingIds = incomingLessons.stream()
                .map(Lesson::id)
                .collect(Collectors.toSet());

        List<Lesson> otherDbLessons = existingLessons.stream()
                .filter(dbLesson -> !incomingIds.contains(dbLesson.id()))
                .toList();

        if (otherDbLessons.isEmpty()) return;

        for (Lesson incoming : incomingLessons) {
            if (daysBeingReplaced.contains(incoming.dayOfWeek())) continue;

            for (Lesson existing : otherDbLessons) {
                if (!incoming.dayOfWeek().equals(existing.dayOfWeek())) continue;

                if (this.isTimeOverlap(incoming, existing)) {
                    throw new BadRequestClientException(
                            String.format("Time overlap on day %d: incoming '%s' (%s-%s) conflicts with existing '%s' (%s-%s)",
                                    incoming.dayOfWeek(),
                                    incoming.name(), incoming.startTime(), incoming.endTime(),
                                    existing.name(), existing.startTime(), existing.endTime())
                    );
                }
            }
        }
    }

    public List<ScheduleInfo> buildScheduleInfo(List<Lesson> lessons) {
        if (lessons == null || lessons.isEmpty())
            return Collections.emptyList();

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
        return scheduleInfo.stream().flatMap(info -> info.dayScheduleDetail().stream().map(detail -> {
            if (info.dayOfWeek() > 7)
                throw new BadRequestClientException("The day of the week is greater than 7");

            return new Lesson(
                    detail.id(),
                    detail.lessonName(),
                    detail.lessonType(),
                    detail.teacherId(),
                    detail.roomId(),
                    groupId,
                    info.dayOfWeek(),
                    false,
                    null,
                    detail.startTime(),
                    detail.endTime()
            );
        })).toList();
    }

    private boolean isTimeOverlap(Lesson a, Lesson b) {
        return a.startTime().isBefore(b.endTime())
                && b.startTime().isBefore(a.endTime());
    }

    private ScheduleItem mapLessonToScheduleItem(Lesson lesson) {
        return new ScheduleItem(
                lesson.id(),
                lesson.name(),
                lesson.type(),
                lesson.roomId(),
                lesson.teacherId(),
                lesson.tag(),
                lesson.startTime(),
                lesson.endTime()
        );
    }
}
