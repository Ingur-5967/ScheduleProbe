package ru.solomka.study.schedule.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.solomka.study.schedule.api.model.security.RefreshToken;
import ru.solomka.study.schedule.api.repository.RefreshTokenRepository;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RefreshTokenService {

    RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public boolean revokeToken(Long userId) {
        if(!refreshTokenRepository.existsByUserId(userId))
            return true;

        refreshTokenRepository.deleteTokenByUserId(userId);
        return true;
    }

    public void create(RefreshToken refreshToken) {
        refreshTokenRepository.create(refreshToken);
    }
}
