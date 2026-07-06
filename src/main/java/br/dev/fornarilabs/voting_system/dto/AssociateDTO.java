package br.dev.fornarilabs.voting_system.dto;

import br.dev.fornarilabs.voting_system.domain.Associate;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.OffsetDateTime;

public record AssociateDTO(
        Long id,
        String name,
        String cpf,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
        OffsetDateTime creationTime
) {
    public AssociateDTO(Associate associate){
        this(associate.getId(), associate.getName(), associate.getCpf(), associate.getCreationTime());
    }
}
