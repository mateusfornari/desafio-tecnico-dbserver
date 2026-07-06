package br.dev.fornarilabs.voting_system.dto;

import br.dev.fornarilabs.voting_system.domain.VoteChoice;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RegisterVoteDTO(
        @NotNull(message = "The associate ID is required.")
        @Positive(message = "The associate ID must be a positive integer.")
        Long associateId,

        @NotNull(message = "The vote choice is required.")
        VoteChoice choice
) {
}
