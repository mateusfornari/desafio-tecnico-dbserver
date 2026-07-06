package br.dev.fornarilabs.voting_system.controller.cpf_validator;

import br.dev.fornarilabs.voting_system.dto.cpf_validator.ValidateCpfDTO;
import br.dev.fornarilabs.voting_system.dto.cpf_validator.ValidateCpfResponseDTO;
import br.dev.fornarilabs.voting_system.service.cpf_validator.CpfValidatorService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/cpf-validator/v1")
public class CpfValidatorController {

    @Autowired
    private CpfValidatorService cpfValidatorService;

    @PostMapping
    public ResponseEntity<?> validateCpf(@Valid @RequestBody ValidateCpfDTO body){
        log.info("CPF validation request received.");
        boolean result = cpfValidatorService.isValid(body.cpf());
        if(result){
            return ResponseEntity.ok(new ValidateCpfResponseDTO("ABLE_TO_VOTE"));
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ValidateCpfResponseDTO("UNABLE_TO_VOTE"));
        }
    }
}
