package ru.solomka.study.schedule.security;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.solomka.study.schedule.model.UserJpaEntity;
import ru.solomka.study.schedule.repository.UserJpaRepository;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDelegateService implements UserDetailsService {

    UserJpaRepository userJpaRepository;

    public UserDelegateService(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        UserJpaEntity user = userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username '%s' not found".formatted(username)));

        return ScheduleUserDetail.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPasswordHash())
                .createdAt(user.getCreatedAt())
                .role(user.getRole())
                .build();
    }
}
