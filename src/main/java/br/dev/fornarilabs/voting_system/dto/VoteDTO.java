package br.dev.fornarilabs.voting_system.dto;

import br.dev.fornarilabs.voting_system.domain.Vote;
import br.dev.fornarilabs.voting_system.domain.VoteChoice;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.OffsetDateTime;

public record VoteDTO(
        VotingSessionDTO session,
        AssociateDTO associate,
        VoteChoice choice,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
        OffsetDateTime votingTime
) {
    public VoteDTO(Vote vote){
        this(new VotingSessionDTO(vote.getSession()), new AssociateDTO(vote.getAssociate()), vote.getChoice(), vote.getVotingTime());
    }
}
