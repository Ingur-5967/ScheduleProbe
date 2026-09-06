package ru.solomka.study.schedule.job;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.solomka.study.schedule.service.LessonTimeTagService;

import java.time.LocalTime;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TimeTagsCleanerJob {

    LessonTimeTagService lessonTimeTagService;
    Logger log = LoggerFactory.getLogger(TimeTagsCleanerJob.class);

    public TimeTagsCleanerJob(LessonTimeTagService lessonTimeTagService) {
        this.lessonTimeTagService = lessonTimeTagService;
    }

    @Scheduled(cron = "0 */15 * * * *")
    public void clearTimeTags() {
        log.debug("Cleaner tags job started at {}", LocalTime.now());
        int deletedExpiredTags = lessonTimeTagService.deleteAllExpiredLessonTimeTags();
        log.debug("Cleaner tag job completed at {}. Count processed tags {}", LocalTime.now(), deletedExpiredTags);
    }
}
