package ru.solomka.study.schedule.configuration;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties(prefix = "jwt")
public class TokenConfigurationProperties {
    String secretKey;
    Duration accessTokenExpiration;
    Duration refreshTokenExpiration;
}
