package br.dev.fornarilabs.voting_system.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "tb_vote")
@EntityListeners(AuditingEntityListener.class)
public class Vote implements Persistable<VoteId> {

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

    // Forçar o INSERT ao chamar o save() do Repository.
    // Isso é para garantir que o banco de dados não faça UPDATE e evite que o associado vote mais de uma vez em condições de concorrência.
    @Override
    public boolean isNew() {
        return true;
    }
}
