package br.dev.fornarilabs.voting_system.controller;

import br.dev.fornarilabs.voting_system.domain.Associate;
import br.dev.fornarilabs.voting_system.dto.AssociateDTO;
import br.dev.fornarilabs.voting_system.dto.CreateAssociateDTO;
import br.dev.fornarilabs.voting_system.service.AssociateService;
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
@RequestMapping("/api/v1/associates")
public class AssociateController {

    @Autowired
    private AssociateService associateService;

    @PostMapping
    ResponseEntity<?> createAssociate(@Valid @RequestBody CreateAssociateDTO body){
        log.info("Associate creation request received.");
        Associate associate = new Associate();
        associate.setName(body.name());
        associate.setCpf(body.cpf());
        Associate createdAssociate = associateService.save(associate);
        AssociateDTO responseBody = new AssociateDTO(createdAssociate);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

}
