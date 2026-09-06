package ru.solomka.study.schedule.repository.base.adapter;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;
import ru.solomka.study.schedule.api.model.lesson.LessonTimeTag;
import ru.solomka.study.schedule.api.repository.LessonTimeTagRepository;
import ru.solomka.study.schedule.model.LessonTimeTagJpaEntity;
import ru.solomka.study.schedule.model.mapper.Mapper;
import ru.solomka.study.schedule.repository.LessonTimeTagJpaRepository;
import ru.solomka.study.schedule.repository.base.BaseJpaRepositoryAdapter;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LessonTimeTagBaseJpaRepositoryAdapter extends BaseJpaRepositoryAdapter<LessonTimeTag, LessonTimeTagJpaEntity, Long>
        implements LessonTimeTagRepository {

    LessonTimeTagJpaRepository lessonTimeTagJpaRepository;

    public LessonTimeTagBaseJpaRepositoryAdapter(LessonTimeTagJpaRepository lessonTimeTagJpaRepository,
                                                 Mapper<LessonTimeTag, LessonTimeTagJpaEntity> mapper) {
        super(lessonTimeTagJpaRepository, mapper);
        this.lessonTimeTagJpaRepository = lessonTimeTagJpaRepository;
    }

    @Override
    public int deleteAllExpiredLessonTimeTags() {
        return lessonTimeTagJpaRepository.deleteAllExpiredLessonTimeTags();
    }
}
