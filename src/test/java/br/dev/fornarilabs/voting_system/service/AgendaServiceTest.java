package br.dev.fornarilabs.voting_system.service;

import br.dev.fornarilabs.voting_system.domain.Agenda;
import br.dev.fornarilabs.voting_system.domain.VoteChoice;
import br.dev.fornarilabs.voting_system.mock.EntityMockCreator;
import br.dev.fornarilabs.voting_system.repository.AgendaRepository;
import br.dev.fornarilabs.voting_system.service.exceptions.AgendaNotFound;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AgendaServiceTest {

    @Mock
    private AgendaRepository agendaRepository;

    @InjectMocks
    private AgendaService agendaService;

    @Test
    @DisplayName("Must save an agenda successfully.")
    void mustSaveAgendaSuccessfully(){
        Agenda agenda = EntityMockCreator.createAgendaMock();
        when(agendaRepository.save(any(Agenda.class))).thenReturn(agenda);
        Agenda createdAgenda = agendaService.save(agenda);
        assertNotNull(createdAgenda);
    }

    @Test
    @DisplayName("Must return an agenda.")
    void mustReturnAnAgenda(){
        Agenda agenda = EntityMockCreator.createAgendaMock();
        when(agendaRepository.findById(1L)).thenReturn(Optional.of(agenda));
        Agenda foundAgenda = agendaService.findById(1L);
        assertNotNull(foundAgenda);
        assertEquals(1L, foundAgenda.getId());
    }

    @Test
    @DisplayName("Must throw AgendaNotFound exception.")
    void mustThrowAgendaNotFound(){
        when(agendaRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(AgendaNotFound.class, () -> {
            agendaService.findById(1L);
        });
    }

    @Test
    @DisplayName("Must increment votes count successfully.")
    void mustIncrementVotesCountSuccessfully(){
        Agenda agenda = EntityMockCreator.createAgendaMock();
        when(agendaRepository.incrementYesVotes(1L)).thenReturn(1);
        agendaService.incrementVotesCount(agenda, VoteChoice.YES);
    }

    @Test
    @DisplayName("Must thow AgendaNotFound when increment votes count.")
    void mustThrowAgendaNotFoundIncrementVotesCount(){
        Agenda agenda = EntityMockCreator.createAgendaMock();
        when(agendaRepository.incrementYesVotes(1L)).thenReturn(0);
        assertThrows(AgendaNotFound.class, () -> {
            agendaService.incrementVotesCount(agenda, VoteChoice.YES);
        });
    }

}
