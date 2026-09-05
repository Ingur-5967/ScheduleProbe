package ru.solomka.study.schedule.model.mapper;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.solomka.study.schedule.api.model.lesson.Lesson;
import ru.solomka.study.schedule.api.model.lesson.LessonTimeTag;
import ru.solomka.study.schedule.model.LessonJpaEntity;
import ru.solomka.study.schedule.model.LessonTimeTagJpaEntity;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LessonJpaEntityLessonMapper implements Mapper<Lesson, LessonJpaEntity> {

    Mapper<LessonTimeTag, LessonTimeTagJpaEntity> mapper;

    public LessonJpaEntityLessonMapper(Mapper<LessonTimeTag, LessonTimeTagJpaEntity> mapper) {
        this.mapper = mapper;
    }

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
                infra.getTag() == null ? null : mapper.mapToDomain(infra.getTag()),
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
                .tag(domain.tag() == null ? null : mapper.mapToInfra(domain.tag()))
                .startTime(domain.startTime())
                .endTime(domain.endTime())
                .build();
    }
}
