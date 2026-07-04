package br.dev.fornarilabs.voting_system.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class VoteId implements Serializable {
    @Column(name = "agenda_id")
    private Long agendaId;

    @Column(name = "associate_id")
    private Long associateId;

    public VoteId(){}

    public VoteId(Long agendaId, Long associateId){
        this.agendaId = agendaId;
        this.associateId = associateId;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(obj == null || obj.getClass() != getClass()){
            return false;
        }
        VoteId voteId = (VoteId) obj;
        return Objects.equals(voteId.agendaId, agendaId) && Objects.equals(voteId.associateId, associateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(agendaId, associateId);
    }
}
