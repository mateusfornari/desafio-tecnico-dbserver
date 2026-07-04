package br.dev.fornarilabs.voting_system.service.exceptions;

public class VotingSessionAlreadyOpen extends VotingSessionException{
    public VotingSessionAlreadyOpen(String message) {
        super(message);
    }
}
