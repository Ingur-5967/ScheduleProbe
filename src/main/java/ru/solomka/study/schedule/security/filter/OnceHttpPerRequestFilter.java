package ru.solomka.study.schedule.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.solomka.study.schedule.model.mapper.Mapper;
import ru.solomka.study.schedule.security.AuthenticationProvider;
import ru.solomka.study.schedule.security.ScheduleUserDetail;
import ru.solomka.study.schedule.security.jwt.TokenEntity;
import ru.solomka.study.schedule.security.jwt.TokenExtractor;
import ru.solomka.study.schedule.security.jwt.TokenValidator;

import java.io.IOException;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OnceHttpPerRequestFilter extends OncePerRequestFilter {

    TokenExtractor tokenExtractor;
    TokenValidator tokenValidator;
    AuthenticationProvider authenticationProvider;

    Mapper<TokenEntity, ScheduleUserDetail> mapper;

    public OnceHttpPerRequestFilter(TokenExtractor tokenExtractor, TokenValidator tokenValidator,
                                    AuthenticationProvider authenticationProvider, Mapper<TokenEntity, ScheduleUserDetail> mapper) {
        this.tokenExtractor = tokenExtractor;
        this.tokenValidator = tokenValidator;
        this.authenticationProvider = authenticationProvider;
        this.mapper = mapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7).trim();

        if(!tokenValidator.validateToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        TokenEntity tokenEntity = tokenExtractor.extract(token);
        authenticationProvider.authenticate(mapper.mapToInfra(tokenEntity));

        filterChain.doFilter(request, response);
    }
}
