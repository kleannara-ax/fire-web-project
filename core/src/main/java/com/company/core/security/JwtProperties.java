package com.company.core.security;

import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Component
@Validated
@ConfigurationProperties(prefix = "security.jwt")
public class JwtProperties {

    @NotBlank
    private String secret;

    private long expirationMs = 3_600_000L;

    @PostConstruct
    void validateSecret() {
        if (secret == null || secret.trim().length() < 32) {
            throw new IllegalStateException("security.jwt.secret must be set and at least 32 characters long");
        }
    }
}
