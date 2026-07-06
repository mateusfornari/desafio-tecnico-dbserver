package br.dev.fornarilabs.voting_system.dto;

import br.dev.fornarilabs.voting_system.domain.VotingSession;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.OffsetDateTime;

public record VotingSessionDTO(
        Long id,
        AgendaDTO agenda,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
        OffsetDateTime startTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
        OffsetDateTime endTime
) {
    public VotingSessionDTO(VotingSession votingSession){
        this(votingSession.getId(), new AgendaDTO(votingSession.getAgenda()), votingSession.getStartTime(), votingSession.getEndTime());
    }
}
