package ru.solomka.study.schedule.api.repository;

import ru.solomka.study.schedule.api.model.user.User;
import ru.solomka.study.schedule.common.jpa.BaseRepository;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Long> {

    User getByUsername(String username);

    User getById(Long id);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

}