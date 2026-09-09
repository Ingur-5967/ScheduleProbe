package ru.solomka.study.schedule.common.jpa.adapter;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.solomka.study.schedule.api.model.lesson.LessonTimeTag;
import ru.solomka.study.schedule.api.repository.LessonTimeTagRepository;
import ru.solomka.study.schedule.model.LessonTimeTagJpaEntity;
import ru.solomka.study.schedule.model.mapper.Mapper;
import ru.solomka.study.schedule.repository.LessonTimeTagJpaRepository;
import ru.solomka.study.schedule.common.jpa.BaseJpaRepositoryAdapter;

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
    @Transactional
    public int deleteAllExpiredLessonTimeTags() {
        return lessonTimeTagJpaRepository.deleteAllExpiredLessonTimeTags();
    }
}
