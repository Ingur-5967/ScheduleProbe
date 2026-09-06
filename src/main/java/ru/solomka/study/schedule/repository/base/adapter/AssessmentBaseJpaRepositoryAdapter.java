package ru.solomka.study.schedule.repository.base.adapter;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;
import ru.solomka.study.schedule.api.model.assessment.Assessment;
import ru.solomka.study.schedule.api.model.assessment.AssessmentType;
import ru.solomka.study.schedule.api.repository.AssessmentRepository;
import ru.solomka.study.schedule.model.AssessmentJpaEntity;
import ru.solomka.study.schedule.model.mapper.Mapper;
import ru.solomka.study.schedule.repository.AssessmentJpaRepository;
import ru.solomka.study.schedule.repository.base.BaseJpaRepositoryAdapter;

import java.util.List;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AssessmentBaseJpaRepositoryAdapter extends BaseJpaRepositoryAdapter<Assessment, AssessmentJpaEntity, Long>
        implements AssessmentRepository {

    AssessmentJpaRepository assessmentJpaRepository;
    Mapper<Assessment, AssessmentJpaEntity> mapper;

    public AssessmentBaseJpaRepositoryAdapter(AssessmentJpaRepository assessmentJpaRepository,
                                              Mapper<Assessment, AssessmentJpaEntity> mapper) {
        super(assessmentJpaRepository, mapper);
        this.assessmentJpaRepository = assessmentJpaRepository;
        this.mapper = mapper;
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
