package ru.solomka.study.schedule.model.mapper;

import org.springframework.stereotype.Component;
import ru.solomka.study.schedule.api.model.lesson.LessonTimeTag;
import ru.solomka.study.schedule.model.LessonTimeTagJpaEntity;

@Component
public class LessonTimeTagJpaEntityLessonTimeTagMapper implements Mapper<LessonTimeTag, LessonTimeTagJpaEntity> {

    @Override
    public LessonTimeTag mapToDomain(LessonTimeTagJpaEntity infra) {
        return new LessonTimeTag(
                infra.getId(),
                infra.getMessage(),
                infra.getExpiredAt()
        );
    }

    @Override
    public LessonTimeTagJpaEntity mapToInfra(LessonTimeTag domain) {
        return LessonTimeTagJpaEntity.builder()
                .id(domain.lessonId())
                .message(domain.message())
                .expiredAt(domain.expiredAt())
                .build();
    }
}
