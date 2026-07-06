package br.dev.fornarilabs.voting_system.service.exceptions;

public class CpfApiError extends RuntimeException {
    public CpfApiError(String message) {
        super(message);
    }
}
