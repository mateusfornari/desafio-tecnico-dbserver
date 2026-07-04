package br.dev.fornarilabs.voting_system.service.exceptions;

public class VoteException extends RuntimeException{
    public VoteException(String message){
        super(message);
    }
}
