package br.dev.fornarilabs.voting_system.repository;

import br.dev.fornarilabs.voting_system.domain.Agenda;
import br.dev.fornarilabs.voting_system.domain.VotingSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class VotingSessionRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private AgendaRepository agendaRepository;

    @Autowired
    private VotingSessionRepository votingSessionRepository;

    @Test
    @DisplayName("Must save the voting session successfully.")
    void mustSaveVotingSessionSuccessfully(){
        VotingSession votingSession = createVotingSession();
        assertNotNull(votingSession);

        Optional<VotingSession> found = votingSessionRepository.findById(votingSession.getId());
        assertTrue(found.isPresent());
    }

    @Test
    @DisplayName("Must find an open session.")
    void mustFindAnOpenSession(){
        VotingSession votingSession = createVotingSession();
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        Optional<VotingSession> found = votingSessionRepository.findActiveSession(votingSession.getAgenda().getId(), now);
        assertTrue(found.isPresent());
    }

    private VotingSession createVotingSession(){
        Agenda agenda = new Agenda();
        agenda.setTitle("Unit Test Agenda");
        agenda.setDescription("Unit Test Agenda long description.");
        agendaRepository.save(agenda);

        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        VotingSession votingSession = new VotingSession();
        votingSession.setAgenda(agenda);
        votingSession.setStartTime(now);
        votingSession.setEndTime(now.plusMinutes(1L));
        return votingSessionRepository.save(votingSession);
    }
}
