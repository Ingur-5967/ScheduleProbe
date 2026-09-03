package ru.solomka.study.schedule.model.mapper;

import org.springframework.stereotype.Component;
import ru.solomka.study.schedule.api.model.security.User;
import ru.solomka.study.schedule.model.UserJpaEntity;

@Component
public class UserJpaEntityUserMapper implements Mapper<User, UserJpaEntity> {

    @Override
    public User mapToDomain(UserJpaEntity infra) {
        return new User(
                infra.getId(),
                infra.getUsername(),
                infra.getPasswordHash(),
                infra.getPassId(),
                infra.getRole(),
                infra.getCreatedAt()
        );
    }

    @Override
    public UserJpaEntity mapToInfra(User domain) {
        return UserJpaEntity.builder()
                .id(domain.id())
                .username(domain.username())
                .passwordHash(domain.passwordHash())
                .passId(domain.passId())
                .role(domain.role())
                .createdAt(domain.createdAt())
                .build();
    }
}
