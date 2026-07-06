package br.dev.fornarilabs.voting_system.controller;


import br.dev.fornarilabs.voting_system.domain.Agenda;
import br.dev.fornarilabs.voting_system.domain.Vote;
import br.dev.fornarilabs.voting_system.domain.VotingSession;
import br.dev.fornarilabs.voting_system.dto.*;
import br.dev.fornarilabs.voting_system.service.AgendaService;
import br.dev.fornarilabs.voting_system.service.VoteService;
import br.dev.fornarilabs.voting_system.service.VotingSessionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/agendas")
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    @Autowired
    private VotingSessionService votingSessionService;

    @Autowired
    private VoteService voteService;

    @PostMapping
    public ResponseEntity<?> createAgenda(@Valid @RequestBody CreateAgendaDTO body){
        log.info("Agenda creation request received.");
        Agenda agenda = new Agenda();
        agenda.setTitle(body.title());
        agenda.setDescription(body.description());
        Agenda createdAgenda = agendaService.save(agenda);
        AgendaDTO responseBody = new AgendaDTO(createdAgenda);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    @GetMapping
    public ResponseEntity<?> listAllAgendas(@PageableDefault(page = 0, size = 20) Pageable pageable){
        log.info("List agendas request received.");
        Page<Agenda> agendaPage = agendaService.listAll(pageable.getPageNumber(), pageable.getPageSize());
        Page<AgendaDTO> responseBody = agendaPage.map(AgendaDTO::new);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAgenda(@PathVariable Long id){
        log.info("Get agenda request received.");
        Agenda agenda = agendaService.findById(id);
        AgendaDTO responseBody = new AgendaDTO(agenda);
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/{agendaId}/open-session")
    public ResponseEntity<?> openSession(@PathVariable Long agendaId, @Valid @RequestBody OpenVotingSessionDTO body){
        log.info("Open voting session request received.");
        VotingSession votingSession = votingSessionService.openVotingSession(agendaId, body.durationMinutes());
        VotingSessionDTO responseBody = new VotingSessionDTO(votingSession);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    @PostMapping("/{agendaId}/vote")
    public ResponseEntity<?> registerVote(@PathVariable Long agendaId, @Valid @RequestBody RegisterVoteDTO body){
        log.info("Register vote request received.");
        Vote vote = voteService.registerVote(body.associateId(), agendaId, body.choice());
        VoteDTO responseBody = new VoteDTO(vote);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }
}
