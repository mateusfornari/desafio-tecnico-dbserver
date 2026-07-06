package br.dev.fornarilabs.voting_system.controller;

import br.dev.fornarilabs.voting_system.domain.*;
import br.dev.fornarilabs.voting_system.dto.CreateAgendaDTO;
import br.dev.fornarilabs.voting_system.dto.RegisterVoteDTO;
import br.dev.fornarilabs.voting_system.mock.EntityMockCreator;
import br.dev.fornarilabs.voting_system.service.AgendaService;
import br.dev.fornarilabs.voting_system.service.VoteService;
import br.dev.fornarilabs.voting_system.service.VotingSessionService;
import br.dev.fornarilabs.voting_system.service.exceptions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AgendaController.class)
public class AgendaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AgendaService agendaService;

    @MockitoBean
    private VotingSessionService votingSessionService;

    @MockitoBean
    private VoteService voteService;

    private static final String BASE_PATH = "/api/v1/agendas";

    @Test
    @DisplayName("Must return 201 and created agenda data.")
    void mustReturn201AndCreatedAgendaData() throws Exception {
        Agenda agenda = EntityMockCreator.createAgendaMock();
        String request = createRequest(agenda);

        when(agendaService.save(any(Agenda.class))).thenReturn(agenda);

        mockMvc.perform(post(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(agenda.getId()))
                .andExpect(jsonPath("$.title").value(agenda.getTitle()))
                .andExpect(jsonPath("$.description").value(agenda.getDescription()))
                .andExpect(jsonPath("$.votesCountYes").value(agenda.getVotesCountYes()))
                .andExpect(jsonPath("$.votesCountNo").value(agenda.getVotesCountNo()))
                .andExpect(jsonPath("$.totalVotes").value(agenda.getVotesCountYes() + agenda.getVotesCountNo()))
        ;
    }

    @Test
    @DisplayName("Must return 200 and agenda data.")
    void mustReturn200AndAgendaData() throws Exception {
        Agenda agenda = EntityMockCreator.createAgendaMock();

        when(agendaService.findById(1L)).thenReturn(agenda);

        mockMvc.perform(get(BASE_PATH + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(agenda.getId()))
                .andExpect(jsonPath("$.title").value(agenda.getTitle()))
                .andExpect(jsonPath("$.description").value(agenda.getDescription()))
                .andExpect(jsonPath("$.votesCountYes").value(agenda.getVotesCountYes()))
                .andExpect(jsonPath("$.votesCountNo").value(agenda.getVotesCountNo()))
                .andExpect(jsonPath("$.totalVotes").value(agenda.getVotesCountYes() + agenda.getVotesCountNo()))
        ;
    }

    @Test
    @DisplayName("Must return 200 and agenda paginated list.")
    void mustReturn200AndAgendaPaginatedList() throws Exception {
        Agenda agenda = EntityMockCreator.createAgendaMock();
        List<Agenda> agendas = List.of(agenda);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Agenda> pageMock = new PageImpl<>(agendas, pageable, agendas.size());

        when(agendaService.listAll(any(Integer.class), any(Integer.class))).thenReturn(pageMock);

        mockMvc.perform(get(BASE_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].id").value(agenda.getId()))
                .andExpect(jsonPath("$.content[0].title").value(agenda.getTitle()))
                .andExpect(jsonPath("$.content[0].description").value(agenda.getDescription()))
                .andExpect(jsonPath("$.content[0].votesCountYes").value(agenda.getVotesCountYes()))
                .andExpect(jsonPath("$.content[0].votesCountNo").value(agenda.getVotesCountNo()))
                .andExpect(jsonPath("$.content[0].totalVotes").value(agenda.getVotesCountYes() + agenda.getVotesCountNo()))
        ;
    }


    @Test
    @DisplayName("Must return 400 when title is shorter than 3")
    void mustReturn400WhenTitleIsShorterThan3() throws Exception {
        Agenda agenda = EntityMockCreator.createAgendaMock();
        agenda.setTitle("a");
        String request = createRequest(agenda);

        mockMvc.perform(post(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @DisplayName("Must return 400 when title is longer than 255")
    void mustReturn400WhenTitleIsLongerThan255() throws Exception {
        Agenda agenda = EntityMockCreator.createAgendaMock();
        agenda.setTitle("a".repeat(256));
        String request = createRequest(agenda);

        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @DisplayName("Must return 400 when description is shorter than 10")
    void mustReturn400WhenDescriptionIsShorterThan10() throws Exception {
        Agenda agenda = EntityMockCreator.createAgendaMock();
        agenda.setDescription("a");
        String request = createRequest(agenda);

        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @DisplayName("Must return 400 when description is longer than 3000")
    void mustReturn400WhenDescriptionIsLongerThan3000() throws Exception {
        Agenda agenda = EntityMockCreator.createAgendaMock();
        agenda.setDescription("a".repeat(3001));
        String request = createRequest(agenda);

        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @DisplayName("Must return 400 when id is malformed")
    void mustReturn400WhenIdIsMalformed() throws Exception {
        mockMvc.perform(get(BASE_PATH + "/abc"))
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @DisplayName("Must return 404 when agenda is not found")
    void mustReturn404WhenAgendaIsNotFound() throws Exception {
        when(agendaService.findById(any(Long.class))).thenThrow(AgendaNotFound.class);
        mockMvc.perform(get(BASE_PATH + "/123456"))
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    @DisplayName("Must create a voting session and return 201 with data body.")
    void mustCreateAVotingSessionSuccessfully() throws Exception {
        String request = "{\"durationMinutes\":1}";
        VotingSession votingSession = EntityMockCreator.createVotingSessionMock();
        Agenda agenda = votingSession.getAgenda();

        when(agendaService.save(any(Agenda.class))).thenReturn(agenda);
        when(votingSessionService.openVotingSession(any(Long.class), any(Long.class))).thenReturn(votingSession);

        mockMvc.perform(post(BASE_PATH + "/1/open-session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(votingSession.getId()))
                .andExpect(jsonPath("$.agenda.id").value(agenda.getId()))
                .andExpect(jsonPath("$.agenda.title").value(agenda.getTitle()))
                .andExpect(jsonPath("$.agenda.description").value(agenda.getDescription()))
                .andExpect(jsonPath("$.agenda.votesCountYes").value(agenda.getVotesCountYes()))
                .andExpect(jsonPath("$.agenda.votesCountNo").value(agenda.getVotesCountNo()))
                .andExpect(jsonPath("$.agenda.totalVotes").value(agenda.getVotesCountYes() + agenda.getVotesCountNo()))
        ;
    }

    @Test
    @DisplayName("Must return 422 if an open voting session exists.")
    void mustReturn422IfAnOpenSessionExists() throws Exception {
        String request = "{\"durationMinutes\":1}";
        when(votingSessionService.openVotingSession(any(Long.class), any(Long.class))).thenThrow(VotingSessionAlreadyOpen.class);

        mockMvc.perform(post(BASE_PATH + "/1/open-session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isUnprocessableContent())
        ;
    }

    @Test
    @DisplayName("Must register vote and return 201 with vote data.")
    void mustRegisterVoteSuccessfully() throws Exception {
        Vote vote = EntityMockCreator.createVoteMock();
        String request = createVoteRequest(vote);
        VotingSession votingSession = vote.getSession();
        Agenda agenda = votingSession.getAgenda();
        Associate associate = vote.getAssociate();

        when(voteService.registerVote(any(Long.class), any(Long.class), any(VoteChoice.class))).thenReturn(vote);

        mockMvc.perform(post(BASE_PATH + "/1/vote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.session.id").value(votingSession.getId()))
                .andExpect(jsonPath("$.session.agenda.id").value(agenda.getId()))
                .andExpect(jsonPath("$.associate.id").value(associate.getId()))
                .andExpect(jsonPath("$.choice").value(vote.getChoice().name()))
        ;
    }

    @Test
    @DisplayName("Must return 422 if there is no open session.")
    void mustReturn422IfThereIsNoOpenSession() throws Exception {
        Vote vote = EntityMockCreator.createVoteMock();
        String request = createVoteRequest(vote);

        when(voteService.registerVote(any(Long.class), any(Long.class), any(VoteChoice.class))).thenThrow(VotingSessionNotOpen.class);

        mockMvc.perform(post(BASE_PATH + "/1/vote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isUnprocessableContent())
        ;
    }

    @Test
    @DisplayName("Must return 422 if associate already voted.")
    void mustReturn422IfAssociateAlreadyVoted() throws Exception {
        Vote vote = EntityMockCreator.createVoteMock();
        String request = createVoteRequest(vote);

        when(voteService.registerVote(any(Long.class), any(Long.class), any(VoteChoice.class))).thenThrow(VoteAlreadyDone.class);

        mockMvc.perform(post(BASE_PATH + "/1/vote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isUnprocessableContent())
        ;
    }

    @Test
    @DisplayName("Must return 404 if agenda is not found.")
    void mustReturn404IfAgendaIsNotFound() throws Exception {
        Vote vote = EntityMockCreator.createVoteMock();
        String request = createVoteRequest(vote);

        when(voteService.registerVote(any(Long.class), any(Long.class), any(VoteChoice.class))).thenThrow(AgendaNotFound.class);

        mockMvc.perform(post(BASE_PATH + "/1/vote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    @DisplayName("Must return 404 if associate is not found.")
    void mustReturn404IfAssociateIsNotFound() throws Exception {
        Vote vote = EntityMockCreator.createVoteMock();
        String request = createVoteRequest(vote);

        when(voteService.registerVote(any(Long.class), any(Long.class), any(VoteChoice.class))).thenThrow(AssociateNotFound.class);

        mockMvc.perform(post(BASE_PATH + "/1/vote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isNotFound())
        ;
    }

    private String createRequest(Agenda agenda) {
        CreateAgendaDTO request = new CreateAgendaDTO(agenda.getTitle(), agenda.getDescription());
        return objectMapper.writeValueAsString(request);
    }

    private String createVoteRequest(Vote vote) {
        RegisterVoteDTO request = new RegisterVoteDTO(vote.getAssociate().getId(), vote.getChoice());
        return objectMapper.writeValueAsString(request);
    }

}
