package com.savarachynskyi.vault.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateSecretRequest(
        @NotBlank(message = "Secret content cannot be empty")
        String content,

        @NotNull(message = "Expiration time is required")
        @Positive(message = "TTL must be a positive number in seconds")
        Long ttlSeconds
) {}