package ru.solomka.study.schedule.api.repository;

import ru.solomka.study.schedule.api.model.security.User;

import java.util.Optional;

public interface UserRepository {

    User create(User user);

    Optional<User> findByUsername(String username);

    Optional<User> findById(Long id);

    boolean existsByUsername(String username);

}