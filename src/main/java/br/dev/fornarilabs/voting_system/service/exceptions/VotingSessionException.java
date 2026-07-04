package br.dev.fornarilabs.voting_system.service.exceptions;

public class VotingSessionException extends RuntimeException{
    public VotingSessionException(String message){
        super(message);
    }
}
