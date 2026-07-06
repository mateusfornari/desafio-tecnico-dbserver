package br.dev.fornarilabs.voting_system.service;

import br.dev.fornarilabs.voting_system.domain.Agenda;
import br.dev.fornarilabs.voting_system.domain.VotingSession;
import br.dev.fornarilabs.voting_system.repository.VotingSessionRepository;
import br.dev.fornarilabs.voting_system.service.exceptions.VotingSessionAlreadyOpen;
import br.dev.fornarilabs.voting_system.service.exceptions.VotingSessionNotOpen;
import jakarta.transaction.Transactional;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Setter
@Slf4j
@Service
@Transactional
public class VotingSessionService {

    @Autowired
    private VotingSessionRepository votingSessionRepository;

    @Autowired
    private AgendaService agendaService;

    @Transactional
    public VotingSession openVotingSession(Long agendaId, Long durationMinutes){
        Agenda agenda = agendaService.findById(agendaId);
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        Optional<VotingSession> alreadyOpenSession = votingSessionRepository.findActiveSession(agendaId, now);
        if(alreadyOpenSession.isPresent()){
            log.warn("Trying to open a session, but an open session already exists for agenda {}.", agendaId);
            throw new VotingSessionAlreadyOpen("An open session already exists.");
        }
        if(durationMinutes == null){
            durationMinutes = 1L;
        }
        VotingSession votingSession = new VotingSession();
        votingSession.setAgenda(agenda);
        votingSession.setStartTime(now);
        votingSession.setEndTime(now.plusMinutes(durationMinutes));
        VotingSession createdVotingSession = votingSessionRepository.save(votingSession);
        log.info("Voting session open with ID: {}", createdVotingSession.getId());
        return createdVotingSession;
    }

    public VotingSession getOpenSession(Agenda agenda){
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        Optional<VotingSession> alreadyOpenSession = votingSessionRepository.findActiveSession(agenda.getId(), now);
        if(alreadyOpenSession.isEmpty()){
            log.warn("No open session for agenda ID: {}", agenda.getId());
            throw new VotingSessionNotOpen("No open session found.");
        }
        return alreadyOpenSession.get();
    }
}
