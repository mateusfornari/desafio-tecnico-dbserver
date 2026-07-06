package br.dev.fornarilabs.voting_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateAgendaDTO(

        @NotBlank(message = "The title is required.")
        @Size(max = 255, min = 3, message = "The title must have min 3 and max 255 characters.")
        String title,

        @NotBlank(message = "The description is required.")
        @Size(max = 3000, min = 10, message = "The description must have min 10 and max 3000 characters.")
        String description
) {
}
