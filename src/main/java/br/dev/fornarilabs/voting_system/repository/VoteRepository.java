package br.dev.fornarilabs.voting_system.repository;

import br.dev.fornarilabs.voting_system.domain.Vote;
import br.dev.fornarilabs.voting_system.domain.VoteId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, VoteId> {
}
