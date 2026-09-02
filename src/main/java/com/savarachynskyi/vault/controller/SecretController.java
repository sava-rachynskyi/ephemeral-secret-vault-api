package com.savarachynskyi.vault.controller;

import com.savarachynskyi.vault.dto.request.CreateSecretRequest;
import com.savarachynskyi.vault.dto.response.SecretResponse;
import com.savarachynskyi.vault.service.SecretService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/secrets")
@RequiredArgsConstructor
@Tag(name = "Secret Vault API", description = "Endpoints for creating and retrieving ephemeral secrets")
public class SecretController {

    private final SecretService secretService;

    @PostMapping
    @Operation(summary = "Create an ephemeral secret with a TTL")
    public ResponseEntity<SecretResponse> createSecret(@Valid @RequestBody CreateSecretRequest request) {
        SecretResponse response = secretService.createSecret(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get and burn secret (One-time read)")
    public ResponseEntity<SecretResponse> getAndBurnSecret(@PathVariable UUID id) {
        SecretResponse response = secretService.getAndBurnSecret(id);
        return ResponseEntity.ok(response);
    }
}