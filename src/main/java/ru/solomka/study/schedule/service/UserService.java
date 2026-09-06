package ru.solomka.study.schedule.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.solomka.study.schedule.api.model.user.User;
import ru.solomka.study.schedule.api.repository.UserRepository;

import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    public User getById(Long id) {
        return userRepository.getById(id);
    }

    public User create(User user) {
        return userRepository.create(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}