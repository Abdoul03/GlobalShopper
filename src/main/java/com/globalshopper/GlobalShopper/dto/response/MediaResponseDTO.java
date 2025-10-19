package com.globalshopper.GlobalShopper.dto.response;

public record MediaResponseDTO(
        long id,
        String fileName,
        String fileType,
        String filePath
) {
}
