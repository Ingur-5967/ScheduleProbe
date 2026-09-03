package ru.solomka.study.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.solomka.study.schedule.api.model.Assessment;
import ru.solomka.study.schedule.api.model.AssessmentType;
import ru.solomka.study.schedule.model.AssessmentJpaEntity;

import java.util.List;

@Repository
public interface AssessmentJpaRepository extends JpaRepository<AssessmentJpaEntity, Assessment> {

    List<AssessmentJpaEntity> findAllByGroupIdAndTypeIn(String groupId, List<AssessmentType> types);
}
