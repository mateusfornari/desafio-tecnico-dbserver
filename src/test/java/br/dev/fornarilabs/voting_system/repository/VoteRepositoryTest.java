package br.dev.fornarilabs.voting_system.repository;

import br.dev.fornarilabs.voting_system.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class VoteRepositoryTest extends BaseRepositoryTest{

    @Autowired
    private AssociateRepository associateRepository;

    @Autowired
    private AgendaRepository agendaRepository;

    @Autowired
    private VotingSessionRepository votingSessionRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Test
    @DisplayName("Must register the vote successfully")
    void mustRegisterVoteSuccessfully(){
        Vote vote = createVote();
        assertNotNull(vote);
        assertNotNull(vote.getId());
        Optional<Vote> found = voteRepository.findById(vote.getId());
        assertTrue(found.isPresent());
    }

    @Test
    @DisplayName("Vote must exists by ID.")
    void voteMustExistsById(){
        Vote vote = createVote();
        assertNotNull(vote);
        assertNotNull(vote.getId());
        boolean result = voteRepository.existsById(vote.getId());
        assertTrue(result);
    }

    @Test
    @DisplayName("Must fail when trying to vote again.")
    void mustFailWhenTryingToVoteAgain(){
        Vote vote = createVote();
        Vote newVote = new Vote();
        newVote.setId(vote.getId());
        newVote.setSession(vote.getSession());
        newVote.setAssociate(vote.getAssociate());
        newVote.setChoice(VoteChoice.YES);
        assertThrows(DuplicateKeyException.class, () -> {
            voteRepository.saveAndFlush(newVote);
        });
    }

    private Vote createVote(){
        Associate associate = new Associate();
        associate.setName("Unit Test Associate");
        associate.setCpf("12345678902");
        associateRepository.save(associate);

        Agenda agenda = new Agenda();
        agenda.setTitle("Unit Test Agenda");
        agenda.setDescription("Unit Test Agenda long description.");
        agendaRepository.save(agenda);

        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        VotingSession votingSession = new VotingSession();
        votingSession.setAgenda(agenda);
        votingSession.setStartTime(now);
        votingSession.setEndTime(now.plusMinutes(1L));
        votingSessionRepository.save(votingSession);

        Vote vote = new Vote();
        vote.setId(new VoteId(agenda.getId(), associate.getId()));
        vote.setSession(votingSession);
        vote.setAssociate(associate);
        vote.setChoice(VoteChoice.YES);
        return voteRepository.saveAndFlush(vote);
    }
}
