package br.dev.fornarilabs.voting_system.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "tb_vote")
public class Vote {

    @EmbeddedId
    private VoteId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("associateId")
    @JoinColumn(name = "associate_id")
    private Associate associate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id")
    private VotingSession session;

    @Column(name = "vote_choice", nullable = false, length = 1)
    private VoteChoice choice;

    @CreatedDate
    @Column(name = "voting_time", updatable = false)
    private OffsetDateTime votingTime;

}
