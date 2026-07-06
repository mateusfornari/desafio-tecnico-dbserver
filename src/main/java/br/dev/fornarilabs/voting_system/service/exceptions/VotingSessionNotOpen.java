package br.dev.fornarilabs.voting_system.service.exceptions;

public class VotingSessionNotOpen extends VotingSessionException{
    public VotingSessionNotOpen(String message) {
        super(message);
    }
}
