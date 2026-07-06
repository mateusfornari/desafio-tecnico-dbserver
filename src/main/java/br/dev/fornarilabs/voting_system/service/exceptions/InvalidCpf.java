package br.dev.fornarilabs.voting_system.service.exceptions;

public class InvalidCpf extends RuntimeException {
    public InvalidCpf(String message) {
        super(message);
    }
}
