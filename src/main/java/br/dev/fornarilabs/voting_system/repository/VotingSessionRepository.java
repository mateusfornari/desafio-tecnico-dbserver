package br.dev.fornarilabs.voting_system.repository;

import br.dev.fornarilabs.voting_system.domain.VotingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.Optional;

public interface VotingSessionRepository extends JpaRepository<VotingSession, Long> {

    /**
     * Encontra a sessão que está aberta, ou seja, com a data/hora atual entre a data/hora inicial e a data/hora final.
     * @param agendaId O ID da pauta.
     * @param currentTime A data/hora atual.
     * @return Retorna o Optional da sessão.
     */
    @Query("""
        SELECT vs FROM VotingSession vs
        WHERE vs.agenda.id = :agendaId
        AND :currentTime BETWEEN vs.startTime AND vs.endTime
        ORDER BY vs.startTime ASC LIMIT 1""")
    Optional<VotingSession> findActiveSession(
            @Param("agendaId") Long agendaId,
            @Param("currentTime") OffsetDateTime currentTime
    );
}
