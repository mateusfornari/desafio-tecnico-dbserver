package br.dev.fornarilabs.voting_system.repository;

import br.dev.fornarilabs.voting_system.domain.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {

    /**
     * Incrementa o contador de votos, de forma atômica, diretamente no banco de dados.
     * Isso evita inconsistência em casos de concorrência
     * @param agendaId O ID da pauta.
     * @return Retorna a quantidade de linhas afetadas. Se retornar 0 significa que não existe um registro com o ID informado.
     */
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Agenda a SET a.votesCountYes = a.votesCountYes + 1 WHERE a.id = :id")
    int incrementYesVotes(@Param("id") Long agendaId);

    /**
     * Incrementa o contador de votos, de forma atômica, diretamente no banco de dados.
     * Isso evita inconsistência em casos de concorrência
     * @param agendaId O ID da pauta.
     * @return Retorna a quantidade de linhas afetadas. Se retornar 0 significa que não existe um registro com o ID informado.
     */
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Agenda a SET a.votesCountNo = a.votesCountNo + 1 WHERE a.id = :id")
    int incrementNoVotes(@Param("id") Long agendaId);
}
