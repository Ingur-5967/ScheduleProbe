package ru.solomka.study.schedule.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NonNull;

public record UserAdditionalInfo(@JsonIgnore @NonNull Long id, @NonNull String fullName, @NonNull String cathedra, String groupId,
                                 Integer studyPeriod, LevelEducation levelEducation) {}