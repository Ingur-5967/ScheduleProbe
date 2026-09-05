package ru.solomka.study.schedule.configuration;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.solomka.study.schedule.api.model.security.UserRole;
import ru.solomka.study.schedule.security.filter.OnceHttpPerRequestFilter;

@Configuration
@EnableMethodSecurity
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SecurityGlobalConfiguration {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain configurationHttpSecurity(HttpSecurity httpSecurity, OnceHttpPerRequestFilter onceHttpPerRequestFilter) {
        return httpSecurity.authorizeHttpRequests(request -> request
                        .requestMatchers("/api/v1/admin/**").hasAnyRole(UserRole.DEANERY.name(), UserRole.OPERATOR.name())
                        .requestMatchers("/api/v1/teacher/**").hasAnyRole(UserRole.TEACHER.name())
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .anyRequest().authenticated()
                ).addFilterBefore(onceHttpPerRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.fromHierarchy(
                "ROLE_DECANT > ROLE_OPERATOR > ROLE_TEACHER > ROLE_STUDENT > ROLE_GHOST"
        );
    }
}
