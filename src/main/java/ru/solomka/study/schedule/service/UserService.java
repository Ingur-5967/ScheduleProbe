package ru.solomka.study.schedule.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.solomka.study.schedule.api.model.security.User;
import ru.solomka.study.schedule.api.repository.UserRepository;
import ru.solomka.study.schedule.exception.UserNotFoundException;
import ru.solomka.study.schedule.model.UserJpaEntity;
import ru.solomka.study.schedule.model.mapper.Mapper;
import ru.solomka.study.schedule.repository.UserJpaRepository;

import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService implements UserRepository {

    UserJpaRepository userJpaRepository;
    Mapper<User, UserJpaEntity> mapper;

    public UserService(UserJpaRepository userJpaRepository, Mapper<User, UserJpaEntity> mapper) {
        this.userJpaRepository = userJpaRepository;
        this.mapper = mapper;
    }

    public User getByUsername(String username) {
        return this.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with username '%s' not found".formatted(username)));
    }

    public User getById(Long id) {
        return this.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id '%s' not found".formatted(id)));
    }

    public User getEnrichedUserAdditionInfo(User user) {
        User fullUser = this.getByUsername(user.username());
        return new User(
                fullUser.username(),
                fullUser.additionalInfo(),
                fullUser.role(),
                fullUser.createdAt());
    }

    @Override
    public User create(User user) {
       UserJpaEntity userJpaEntity = mapper.mapToInfra(user);
       return mapper.mapToDomain(userJpaRepository.save(userJpaEntity));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername(username)
                .map(mapper::mapToDomain);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id).map(mapper::mapToDomain);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userJpaRepository.existsByUsername(username);
    }
}
