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
@Table(name = "tb_associate")
@EntityListeners(AuditingEntityListener.class)
public class Associate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cpf;
    private String name;

    @CreatedDate
    @Column(name = "creation_time", updatable = false)
    private OffsetDateTime creationTime;
}
