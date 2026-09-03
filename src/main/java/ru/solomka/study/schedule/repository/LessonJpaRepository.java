package ru.solomka.study.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.solomka.study.schedule.model.LessonJpaEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface LessonJpaRepository extends JpaRepository<LessonJpaRepository, UUID> {

    List<LessonJpaEntity> findAllLessonByGroupId(Long groupId);

    List<String> findAllRoomIdByGroupId(Long groupId);

    List<String> findAllTeacherNameByGroupId(Long groupId);
}
