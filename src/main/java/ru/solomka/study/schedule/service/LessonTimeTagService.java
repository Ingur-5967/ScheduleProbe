package ru.solomka.study.schedule.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.solomka.study.schedule.api.model.lesson.LessonTimeTag;
import ru.solomka.study.schedule.api.repository.LessonTimeTagRepository;
import ru.solomka.study.schedule.exception.BadRequestClientExceptiom;
import ru.solomka.study.schedule.model.LessonTimeTagJpaEntity;
import ru.solomka.study.schedule.model.mapper.Mapper;
import ru.solomka.study.schedule.repository.LessonTimeTagJpaRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LessonTimeTagService implements LessonTimeTagRepository {

    LessonTimeTagJpaRepository lessonTimeTagJpaRepository;
    Mapper<LessonTimeTag, LessonTimeTagJpaEntity> mapper;

    public LessonTimeTagService(LessonTimeTagJpaRepository lessonTimeTagJpaRepository, Mapper<LessonTimeTag, LessonTimeTagJpaEntity> mapper) {
        this.lessonTimeTagJpaRepository = lessonTimeTagJpaRepository;
        this.mapper = mapper;
    }

    @Transactional
    public List<LessonTimeTag> assignLessonTimeTags(List<LessonTimeTag> timeTags) {
        if(timeTags.isEmpty())
            return Collections.emptyList();

        int notUniqueTags = timeTags.stream().map(LessonTimeTag::id).collect(Collectors.toSet()).size();

        if(notUniqueTags != timeTags.size())
            throw new BadRequestClientExceptiom("One element has 2 tags or more");

        return this.createAll(timeTags);
    }

    @Override
    public LessonTimeTag create(LessonTimeTag lessonTimeTag) {
        LessonTimeTagJpaEntity lessonTimeTagJpaEntity = mapper.mapToInfra(lessonTimeTag);
        return mapper.mapToDomain(lessonTimeTagJpaRepository.save(lessonTimeTagJpaEntity));
    }

    @Override
    public List<LessonTimeTag> createAll(List<LessonTimeTag> lessonTimeTags) {
        List<LessonTimeTagJpaEntity> lessonTimeTagJpaEntities = lessonTimeTags.stream().map(mapper::mapToInfra).toList();
        return lessonTimeTagJpaRepository.saveAll(lessonTimeTagJpaEntities).stream()
                .map(mapper::mapToDomain)
                .toList();
    }

    @Override
    public void deleteAllExpiredLessonTimeTags() {
        lessonTimeTagJpaRepository.deleteAllExpiredLessonTimeTags();
    }
}
