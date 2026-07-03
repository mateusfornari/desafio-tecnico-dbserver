package br.dev.fornarilabs.voting_system.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "tb_voting_session")
public class VotingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agenda_id")
    private Agenda agenda;

    @CreatedDate
    @Column(name = "start_time", updatable = false)
    private OffsetDateTime startTime;

    @Column(name = "end_time")
    private OffsetDateTime endTime;

}
