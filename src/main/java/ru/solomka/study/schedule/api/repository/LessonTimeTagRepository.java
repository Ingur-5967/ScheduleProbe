package ru.solomka.study.schedule.api.repository;

import ru.solomka.study.schedule.api.model.lesson.LessonTimeTag;
import ru.solomka.study.schedule.repository.base.BaseRepository;

public interface LessonTimeTagRepository extends BaseRepository<LessonTimeTag, Long> {

    int deleteAllExpiredLessonTimeTags();
}
