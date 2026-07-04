package br.dev.fornarilabs.voting_system.service;

import br.dev.fornarilabs.voting_system.domain.*;
import br.dev.fornarilabs.voting_system.repository.VoteRepository;
import br.dev.fornarilabs.voting_system.service.exceptions.VoteAlreadyDone;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private AgendaService agendaService;

    @Autowired
    private AssociateService associateService;

    @Autowired
    private VotingSessionService votingSessionService;

    @Transactional
    public Vote registerVote(Long associateId, Long agendaId, VoteChoice choice){
        Associate associate = associateService.findById(associateId);
        Agenda agenda = agendaService.findById(agendaId);
        VotingSession votingSession = votingSessionService.getOpenSession(agenda);
        VoteId voteId = new VoteId(agenda.getId(), associate.getId());
        Vote vote = new Vote();
        vote.setId(voteId);
        vote.setAssociate(associate);
        vote.setSession(votingSession);
        vote.setChoice(choice);
        if(voteRepository.existsById(voteId)){
            log.warn("Associate {} already voted in agenda {}.", associate.getId(), agenda.getId());
            throw new VoteAlreadyDone("Associate already voted.");
        }
        Vote createdVote = voteRepository.save(vote);
        agendaService.incrementVotesCount(agenda, choice);
        log.info("Associate {} voted in agenda {} successfully.", associate.getId(), agenda.getId());
        return createdVote;
    }

}
