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
import ru.solomka.study.schedule.api.model.security.UserRole;

@Configuration
@EnableMethodSecurity
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SecurityGlobalConfiguration {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    HttpSecurity configurationHttpSecurity(HttpSecurity httpSecurity) {
        return httpSecurity.authorizeHttpRequests(request -> request
                .requestMatchers("/admin/**").hasAnyRole(UserRole.DEANERY.name(), UserRole.OPERATOR.name())
                .requestMatchers("/teacher/**").hasAnyRole(UserRole.TEACHER.name())
                .requestMatchers("/view/**").permitAll()
                .anyRequest().authenticated()
        );
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.fromHierarchy(
                "ROLE_DECANT > ROLE_OPERATOR > ROLE_TEACHER > ROLE_STUDENT > ROLE_GHOST"
        );
    }
}
