package ru.solomka.study.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.solomka.study.schedule.model.LessonJpaEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface LessonJpaRepository extends JpaRepository<LessonJpaEntity, UUID> {

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM LessonJpaEntity e WHERE e.dayOfWeek IN :days")
    void deleteLessonsInDaysOfWeek(@Param("days") List<Integer> days);

    List<LessonJpaEntity> findAllByTeacherIdAndGroupId(Long teacherId, String groupId);

    boolean existsByGroupIdAndTeacherId(String groupId, Long teacherId);

    List<LessonJpaEntity> findAllLessonByGroupId(String groupId);

    List<String> findAllRoomIdByGroupId(String groupId);

    List<String> findAllTeacherNameByGroupId(String groupId);
}
