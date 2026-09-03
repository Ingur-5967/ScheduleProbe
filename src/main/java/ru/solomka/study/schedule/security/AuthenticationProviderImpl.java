package ru.solomka.study.schedule.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.solomka.study.schedule.security.jwt.TokenEntity;

import java.util.List;

@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {

    @Override
    public void authenticate(TokenEntity token) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                token,
                null,
                List.of(new SimpleGrantedAuthority("ROLE_" + token.role().name()))
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public ScheduleUserDetail getCurrentAuthenticatedUser() {
        return (ScheduleUserDetail) SecurityContextHolder.getContext().getAuthentication();
    }
}
