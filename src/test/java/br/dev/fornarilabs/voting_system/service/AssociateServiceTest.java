package br.dev.fornarilabs.voting_system.service;


import br.dev.fornarilabs.voting_system.domain.Associate;
import br.dev.fornarilabs.voting_system.mock.EntityMockCreator;
import br.dev.fornarilabs.voting_system.repository.AssociateRepository;
import br.dev.fornarilabs.voting_system.service.exceptions.AssociateAlreadyExists;
import br.dev.fornarilabs.voting_system.service.exceptions.AssociateNotFound;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AssociateServiceTest {

    @Mock
    private AssociateRepository associateRepository;

    @InjectMocks
    private AssociateService associateService;

    @Test
    @DisplayName("Must save an associate successfully.")
    void mustSaveAssociateSuccessfully(){
        Associate associate = EntityMockCreator.createAssociateMock();
        when(associateRepository.existsByCpf(associate.getCpf())).thenReturn(false);
        when(associateRepository.save(any(Associate.class))).thenReturn(associate);
        Associate createdAssociate = associateService.save(associate);
        assertNotNull(createdAssociate);
    }

    @Test
    @DisplayName("Must return an associate.")
    void mustReturnAnAssociate(){
        Associate associate = EntityMockCreator.createAssociateMock();
        when(associateRepository.findById(1L)).thenReturn(Optional.of(associate));
        Associate foundAssociate = associateService.findById(1L);
        assertNotNull(foundAssociate);
        assertEquals("12345678901", foundAssociate.getCpf());
    }

    @Test
    @DisplayName("Must throw AssociateAlreadyExists exception.")
    void mustThrowAssociateAlreadyExists(){
        Associate associate = EntityMockCreator.createAssociateMock();
        when(associateRepository.existsByCpf(associate.getCpf())).thenReturn(true);
        assertThrows(AssociateAlreadyExists.class, () -> {
            associateService.save(associate);
        });
    }

    @Test
    @DisplayName("Must throw AssociateNotFound exception.")
    void mustThrowAssociateNotFound(){
        when(associateRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(AssociateNotFound.class, () -> {
            associateService.findById(1L);
        });
    }

}
