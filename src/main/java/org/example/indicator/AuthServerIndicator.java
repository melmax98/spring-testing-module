package org.example.indicator;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.net.URL;

@Component
@ConditionalOnEnabledHealthIndicator("auth-status")
public class AuthServerIndicator implements HealthIndicator {

    @Value("${spring.security.oauth2.client.provider.custom-provider.user-info-uri}")
    private String checkUrl;

    @SneakyThrows
    @Override
    public Health health() {
        URL url = new URL(checkUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        try {
            con.getResponseCode();
            return Health.up().withDetail("auth-server-active", true).build();
        } catch (Exception e) {
            return Health.down().withDetail("auth-server-active", false).build();
        }
    }
}
