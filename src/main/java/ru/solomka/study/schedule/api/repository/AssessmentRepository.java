package ru.solomka.study.schedule.api.repository;

import ru.solomka.study.schedule.api.model.Assessment;
import ru.solomka.study.schedule.api.model.AssessmentType;

import java.util.List;

public interface AssessmentRepository {

    List<Assessment> findAllAssessmentByGroupId(String groupId, List<AssessmentType> types);
}
