package br.dev.fornarilabs.voting_system.mock;

import br.dev.fornarilabs.voting_system.domain.*;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class EntityMockCreator {

    public static Agenda createAgendaMock(){
        Agenda agenda = new Agenda();
        agenda.setId(1L);
        agenda.setTitle("Unit Test Agenda");
        agenda.setDescription("Unit Test Agenda description.");
        return agenda;
    }

    public static Associate createAssociateMock(){
        Associate associate = new Associate();
        associate.setId(1L);
        associate.setName("Unit Test Associate");
        associate.setCpf("12345678901");
        return associate;
    }

    public static VotingSession createVotingSessionMock(){
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        VotingSession votingSession = new VotingSession();
        votingSession.setId(1L);
        votingSession.setAgenda(createAgendaMock());
        votingSession.setStartTime(now);
        votingSession.setEndTime(now.plusMinutes(1L));
        return votingSession;
    }

    public static Vote createVoteMock(){
        Associate associate = createAssociateMock();
        VotingSession votingSession = createVotingSessionMock();
        Vote vote = new Vote();
        vote.setId(new VoteId(votingSession.getAgenda().getId(), associate.getId()));
        vote.setAssociate(associate);
        vote.setSession(votingSession);
        vote.setChoice(VoteChoice.YES);
        return vote;
    }
}
