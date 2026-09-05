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
import ru.solomka.study.schedule.security.jwt.TokenParser;

import java.io.IOException;
import java.util.Optional;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OnceHttpPerRequestFilter extends OncePerRequestFilter {

    TokenParser tokenParser;
    AuthenticationProvider authenticationProvider;

    Mapper<TokenEntity, ScheduleUserDetail> mapper;

    public OnceHttpPerRequestFilter(TokenParser tokenParser, AuthenticationProvider authenticationProvider,
                                    Mapper<TokenEntity, ScheduleUserDetail> mapper) {
        this.tokenParser = tokenParser;
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

        String tokenHeader = authHeader.substring(7).trim();

        Optional<TokenEntity> tokenEntity = tokenParser.parseAndValidateToken(tokenHeader);

        tokenEntity.ifPresentOrElse(token -> {
            authenticationProvider.authenticate(mapper.mapToInfra(token));
            try {
                filterChain.doFilter(request, response);
            } catch (IOException | ServletException e) {
                throw new RuntimeException(e);
            }
        }, () -> {
            try {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
