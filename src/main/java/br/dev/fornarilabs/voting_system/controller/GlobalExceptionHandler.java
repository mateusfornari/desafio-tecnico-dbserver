package br.dev.fornarilabs.voting_system.controller;

import br.dev.fornarilabs.voting_system.dto.BadRequestDTO;
import br.dev.fornarilabs.voting_system.dto.ErrorResponseDTO;
import br.dev.fornarilabs.voting_system.dto.FieldErrorDTO;
import br.dev.fornarilabs.voting_system.service.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BadRequestDTO> handleException(MethodArgumentNotValidException e){
        log.error(e.getMessage());
        List<FieldErrorDTO> errors = new ArrayList<>();
        e.getBindingResult().getFieldErrors().forEach(error ->
                errors.add(new FieldErrorDTO(error.getField(), error.getDefaultMessage()))
        );
        BadRequestDTO error = new BadRequestDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Bad request.",
                errors,
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleException(HttpMessageNotReadableException e){
        log.error(e.getMessage());
        return generateBadRequestResponse("Invalid request body.");
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleException(NoResourceFoundException e){
        log.error(e.getMessage());
        return generateNotFoundResponse("Resource not found.");
    }

    @ExceptionHandler(AgendaNotFound.class)
    public ResponseEntity<ErrorResponseDTO> handleException(AgendaNotFound e){
        log.warn(e.getMessage());
        return generateNotFoundResponse("The agenda was not found.");
    }

    @ExceptionHandler(AssociateNotFound.class)
    public ResponseEntity<ErrorResponseDTO> handleException(AssociateNotFound e){
        log.warn(e.getMessage());
        return generateNotFoundResponse("The associate was not found.");
    }

    @ExceptionHandler(AssociateAlreadyExists.class)
    public ResponseEntity<ErrorResponseDTO> handleException(AssociateAlreadyExists e){
        log.warn(e.getMessage());
        return generateUnprocessableResponse("Associate already exists with this CPF.");
    }

    @ExceptionHandler(VotingSessionAlreadyOpen.class)
    public ResponseEntity<ErrorResponseDTO> handleException(VotingSessionAlreadyOpen e){
        log.warn(e.getMessage());
        return generateUnprocessableResponse("There is another session open.");
    }

    @ExceptionHandler(VotingSessionNotOpen.class)
    public ResponseEntity<ErrorResponseDTO> handleException(VotingSessionNotOpen e){
        log.warn(e.getMessage());
        return generateUnprocessableResponse("The voting session is closed.");
    }

    @ExceptionHandler(VoteAlreadyDone.class)
    public ResponseEntity<ErrorResponseDTO> handleException(VoteAlreadyDone e){
        log.warn(e.getMessage());
        return generateUnprocessableResponse("The associate already voted in this agenda.");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handleException(MethodArgumentTypeMismatchException e){
        log.error(e.getMessage());
        return generateBadRequestResponse("The '%s' param is malformed.".formatted(e.getName()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException(Exception e){
        log.error(e.getMessage());
        e.printStackTrace();
        return generateResponse("Unexpected error.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponseDTO> generateUnprocessableResponse(String message){
        return generateResponse(message, HttpStatus.UNPROCESSABLE_CONTENT);
    }

    private ResponseEntity<ErrorResponseDTO> generateNotFoundResponse(String message){
        return generateResponse(message, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ErrorResponseDTO> generateBadRequestResponse(String message){
        return generateResponse(message, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponseDTO> generateResponse(String message, HttpStatus status){
        ErrorResponseDTO error = new ErrorResponseDTO(
                status.value(),
                message,
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(error, status);
    }
}
