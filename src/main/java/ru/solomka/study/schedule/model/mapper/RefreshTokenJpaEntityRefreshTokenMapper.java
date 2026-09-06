package ru.solomka.study.schedule.model.mapper;

import org.springframework.stereotype.Component;
import ru.solomka.study.schedule.api.model.security.RefreshToken;
import ru.solomka.study.schedule.model.RefreshTokenJpaEntity;

@Component
public class RefreshTokenJpaEntityRefreshTokenMapper implements Mapper<RefreshToken, RefreshTokenJpaEntity> {

    @Override
    public RefreshToken mapToDomain(RefreshTokenJpaEntity infra) {
        return new RefreshToken(
                infra.getId(),
                infra.getUserId(),
                infra.getToken(),
                infra.getExpiredAt()
        );
    }

    @Override
    public RefreshTokenJpaEntity mapToInfra(RefreshToken domain) {
       return RefreshTokenJpaEntity.builder()
               .id(domain.id())
               .userId(domain.userId())
               .token(domain.token())
               .expiredAt(domain.expiredAt())
               .build();
    }
}
