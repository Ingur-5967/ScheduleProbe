package ru.solomka.study.schedule.api.repository;

import ru.solomka.study.schedule.api.model.Lesson;

import java.util.List;

public interface LessonRepository {

    List<Lesson> findAllLessonByGroupId(Long groupId);

    List<String> findAllRoomIdByGroupId(Long groupId);

    List<String> findAllTeacherIdByGroupId(Long groupId);
}