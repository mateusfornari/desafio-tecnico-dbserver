package br.dev.fornarilabs.voting_system.repository;

import br.dev.fornarilabs.voting_system.domain.Agenda;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AgendaRepositoryTest extends BaseRepositoryTest{

    @Autowired
    private AgendaRepository agendaRepository;

    @Test
    @DisplayName("Must save the agenda successfully.")
    void mustSaveAgendaSuccessfully(){
        Agenda createdAgenda = createAgenda();
        assertNotNull(createdAgenda);
        assertNotNull(createdAgenda.getCreationTime());

        Optional<Agenda> found = agendaRepository.findById(createdAgenda.getId());
        assertTrue(found.isPresent());
        assertEquals(createdAgenda.getTitle(), found.get().getTitle());
        assertEquals(0L, found.get().getVotesCountYes());
        assertEquals(0L, found.get().getVotesCountNo());
    }

    @Test
    @DisplayName("Must increment votes in database.")
    void mustIncrementVotesInDatabase(){
        Agenda agenda = createAgenda();
        int result = agendaRepository.incrementYesVotes(agenda.getId());
        assertEquals(1, result);
        Optional<Agenda> found = agendaRepository.findById(agenda.getId());
        assertTrue(found.isPresent());
        assertEquals(1L, found.get().getVotesCountYes());
        assertEquals(0L, found.get().getVotesCountNo());

        result = agendaRepository.incrementYesVotes(agenda.getId());
        assertEquals(1, result);
        found = agendaRepository.findById(agenda.getId());
        assertTrue(found.isPresent());
        assertEquals(2L, found.get().getVotesCountYes());
        assertEquals(0L, found.get().getVotesCountNo());

        result = agendaRepository.incrementNoVotes(agenda.getId());
        assertEquals(1, result);
        found = agendaRepository.findById(agenda.getId());
        assertTrue(found.isPresent());
        assertEquals(2L, found.get().getVotesCountYes());
        assertEquals(1L, found.get().getVotesCountNo());
    }

    @Test
    @DisplayName("Must return 0 if agenda not exists.")
    void mustReturn0IfAgendaNotExists(){
        int result = agendaRepository.incrementYesVotes(123456L);
        assertEquals(0, result);
    }

    private Agenda createAgenda(){
        Agenda agenda = new Agenda();
        agenda.setTitle("Unit Test Agenda");
        agenda.setDescription("Unit Test Agenda long description.");
        return agendaRepository.save(agenda);
    }
}
