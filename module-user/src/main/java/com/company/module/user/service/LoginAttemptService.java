package com.company.module.user.service;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginAttemptService {

    private static final int MAX_FAILURES = 5;
    private static final Duration WINDOW = Duration.ofMinutes(10);
    private static final Duration LOCKOUT = Duration.ofMinutes(10);

    private final Map<String, AttemptState> attempts = new ConcurrentHashMap<>();

    public void checkAllowed(String key) {
        AttemptState state = attempts.get(key);
        if (state == null) return;
        Instant now = Instant.now();
        if (state.lockedUntil != null && now.isBefore(state.lockedUntil)) {
            throw new IllegalStateException("LOCKED");
        }
        if (state.lastFailureAt != null && Duration.between(state.lastFailureAt, now).compareTo(WINDOW) > 0) {
            attempts.remove(key);
        }
    }

    public void recordSuccess(String key) {
        attempts.remove(key);
    }

    public void recordFailure(String key) {
        Instant now = Instant.now();
        attempts.compute(key, (k, current) -> {
            AttemptState next = current;
            if (next == null || next.lastFailureAt == null || Duration.between(next.lastFailureAt, now).compareTo(WINDOW) > 0) {
                next = new AttemptState();
                next.failures = 1;
                next.lastFailureAt = now;
                return next;
            }
            next.failures += 1;
            next.lastFailureAt = now;
            if (next.failures >= MAX_FAILURES) {
                next.lockedUntil = now.plus(LOCKOUT);
            }
            return next;
        });
    }

    private static final class AttemptState {
        private int failures;
        private Instant lastFailureAt;
        private Instant lockedUntil;
    }
}
