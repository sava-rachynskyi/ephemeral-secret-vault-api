package com.savarachynskyi.vault.repository;

import com.savarachynskyi.vault.entity.SecretMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SecretMetadataRepository extends JpaRepository<SecretMetadata, UUID> {
}