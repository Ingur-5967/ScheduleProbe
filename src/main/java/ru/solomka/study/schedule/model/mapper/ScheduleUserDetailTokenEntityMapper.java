package ru.solomka.study.schedule.model.mapper;

import org.springframework.stereotype.Component;
import ru.solomka.study.schedule.security.ScheduleUserDetail;
import ru.solomka.study.schedule.security.jwt.TokenEntity;

@Component
public class ScheduleUserDetailTokenEntityMapper implements Mapper<TokenEntity, ScheduleUserDetail> {

    @Override
    public TokenEntity mapToDomain(ScheduleUserDetail infra) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ScheduleUserDetail mapToInfra(TokenEntity domain) {
        return new ScheduleUserDetail(
                domain.id(),
                domain.username(),
                domain.role()
        );
    }
}
