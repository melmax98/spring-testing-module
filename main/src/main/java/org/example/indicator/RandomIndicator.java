package org.example.indicator;

import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@ConditionalOnEnabledHealthIndicator("random")
public class RandomIndicator implements HealthIndicator {
    @Override
    public Health health() {
        boolean healthy = new Random().nextBoolean();
        if (healthy) {
            return Health.up().build();
        }
        return Health.down().build();
    }
}
