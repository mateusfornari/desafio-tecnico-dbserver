package br.dev.fornarilabs.voting_system.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "tb_agenda")
@EntityListeners(AuditingEntityListener.class)
public class Agenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @Column(name = "votes_count_yes")
    private Long votesCountYes = 0L;

    @Column(name = "votes_count_no")
    private Long votesCountNo = 0L;

    @CreatedDate
    @Column(name = "creation_time", updatable = false)
    private OffsetDateTime creationTime;
}
