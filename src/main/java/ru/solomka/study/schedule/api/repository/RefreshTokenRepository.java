package ru.solomka.study.schedule.api.repository;

import ru.solomka.study.schedule.api.model.security.RefreshToken;
import ru.solomka.study.schedule.common.jpa.BaseRepository;

import java.util.UUID;

public interface RefreshTokenRepository extends BaseRepository<RefreshToken, UUID> {

    void deleteTokenByUserId(Long userId);

    boolean existsByUserId(Long userId);

    boolean existsById(UUID id);
}
