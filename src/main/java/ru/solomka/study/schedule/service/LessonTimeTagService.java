package ru.solomka.study.schedule.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.solomka.study.schedule.api.model.lesson.LessonTimeTag;
import ru.solomka.study.schedule.api.repository.LessonTimeTagRepository;
import ru.solomka.study.schedule.exception.BadRequestClientException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LessonTimeTagService {

    LessonTimeTagRepository lessonTimeTagRepository;

    public LessonTimeTagService(LessonTimeTagRepository lessonTimeTagRepository) {
        this.lessonTimeTagRepository = lessonTimeTagRepository;
    }

    @Transactional
    public List<LessonTimeTag> assignLessonTimeTags(List<LessonTimeTag> timeTags) {
        if(timeTags.isEmpty())
            return Collections.emptyList();

        int notUniqueTags = timeTags.stream().map(LessonTimeTag::id).collect(Collectors.toSet()).size();

        if(notUniqueTags != timeTags.size())
            throw new BadRequestClientException("One element has 2 tags or more");

        return lessonTimeTagRepository.createAll(timeTags);
    }

    public int deleteAllExpiredLessonTimeTags() {
        return lessonTimeTagRepository.deleteAllExpiredLessonTimeTags();
    }
}
