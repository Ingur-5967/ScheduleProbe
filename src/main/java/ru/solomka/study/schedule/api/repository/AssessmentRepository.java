package ru.solomka.study.schedule.api.repository;

import ru.solomka.study.schedule.api.model.assessment.Assessment;
import ru.solomka.study.schedule.api.model.assessment.AssessmentType;
import ru.solomka.study.schedule.repository.base.BaseRepository;

import java.util.List;

public interface AssessmentRepository extends BaseRepository<Assessment, Long> {

    List<Assessment> findAllAssessmentByGroupId(String groupId, List<AssessmentType> types);
}
