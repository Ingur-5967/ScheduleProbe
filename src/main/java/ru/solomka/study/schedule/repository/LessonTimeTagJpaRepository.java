package ru.solomka.study.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.solomka.study.schedule.model.LessonTimeTagJpaEntity;

@Repository
public interface LessonTimeTagJpaRepository extends JpaRepository<LessonTimeTagJpaEntity, Long> {

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM LessonTimeTagJpaEntity lttje WHERE lttje.expiredAt < CURRENT_TIMESTAMP")
    void deleteAllExpiredLessonTimeTags();
}