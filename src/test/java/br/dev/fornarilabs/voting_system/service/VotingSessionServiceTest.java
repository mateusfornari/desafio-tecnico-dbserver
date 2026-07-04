package br.dev.fornarilabs.voting_system.service;

import br.dev.fornarilabs.voting_system.domain.VotingSession;
import br.dev.fornarilabs.voting_system.mock.EntityMockCreator;
import br.dev.fornarilabs.voting_system.repository.VotingSessionRepository;
import br.dev.fornarilabs.voting_system.service.exceptions.AgendaNotFound;
import br.dev.fornarilabs.voting_system.service.exceptions.VotingSessionAlreadyOpen;
import br.dev.fornarilabs.voting_system.service.exceptions.VotingSessionNotOpen;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class VotingSessionServiceTest {

    @Mock
    private VotingSessionRepository votingSessionRepository;

    @Mock
    private AgendaService agendaService;

    @InjectMocks
    private VotingSessionService votingSessionService;


    @Test
    @DisplayName("Must open a new voting session successfully.")
    void mustOpenANewVotingSessionSuccessfully(){
        VotingSession votingSession = EntityMockCreator.createVotingSessionMock();
        when(agendaService.findById(1L)).thenReturn(votingSession.getAgenda());
        when(votingSessionRepository.findActiveSession(any(Long.class), any(OffsetDateTime.class))).thenReturn(Optional.empty());
        when(votingSessionRepository.save(any(VotingSession.class))).thenReturn(votingSession);
        VotingSession createdVotingSession = votingSessionService.openVotingSession(1L, null);
        assertNotNull(createdVotingSession);

        // Verificar se adicionou 1 minuto quando o parâmetro durationMinutes é null.
        assertEquals(1L, ChronoUnit.MINUTES.between(createdVotingSession.getStartTime(), createdVotingSession.getEndTime()));
    }

    @Test
    @DisplayName("Must throw AgendaNotFound exception.")
    void mustThrowAgendaNotFound(){
        when(agendaService.findById(1L)).thenThrow(AgendaNotFound.class);
        assertThrows(AgendaNotFound.class, () -> {
            votingSessionService.openVotingSession(1L, null);
        });
    }

    @Test
    @DisplayName("Must throw VotingSessionAlreadyOpen exception.")
    void mustThrowVotingSessionAlreadyOpen(){
        VotingSession votingSession = EntityMockCreator.createVotingSessionMock();
        when(agendaService.findById(1L)).thenReturn(votingSession.getAgenda());
        when(votingSessionRepository.findActiveSession(any(Long.class), any(OffsetDateTime.class))).thenReturn(Optional.of(votingSession));
        assertThrows(VotingSessionAlreadyOpen.class, () -> {
            votingSessionService.openVotingSession(1L, null);
        });
    }

    @Test
    @DisplayName("Must return an open session.")
    void mustReturnAnOpenSession(){
        VotingSession votingSession = EntityMockCreator.createVotingSessionMock();
        when(votingSessionRepository.findActiveSession(any(Long.class), any(OffsetDateTime.class))).thenReturn(Optional.of(votingSession));
        VotingSession foudVotingSession = votingSessionService.getOpenSession(votingSession.getAgenda());
        assertNotNull(foudVotingSession);
    }

    @Test
    @DisplayName("Must thow VotingSessionNotOpen.")
    void mustThrowVotingSessionNotOpen(){
        VotingSession votingSession = EntityMockCreator.createVotingSessionMock();
        when(votingSessionRepository.findActiveSession(any(Long.class), any(OffsetDateTime.class))).thenReturn(Optional.empty());
        assertThrows(VotingSessionNotOpen.class, () -> {
            votingSessionService.getOpenSession(votingSession.getAgenda());
        });
    }
}
