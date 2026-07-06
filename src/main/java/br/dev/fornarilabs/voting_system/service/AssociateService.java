package br.dev.fornarilabs.voting_system.service;

import br.dev.fornarilabs.voting_system.domain.Associate;
import br.dev.fornarilabs.voting_system.repository.AssociateRepository;
import br.dev.fornarilabs.voting_system.service.exceptions.AssociateAlreadyExists;
import br.dev.fornarilabs.voting_system.service.exceptions.AssociateNotFound;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@Transactional
public class AssociateService {

    @Autowired
    private AssociateRepository associateRepository;

    @Transactional
    public Associate save(Associate associate){
        if(associateRepository.existsByCpf(associate.getCpf())){
            log.warn("Associate already exists with CPF: {}", associate.getCpf());
            throw new AssociateAlreadyExists("Associate already exists with the provided CPF.");
        }
        Associate createdAssociate = associateRepository.save(associate);
        log.info("Associate created successfully with ID: {}", createdAssociate.getId());
        return createdAssociate;
    }

    public Associate findById(Long id){
        Optional<Associate> foundAssociate = associateRepository.findById(id);
        if(foundAssociate.isEmpty()){
            log.warn("Associate not found by ID: {}", id);
            throw new AssociateNotFound("Associate not found.");
        }
        return foundAssociate.get();
    }
}
