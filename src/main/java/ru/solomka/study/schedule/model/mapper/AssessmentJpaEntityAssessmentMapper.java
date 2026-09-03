package ru.solomka.study.schedule.model.mapper;

import org.springframework.stereotype.Component;
import ru.solomka.study.schedule.api.model.Assessment;
import ru.solomka.study.schedule.model.AssessmentJpaEntity;

@Component
public class AssessmentJpaEntityAssessmentMapper implements Mapper<Assessment, AssessmentJpaEntity> {

    @Override
    public Assessment mapToDomain(AssessmentJpaEntity infra) {
        return new Assessment(
                infra.getSubjectId(),
                infra.getTeacherId(),
                infra.getType(),
                infra.getGroupId(),
                infra.getRoomId(),
                infra.getStartTime(),
                infra.getEndTime()
        );
    }

    @Override
    public AssessmentJpaEntity mapToInfra(Assessment domain) {
        return AssessmentJpaEntity.builder()
                .subjectId(domain.getSubjectId())
                .teacherId(domain.getTeacherId())
                .type(domain.getType())
                .groupId(domain.getGroupId())
                .roomId(domain.getRoomId())
                .startTime(domain.getStartTime())
                .endTime(domain.getEndTime())
                .build();
    }
}
