package ru.solomka.study.schedule.controller.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.study.schedule.api.model.ScheduleInfo;

import java.util.List;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScheduleEditRequest {

    List<ScheduleInfo> scheduleInfo;
}
