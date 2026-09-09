package ru.solomka.study.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.solomka.study.schedule.model.LessonJpaEntity;

import java.util.List;

@Repository
public interface LessonJpaRepository extends JpaRepository<LessonJpaEntity, Long> {

    @Modifying
    @Query("DELETE FROM LessonJpaEntity l WHERE l.groupId = :groupId AND l.dayOfWeek IN :days AND l.id NOT IN :excludeIds")
    void deleteLessonsInDaysOfWeekExcludingIds(@Param("groupId") String groupId,
                                               @Param("days") List<Integer> days,
                                               @Param("excludeIds") List<Long> excludeIds);

    boolean existsByGroupIdAndTeacherId(String groupId, Long teacherId);

    List<LessonJpaEntity> findAllByTeacherIdAndGroupId(Long teacherId, String groupId);

    List<LessonJpaEntity> findAllLessonByGroupId(String groupId);

    List<String> findAllRoomIdByGroupId(String groupId);

    List<String> findAllTeacherNameByGroupId(String groupId);

    List<LessonJpaEntity> findByGroupIdAndDayOfWeekIn(String groupId, List<Integer> daysOfWeek);
}

