package ru.solomka.study.schedule.api.repository;

import ru.solomka.study.schedule.api.model.lesson.Lesson;

import java.util.List;

public interface LessonRepository {

    Lesson create(Lesson lesson);

    List<Lesson> createAll(List<Lesson> lessons);

    void deleteLessonsInDaysOfWeek(List<Integer> days);

    boolean containsTeacherInSchedule(String groupId, Long teacherId);

    List<Lesson> findAllByTeacherIdAndGroupId(Long teacherId, String groupId);

    List<Lesson> findAllLessonByGroupId(String groupId);

    List<String> findAllRoomIdByGroupId(String groupId);

    List<String> findAllTeacherIdByGroupId(String groupId);
}