package ru.solomka.study.schedule.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.solomka.study.schedule.api.model.lesson.Lesson;
import ru.solomka.study.schedule.api.repository.LessonRepository;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LessonService {

    LessonRepository lessonRepository;

    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public List<Lesson> updateAll(List<Lesson> lessons) {
        return lessonRepository.updateAll(lessons);
    }

    public List<Lesson> findByGroupIdAndDayOfWeekIn(String groupId, List<Integer> daysOfWeek) {
        return lessonRepository.findByGroupIdAndDayOfWeekIn(groupId, daysOfWeek);
    }

    public void deleteLessonsInDaysOfWeekExcludingIds(String groupId, List<Integer> days, List<Long> excludeIds) {
        lessonRepository.deleteLessonsInDaysOfWeekExcludingIds(groupId, days, excludeIds);
    }

    public boolean containsTeacherInSchedule(String groupId, Long teacherId) {
        return lessonRepository.containsTeacherInSchedule(groupId, teacherId);
    }

    public List<Lesson> findAllByTeacherIdAndGroupId(Long teacherId, String groupId) {
        return lessonRepository.findAllByTeacherIdAndGroupId(teacherId, groupId);
    }

    public List<Lesson> findAllLessonByGroupId(String groupId) {
        return lessonRepository.findAllLessonByGroupId(groupId);
    }

    public List<String> findAllRoomIdByGroupId(String groupId) {
        return lessonRepository.findAllRoomIdByGroupId(groupId);
    }

    public List<String> findAllTeacherIdByGroupId(String groupId) {
        return lessonRepository.findAllTeacherIdByGroupId(groupId);
    }
}
