package br.dev.fornarilabs.voting_system.repository;

import br.dev.fornarilabs.voting_system.domain.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {
}
