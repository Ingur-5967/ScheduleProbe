package ru.solomka.study.schedule.common.jpa.adapter;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.solomka.study.schedule.api.model.security.RefreshToken;
import ru.solomka.study.schedule.api.repository.RefreshTokenRepository;
import ru.solomka.study.schedule.model.RefreshTokenJpaEntity;
import ru.solomka.study.schedule.model.mapper.Mapper;
import ru.solomka.study.schedule.repository.RefreshTokenJpaRepository;
import ru.solomka.study.schedule.common.jpa.BaseJpaRepositoryAdapter;

import java.util.UUID;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RefreshTokenBaseJpaRepositoryAdapter extends BaseJpaRepositoryAdapter<RefreshToken, RefreshTokenJpaEntity, UUID> implements RefreshTokenRepository {

    RefreshTokenJpaRepository refreshTokenJpaRepository;

    public RefreshTokenBaseJpaRepositoryAdapter(RefreshTokenJpaRepository refreshTokenJpaRepository,
                                                Mapper<RefreshToken, RefreshTokenJpaEntity> mapper) {
        super(refreshTokenJpaRepository, mapper);
        this.refreshTokenJpaRepository = refreshTokenJpaRepository;
    }

    @Override
    @Transactional
    public void deleteTokenByUserId(Long userId) {
        refreshTokenJpaRepository.deleteTokenByUserId(userId);
    }

    @Override
    public boolean existsByUserId(Long userId) {
        return refreshTokenJpaRepository.existsByUserId(userId);
    }

    @Override
    public boolean existsById(UUID id) {
        return refreshTokenJpaRepository.existsById(id);
    }
}
