package ru.solomka.study.schedule.api.model.assessment;

import lombok.NonNull;
import ru.solomka.study.schedule.common.Identifiable;

import java.time.Instant;

public record Assessment(@NonNull Long id, @NonNull Long subjectId, @NonNull Long teacherId,
                         @NonNull AssessmentType type, @NonNull String groupId, @NonNull String roomId,
                         @NonNull Instant startTime, @NonNull Instant endTime) implements Identifiable<Long> {
    @Override
    public Long getId() {
        return this.id;
    }
}
