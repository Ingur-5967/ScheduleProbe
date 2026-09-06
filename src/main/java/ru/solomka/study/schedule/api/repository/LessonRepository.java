package ru.solomka.study.schedule.api.repository;

import ru.solomka.study.schedule.api.model.lesson.Lesson;
import ru.solomka.study.schedule.repository.base.BaseRepository;

import java.util.List;

public interface LessonRepository extends BaseRepository<Lesson, Long> {

    List<Lesson> findByGroupIdAndDayOfWeekIn(String groupId, List<Integer> daysOfWeek);

    List<Lesson> findAllByTeacherIdAndGroupId(Long teacherId, String groupId);

    List<Lesson> findAllLessonByGroupId(String groupId);

    List<String> findAllRoomIdByGroupId(String groupId);

    List<String> findAllTeacherIdByGroupId(String groupId);

    void deleteLessonsInDaysOfWeekExcludingIds(String groupId, List<Integer> days, List<Long> excludeIds);

    boolean containsTeacherInSchedule(String groupId, Long teacherId);
}