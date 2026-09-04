package ru.solomka.study.schedule.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {

    @Override
    public void authenticate(ScheduleUserDetail userDetail) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetail,
                null,
                List.of(new SimpleGrantedAuthority("ROLE_" + userDetail.getRole().name()))
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public ScheduleUserDetail getCurrentAuthenticatedUser() {
        return (ScheduleUserDetail) SecurityContextHolder.getContext().getAuthentication();
    }
}
