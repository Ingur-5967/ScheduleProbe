package ru.solomka.study.schedule.common.jpa.adapter;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;
import ru.solomka.study.schedule.api.model.lesson.Lesson;
import ru.solomka.study.schedule.api.repository.LessonRepository;
import ru.solomka.study.schedule.exception.BadRequestClientException;
import ru.solomka.study.schedule.model.LessonJpaEntity;
import ru.solomka.study.schedule.model.mapper.Mapper;
import ru.solomka.study.schedule.repository.LessonJpaRepository;
import ru.solomka.study.schedule.common.jpa.BaseJpaRepositoryAdapter;

import java.util.List;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LessonBaseJpaRepositoryAdapter extends BaseJpaRepositoryAdapter<Lesson, LessonJpaEntity, Long> implements LessonRepository {

    LessonJpaRepository lessonJpaRepository;
    Mapper<Lesson, LessonJpaEntity> mapper;

    public LessonBaseJpaRepositoryAdapter(LessonJpaRepository lessonJpaRepository,
                                          Mapper<Lesson, LessonJpaEntity> mapper) {
        super(lessonJpaRepository, mapper);
        this.lessonJpaRepository = lessonJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Lesson> updateAll(List<Lesson> lessons) {
        if(!lessons.stream().allMatch(lesson -> lesson.id() != null && lessonJpaRepository.existsById(lesson.id())))
            throw new BadRequestClientException("The object lesson for update must contain field 'id'");

        return super.updateAll(lessons);
    }

    @Override
    public List<Lesson> findByGroupIdAndDayOfWeekIn(String groupId, List<Integer> daysOfWeek) {
        return lessonJpaRepository.findByGroupIdAndDayOfWeekIn(groupId, daysOfWeek).stream()
                .map(mapper::mapToDomain)
                .toList();
    }

    @Override
    public void deleteLessonsInDaysOfWeekExcludingIds(String groupId, List<Integer> days, List<Long> excludeIds) {
        lessonJpaRepository.deleteLessonsInDaysOfWeekExcludingIds(groupId, days, excludeIds);
    }

    @Override
    public boolean containsTeacherInSchedule(String groupId, Long teacherId) {
        return lessonJpaRepository.existsByGroupIdAndTeacherId(groupId, teacherId);
    }

    @Override
    public List<Lesson> findAllByTeacherIdAndGroupId(Long teacherId, String groupId) {
        return lessonJpaRepository.findAllByTeacherIdAndGroupId(teacherId, groupId).stream()
                .map(mapper::mapToDomain)
                .toList();
    }

    @Override
    public List<Lesson> findAllLessonByGroupId(String groupId) {
        return lessonJpaRepository.findAllLessonByGroupId(groupId).stream()
                .map(mapper::mapToDomain)
                .toList();
    }

    @Override
    public List<String> findAllRoomIdByGroupId(String groupId) {
        return lessonJpaRepository.findAllRoomIdByGroupId(groupId);
    }

    @Override
    public List<String> findAllTeacherIdByGroupId(String groupId) {
        return lessonJpaRepository.findAllTeacherNameByGroupId(groupId);
    }
}
