package ru.solomka.study.schedule.job;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.solomka.study.schedule.service.LessonTimeTagService;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TimeTagsCleanerJob {

    LessonTimeTagService lessonTimeTagService;

    public TimeTagsCleanerJob(LessonTimeTagService lessonTimeTagService) {
        this.lessonTimeTagService = lessonTimeTagService;
    }

    @Scheduled(cron = "0 */15 * * * *")
    public void clearTimeTags() {
        lessonTimeTagService.deleteAllExpiredLessonTimeTags();
    }
}
