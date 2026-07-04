package br.dev.fornarilabs.voting_system.service;

import br.dev.fornarilabs.voting_system.domain.Agenda;
import br.dev.fornarilabs.voting_system.domain.VoteChoice;
import br.dev.fornarilabs.voting_system.repository.AgendaRepository;
import br.dev.fornarilabs.voting_system.service.exceptions.AgendaNotFound;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@Transactional
public class AgendaService {

    @Autowired
    private AgendaRepository agendaRepository;

    @Transactional
    public Agenda save(Agenda agenda){
        Agenda createdAgenda = agendaRepository.save(agenda);
        log.info("Agenda created successfully with ID: {}", createdAgenda.getId());
        return createdAgenda;
    }

    public Agenda findById(Long id){
        Optional<Agenda> foundAgenda = agendaRepository.findById(id);
        if(foundAgenda.isEmpty()){
            log.warn("Agenda not found by ID: {}", id);
            throw new AgendaNotFound("Agenda not found.");
        }
        return foundAgenda.get();
    }

    public Page<Agenda> listAll(int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("creationTime").descending());
        return agendaRepository.findAll(pageable);
    }

    @Transactional
    public void incrementVotesCount(Agenda agenda, VoteChoice choice){
        int result;
        if(choice == VoteChoice.YES){
            result = agendaRepository.incrementYesVotes(agenda.getId());
        }else{
            result = agendaRepository.incrementNoVotes(agenda.getId());
        }
        if(result == 0){
            log.warn("Trying to change an unexistent agenda with ID: {}", agenda.getId());
            throw new AgendaNotFound("Agenda not found.");
        }
    }
}
