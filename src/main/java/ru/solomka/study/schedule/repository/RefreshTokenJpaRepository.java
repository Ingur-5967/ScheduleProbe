package ru.solomka.study.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import ru.solomka.study.schedule.model.RefreshTokenJpaEntity;

import java.util.UUID;

@Repository
public interface RefreshTokenJpaRepository extends JpaRepository<RefreshTokenJpaEntity, UUID> {

    @Modifying
    void deleteTokenByUserId(Long id);

    boolean existsByUserId(Long userId);
}
