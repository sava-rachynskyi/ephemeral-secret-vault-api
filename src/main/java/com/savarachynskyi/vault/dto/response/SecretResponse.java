package com.savarachynskyi.vault.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record SecretResponse(
        UUID id,
        String content,
        LocalDateTime createdAt,
        LocalDateTime expiresAt
) {}