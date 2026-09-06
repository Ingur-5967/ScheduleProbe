package ru.solomka.study.schedule.common.jpa.adapter;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;
import ru.solomka.study.schedule.api.model.user.User;
import ru.solomka.study.schedule.api.repository.UserRepository;
import ru.solomka.study.schedule.exception.UserNotFoundException;
import ru.solomka.study.schedule.model.UserJpaEntity;
import ru.solomka.study.schedule.model.mapper.Mapper;
import ru.solomka.study.schedule.repository.UserJpaRepository;
import ru.solomka.study.schedule.common.jpa.BaseJpaRepositoryAdapter;

import java.util.Optional;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserBaseJpaRepositoryAdapter extends BaseJpaRepositoryAdapter<User, UserJpaEntity, Long>
        implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    private final Mapper<User, UserJpaEntity> mapper;

    public UserBaseJpaRepositoryAdapter(UserJpaRepository userJpaRepository,
                                        Mapper<User, UserJpaEntity> mapper) {
        super(userJpaRepository, mapper);
        this.userJpaRepository = userJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public User getByUsername(String username) {
        return this.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with username '%s' not found".formatted(username)));
    }

    @Override
    public User getById(Long id) {
        return this.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id '%s' not found".formatted(id)));
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
