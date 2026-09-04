package ru.solomka.study.schedule.security;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.solomka.study.schedule.api.model.security.User;
import ru.solomka.study.schedule.api.repository.UserRepository;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDelegateService implements UserDetailsService {

    UserRepository userRepository;

    public UserDelegateService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username '%s' not found".formatted(username)));

        return ScheduleUserDetail.builder()
                .id(user.id())
                .username(user.username())
                .role(user.role())
                .build();
    }
}
