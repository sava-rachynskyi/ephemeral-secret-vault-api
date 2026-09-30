package com.savarachynskyi.vault;

import com.savarachynskyi.vault.dto.request.CreateSecretRequest;
import com.savarachynskyi.vault.dto.response.SecretResponse;
import com.savarachynskyi.vault.exception.SecretNotFoundException;
import com.savarachynskyi.vault.service.SecretService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
class SecretIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @Container
    @ServiceConnection(name = "redis")
    static GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:7-alpine"))
            .withExposedPorts(6379);

    @Autowired
    private SecretService secretService;

    @Test
    @DisplayName("Should save secret and burn on first read")
    void shouldCreateAndBurnSecret() {
        CreateSecretRequest request = new CreateSecretRequest("my-top-secret-payload", 60L);
        SecretResponse created = secretService.createSecret(request);

        assertNotNull(created.id());
        assertEquals("my-top-secret-payload", created.content());

        SecretResponse readFirstTime = secretService.getAndBurnSecret(created.id());
        assertEquals("my-top-secret-payload", readFirstTime.content());

        assertThrows(SecretNotFoundException.class, () ->
                secretService.getAndBurnSecret(created.id())
        );
    }
}