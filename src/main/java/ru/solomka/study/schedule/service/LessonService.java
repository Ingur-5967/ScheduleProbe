package ru.solomka.study.schedule.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.solomka.study.schedule.api.model.Lesson;
import ru.solomka.study.schedule.api.repository.LessonRepository;
import ru.solomka.study.schedule.model.LessonJpaEntity;
import ru.solomka.study.schedule.model.mapper.Mapper;
import ru.solomka.study.schedule.repository.LessonJpaRepository;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LessonService implements LessonRepository {

    LessonJpaRepository lessonJpaRepository;
    Mapper<Lesson, LessonJpaEntity> mapper;

    public LessonService(LessonJpaRepository lessonJpaRepository, Mapper<Lesson, LessonJpaEntity> mapper) {
        this.lessonJpaRepository = lessonJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Lesson create(Lesson lesson) {
        LessonJpaEntity lessonJpaEntity = mapper.mapToInfra(lesson);
        return mapper.mapToDomain(lessonJpaRepository.save(lessonJpaEntity));
    }

    @Override
    public List<Lesson> createAll(List<Lesson> lessons) {
        List<LessonJpaEntity> lessonJpaEntities = lessons.stream().map(mapper::mapToInfra).toList();
        return lessonJpaRepository.saveAll(lessonJpaEntities).stream()
                .map(mapper::mapToDomain)
                .toList();
    }

    @Override
    public void deleteLessonsInDaysOfWeek(List<Integer> days) {
        lessonJpaRepository.deleteLessonsInDaysOfWeek(days);
    }

    @Override
    public List<Lesson> findAllLessonByGroupId(Long groupId) {
        return lessonJpaRepository.findAllLessonByGroupId(groupId).stream()
                .map(mapper::mapToDomain)
                .toList();
    }

    @Override
    public List<String> findAllRoomIdByGroupId(Long groupId) {
        return lessonJpaRepository.findAllRoomIdByGroupId(groupId);
    }

    @Override
    public List<String> findAllTeacherIdByGroupId(Long groupId) {
        return lessonJpaRepository.findAllTeacherNameByGroupId(groupId);
    }
}
