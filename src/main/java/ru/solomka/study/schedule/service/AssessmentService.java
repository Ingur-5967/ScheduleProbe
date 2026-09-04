package ru.solomka.study.schedule.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.solomka.study.schedule.api.model.Assessment;
import ru.solomka.study.schedule.api.model.AssessmentType;
import ru.solomka.study.schedule.api.repository.AssessmentRepository;
import ru.solomka.study.schedule.model.AssessmentJpaEntity;
import ru.solomka.study.schedule.model.mapper.Mapper;
import ru.solomka.study.schedule.repository.AssessmentJpaRepository;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AssessmentService implements AssessmentRepository {

    AssessmentJpaRepository assessmentJpaRepository;
    Mapper<Assessment, AssessmentJpaEntity> mapper;

    public AssessmentService(AssessmentJpaRepository assessmentJpaRepository,
                             Mapper<Assessment, AssessmentJpaEntity> mapper) {
        this.assessmentJpaRepository = assessmentJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Assessment create(Assessment assessment) {
        AssessmentJpaEntity assessmentJpaEntity = mapper.mapToInfra(assessment);
        return mapper.mapToDomain(assessmentJpaRepository.save(assessmentJpaEntity));
    }

    @Override
    public List<Assessment> createAll(List<Assessment> assessments) {
        List<AssessmentJpaEntity> assessmentJpaEntities = assessments.stream().map(mapper::mapToInfra).toList();
        return assessmentJpaRepository.saveAll(assessmentJpaEntities).stream()
                .map(mapper::mapToDomain)
                .toList();
    }

    @Override
    public List<Assessment> findAllAssessmentByGroupId(String groupId, List<AssessmentType> types) {
        List<AssessmentJpaEntity> assessmentJpaEntities = assessmentJpaRepository.findAllByGroupIdAndTypeIn(
                groupId,
                types.isEmpty() ? List.of(AssessmentType.values()) : types
        );
        return assessmentJpaEntities.stream()
                .map(mapper::mapToDomain)
                .toList();
    }
}
