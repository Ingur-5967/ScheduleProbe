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
                .subjectId(domain.subjectId())
                .teacherId(domain.teacherId())
                .type(domain.type())
                .groupId(domain.groupId())
                .roomId(domain.roomId())
                .startTime(domain.startTime())
                .endTime(domain.endTime())
                .build();
    }
}
