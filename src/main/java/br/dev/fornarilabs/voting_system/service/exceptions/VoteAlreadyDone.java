package br.dev.fornarilabs.voting_system.service.exceptions;

public class VoteAlreadyDone extends VoteException{
    public VoteAlreadyDone(String message) {
        super(message);
    }
}
