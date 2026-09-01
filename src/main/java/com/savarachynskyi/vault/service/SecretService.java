package com.savarachynskyi.vault.service;

import com.savarachynskyi.vault.dto.request.CreateSecretRequest;
import com.savarachynskyi.vault.dto.response.SecretResponse;
import com.savarachynskyi.vault.entity.SecretMetadata;
import com.savarachynskyi.vault.mapper.SecretMapper;
import com.savarachynskyi.vault.repository.SecretMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SecretService {

    private final SecretMetadataRepository metadataRepository;
    private final RedisSecretService redisSecretService;
    private final SecretMapper secretMapper;

    @Transactional
    public SecretResponse createSecret(CreateSecretRequest request) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresAt = now.plusSeconds(request.ttlSeconds());

        SecretMetadata metadata = SecretMetadata.builder()
                .createdAt(now)
                .expiresAt(expiresAt)
                .isRead(false)
                .build();

        SecretMetadata savedMetadata = metadataRepository.save(metadata);

        redisSecretService.saveSecret(
                savedMetadata.getId().toString(),
                request.content(),
                request.ttlSeconds()
        );

        return secretMapper.toResponse(savedMetadata, request.content());
    }

    @Transactional
    public SecretResponse getAndBurnSecret(UUID id) {
        SecretMetadata metadata = metadataRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Secret not found"));

        if (metadata.isRead() || metadata.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new NoSuchElementException("Secret has expired or has already been read");
        }

        String content = redisSecretService.getAndDeleteSecret(id.toString());

        if (content == null) {
            throw new NoSuchElementException("Secret content expired or unavailable");
        }

        metadata.setRead(true);
        metadataRepository.save(metadata);

        return secretMapper.toResponse(metadata, content);
    }
}