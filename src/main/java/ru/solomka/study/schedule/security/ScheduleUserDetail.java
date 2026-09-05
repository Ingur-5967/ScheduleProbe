package ru.solomka.study.schedule.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.solomka.study.schedule.api.model.security.UserRole;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleUserDetail implements UserDetails {

    private Long id;
    private String username;
    private UserRole role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String authorityName = "ROLE_" + role.name();
        return List.of(new SimpleGrantedAuthority(authorityName));
    }

    @Override
    public @Nullable String getPassword() {
        return null;
    }
}
