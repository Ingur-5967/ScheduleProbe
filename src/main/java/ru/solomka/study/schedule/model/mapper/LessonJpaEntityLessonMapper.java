package ru.solomka.study.schedule.model.mapper;

import org.springframework.stereotype.Component;
import ru.solomka.study.schedule.api.model.lesson.Lesson;
import ru.solomka.study.schedule.model.LessonJpaEntity;

@Component
public class LessonJpaEntityLessonMapper implements Mapper<Lesson, LessonJpaEntity> {

    @Override
    public Lesson mapToDomain(LessonJpaEntity infra) {
        return new Lesson(
                infra.getName(),
                infra.getType(),
                infra.getTeacherId(),
                infra.getRoomId(),
                infra.getGroupId(),
                infra.getDayOfWeek(),
                infra.isUpperWeek(),
                infra.getStartTime(),
                infra.getEndTime()
        );
    }

    @Override
    public LessonJpaEntity mapToInfra(Lesson domain) {
        return LessonJpaEntity.builder()
                .name(domain.name())
                .type(domain.type())
                .teacherId(domain.teacherId())
                .roomId(domain.roomId())
                .groupId(domain.groupId())
                .dayOfWeek(domain.dayOfWeek())
                .isUpperWeek(domain.isUpperWeek())
                .startTime(domain.startTime())
                .endTime(domain.endTime())
                .build();
    }
}
