package br.dev.fornarilabs.voting_system.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateAssociateDTO(

        @NotBlank(message = "The name is required.")
        @Size(max = 255, min = 3, message = "The name must have min 3 and max 255 characters.")
        String name,

        @NotBlank
        @Size(max = 11, min = 11, message = "The cpf must have 11 numeric characters.")
        @Digits(integer = 11, fraction = 0, message = "The cpf must have 11 numeric characters.")
        String cpf
) {
}
