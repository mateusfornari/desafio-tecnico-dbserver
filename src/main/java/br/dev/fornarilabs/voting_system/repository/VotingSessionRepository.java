package br.dev.fornarilabs.voting_system.repository;

import br.dev.fornarilabs.voting_system.domain.VotingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.Optional;

public interface VotingSessionRepository extends JpaRepository<VotingSession, Long> {

    @Query("""
        SELECT vs FROM VotingSession vs
        WHERE vs.agenda.id = :agendaId
        AND :currentTime BETWEEN vs.startTime AND vs.endTime""")
    Optional<VotingSession> findActiveSession(
            @Param("agendaId") Long agendaId,
            @Param("currentTime") OffsetDateTime currentTime
    );
}
