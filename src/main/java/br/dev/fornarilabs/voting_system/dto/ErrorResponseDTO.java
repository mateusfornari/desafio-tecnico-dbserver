package br.dev.fornarilabs.voting_system.dto;

public record ErrorResponseDTO(
        int status,
        String message,
        long timestamp
) {
}
