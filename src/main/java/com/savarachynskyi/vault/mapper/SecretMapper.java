package com.savarachynskyi.vault.mapper;

import com.savarachynskyi.vault.dto.response.SecretResponse;
import com.savarachynskyi.vault.entity.SecretMetadata;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SecretMapper {

    @Mapping(target = "id", source = "metadata.id")
    @Mapping(target = "createdAt", source = "metadata.createdAt")
    @Mapping(target = "expiresAt", source = "metadata.expiresAt")
    @Mapping(target = "content", source = "content")
    SecretResponse toResponse(SecretMetadata metadata, String content);
}