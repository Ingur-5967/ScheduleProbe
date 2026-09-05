package ru.solomka.study.schedule.model.mapper;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.solomka.study.schedule.api.model.UserAdditionalInfo;
import ru.solomka.study.schedule.api.model.security.User;
import ru.solomka.study.schedule.model.UserAdditionalInfoJpaEntity;
import ru.solomka.study.schedule.model.UserJpaEntity;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserJpaEntityUserMapper implements Mapper<User, UserJpaEntity> {

    Mapper<UserAdditionalInfo, UserAdditionalInfoJpaEntity> mapper;

    public UserJpaEntityUserMapper(Mapper<UserAdditionalInfo, UserAdditionalInfoJpaEntity> mapper) {
        this.mapper = mapper;
    }

    @Override
    public User mapToDomain(UserJpaEntity infra) {
        return new User(
                infra.getId(),
                infra.getUsername(),
                infra.getPasswordHash(),
                infra.getAdditionalInfo() == null ? null : mapper.mapToDomain(infra.getAdditionalInfo()),
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
                .additionalInfo(domain.additionalInfo() == null ? null : mapper.mapToInfra(domain.additionalInfo()))
                .role(domain.role())
                .createdAt(domain.createdAt())
                .build();
    }
}
