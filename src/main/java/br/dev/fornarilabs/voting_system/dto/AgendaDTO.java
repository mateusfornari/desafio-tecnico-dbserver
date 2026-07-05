package br.dev.fornarilabs.voting_system.dto;

import br.dev.fornarilabs.voting_system.domain.Agenda;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.OffsetDateTime;

public record AgendaDTO(
        Long id,
        String title,
        String description,
        Long votesCountYes,
        Long votesCountNo,
        Long totalVotes,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
        OffsetDateTime creationTime
) {
    public AgendaDTO(Agenda agenda){
        Long votes = agenda.getVotesCountYes() + agenda.getVotesCountNo();
        this(agenda.getId(), agenda.getTitle(), agenda.getDescription(), agenda.getVotesCountYes(), agenda.getVotesCountNo(), votes, agenda.getCreationTime());
    }
}
