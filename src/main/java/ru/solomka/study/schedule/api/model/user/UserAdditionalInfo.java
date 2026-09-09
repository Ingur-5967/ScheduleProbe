package ru.solomka.study.schedule.api.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NonNull;
import ru.solomka.study.schedule.common.Identifiable;

public record UserAdditionalInfo(@JsonIgnore @NonNull Long id, @NonNull String fullName, @NonNull String cathedra, String groupId,
                                 Integer studyPeriod, LevelEducation levelEducation) implements Identifiable<Long> {
    @Override
    public Long getId() {
        return this.id;
    }
}