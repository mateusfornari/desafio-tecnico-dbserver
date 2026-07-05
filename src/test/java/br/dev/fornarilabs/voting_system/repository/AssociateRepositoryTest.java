package br.dev.fornarilabs.voting_system.repository;

import br.dev.fornarilabs.voting_system.domain.Associate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AssociateRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private AssociateRepository associateRepository;

    @Test
    @DisplayName("Must save the associate successfully.")
    void mustSaveAssociateSuccessfully(){
        Associate associate = new Associate();
        associate.setName("Unit Test Associate");
        associate.setCpf("12345678901");
        Associate createdAssociate = associateRepository.save(associate);
        assertNotNull(createdAssociate);
        assertEquals(1L, createdAssociate.getId());
        assertNotNull(createdAssociate.getCreationTime());

        Optional<Associate> found = associateRepository.findById(1L);
        assertTrue(found.isPresent());
        assertEquals(associate.getCpf(), found.get().getCpf());
    }

    @Test
    @DisplayName("Associate must exists by CPF.")
    void associateMustExistsByCpf(){
        String cpf = "12345678903";
        Associate associate = new Associate();
        associate.setCpf(cpf);
        associate.setName("Unit Test CPF exists");
        associateRepository.save(associate);

        boolean result = associateRepository.existsByCpf(cpf);
        assertTrue(result);
    }
}
