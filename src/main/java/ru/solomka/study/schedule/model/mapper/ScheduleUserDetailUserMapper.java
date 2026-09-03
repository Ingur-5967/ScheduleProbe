package ru.solomka.study.schedule.model.mapper;

import org.springframework.stereotype.Component;
import ru.solomka.study.schedule.api.model.security.User;
import ru.solomka.study.schedule.security.ScheduleUserDetail;

@Component
public class ScheduleUserDetailUserMapper implements Mapper<User, ScheduleUserDetail> {

    @Override
    public User mapToDomain(ScheduleUserDetail infra) {
        assert infra.getPassword() != null;
        return new User(
                infra.getId(),
                infra.getUsername(),
                infra.getPassword(),
                infra.getRole(),
                infra.getCreatedAt()
        );
    }

    @Override
    public ScheduleUserDetail mapToInfra(User domain) {
        return ScheduleUserDetail.builder()
                .id(domain.id())
                .username(domain.username())
                .password(domain.passwordHash())
                .role(domain.role())
                .createdAt(domain.createdAt())
                .build();
    }
}
