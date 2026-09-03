package ru.solomka.study.schedule.configuration;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class TokenConfiguration {

    @Bean
    SecretKey signKey(TokenConfigurationProperties tokenConfigurationProperties) {
        byte[] keyBytes = Decoders.BASE64URL.decode(tokenConfigurationProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
