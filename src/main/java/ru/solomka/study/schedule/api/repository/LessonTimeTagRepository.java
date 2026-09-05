package ru.solomka.study.schedule.api.repository;

import ru.solomka.study.schedule.api.model.lesson.LessonTimeTag;

import java.util.List;

public interface LessonTimeTagRepository {

    LessonTimeTag create(LessonTimeTag LessonTimeTag);

    List<LessonTimeTag> createAll(List<LessonTimeTag> lessonTimeTags);

    void deleteAllExpiredLessonTimeTags();
}
