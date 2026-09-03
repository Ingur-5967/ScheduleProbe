package ru.solomka.study.schedule.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.solomka.study.schedule.api.model.security.UserRole;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleUserDetail implements UserDetails {

    private UUID id;
    private String username;
    private String password;

    private UserRole role;
    private Instant createdAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String authorityName = "ROLE_" + role.name();
        return List.of(new SimpleGrantedAuthority(authorityName));
    }
}
