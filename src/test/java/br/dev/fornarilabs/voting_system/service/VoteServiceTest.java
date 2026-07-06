package br.dev.fornarilabs.voting_system.service;

import br.dev.fornarilabs.voting_system.domain.*;
import br.dev.fornarilabs.voting_system.mock.EntityMockCreator;
import br.dev.fornarilabs.voting_system.repository.VoteRepository;
import br.dev.fornarilabs.voting_system.service.exceptions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class VoteServiceTest {

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private AgendaService agendaService;

    @Mock
    private AssociateService associateService;

    @Mock
    private VotingSessionService votingSessionService;

    @Mock
    private CpfClientService cpfClientService;

    @InjectMocks
    private VoteService voteService;

    @Test
    @DisplayName("Must register vote successfully.")
    void mustRegisterVoteSuccessfully(){
        Vote vote = EntityMockCreator.createVoteMock();
        Associate associate = vote.getAssociate();
        VotingSession votingSession = vote.getSession();
        Agenda agenda = votingSession.getAgenda();
        when(cpfClientService.isCpfValid(any(String.class))).thenReturn(true);
        when(associateService.findById(1L)).thenReturn(associate);
        when(agendaService.findById(1L)).thenReturn(agenda);
        when(votingSessionService.getOpenSession(any(Agenda.class))).thenReturn(votingSession);
        when(voteRepository.existsById(any(VoteId.class))).thenReturn(false);
        when(voteRepository.saveAndFlush(any(Vote.class))).thenReturn(vote);
        Vote createdVote = voteService.registerVote(1L, 1L, VoteChoice.YES);
        assertNotNull(createdVote);
    }

    @Test
    @DisplayName("Must throw AssociateNotFound exception.")
    void mustThrowAssociateNotFound(){
        when(associateService.findById(any(Long.class))).thenThrow(AssociateNotFound.class);
        assertThrows(AssociateNotFound.class, () -> {
            voteService.registerVote(1L, 1L, VoteChoice.YES);
        });
    }

    @Test
    @DisplayName("Must throw AgendaNotFound exception.")
    void mustThrowAgendaNotFound(){
        Vote vote = EntityMockCreator.createVoteMock();
        Associate associate = vote.getAssociate();
        when(cpfClientService.isCpfValid(any(String.class))).thenReturn(true);
        when(associateService.findById(1L)).thenReturn(associate);
        when(agendaService.findById(any(Long.class))).thenThrow(AgendaNotFound.class);
        assertThrows(AgendaNotFound.class, () -> {
            voteService.registerVote(1L, 1L, VoteChoice.YES);
        });
    }

    @Test
    @DisplayName("Must throw VotingSessionNotOpen exception.")
    void mustThrowVotingSessionNotOpen(){
        Vote vote = EntityMockCreator.createVoteMock();
        Associate associate = vote.getAssociate();
        VotingSession votingSession = vote.getSession();
        Agenda agenda = votingSession.getAgenda();
        when(cpfClientService.isCpfValid(any(String.class))).thenReturn(true);
        when(associateService.findById(1L)).thenReturn(associate);
        when(agendaService.findById(1L)).thenReturn(agenda);
        when(votingSessionService.getOpenSession(any(Agenda.class))).thenThrow(VotingSessionNotOpen.class);
        assertThrows(VotingSessionNotOpen.class, () -> {
            voteService.registerVote(1L, 1L, VoteChoice.YES);
        });
    }

    @Test
    @DisplayName("Must throw VoteAlreadyDone exception.")
    void mustThrowVoteAlreadyDone(){
        Vote vote = EntityMockCreator.createVoteMock();
        Associate associate = vote.getAssociate();
        VotingSession votingSession = vote.getSession();
        Agenda agenda = votingSession.getAgenda();
        when(cpfClientService.isCpfValid(any(String.class))).thenReturn(true);
        when(associateService.findById(1L)).thenReturn(associate);
        when(agendaService.findById(1L)).thenReturn(agenda);
        when(votingSessionService.getOpenSession(any(Agenda.class))).thenReturn(votingSession);
        when(voteRepository.existsById(any(VoteId.class))).thenReturn(true);
        assertThrows(VoteAlreadyDone.class, () -> {
            voteService.registerVote(1L, 1L, VoteChoice.YES);
        });
    }

    @Test
    @DisplayName("Must throw InvalidCpf exception.")
    void mustThrowInvalidCpf(){
        Vote vote = EntityMockCreator.createVoteMock();
        Associate associate = vote.getAssociate();
        when(cpfClientService.isCpfValid(any(String.class))).thenReturn(false);
        when(associateService.findById(1L)).thenReturn(associate);
        assertThrows(InvalidCpf.class, () -> {
            voteService.registerVote(1L, 1L, VoteChoice.YES);
        });
    }
}
