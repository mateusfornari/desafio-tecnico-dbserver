package br.dev.fornarilabs.voting_system.controller;

import br.dev.fornarilabs.voting_system.domain.Associate;
import br.dev.fornarilabs.voting_system.mock.EntityMockCreator;
import br.dev.fornarilabs.voting_system.service.AssociateService;
import br.dev.fornarilabs.voting_system.service.exceptions.AssociateAlreadyExists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AssociateController.class)
public class AssociateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AssociateService associateService;

    private static final String BASE_PATH = "/api/v1/associates";

    @Test
    @DisplayName("Must return 201 and created associate data.")
    void mustReturn201AndCreatedAssociateData() throws Exception {
        Associate associate = EntityMockCreator.createAssociateMock();
        String request = createRequest(associate);

        when(associateService.save(any(Associate.class))).thenReturn(associate);

        mockMvc.perform(post(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(associate.getId()))
                .andExpect(jsonPath("$.name").value(associate.getName()))
                .andExpect(jsonPath("$.cpf").value(associate.getCpf()))
        ;
    }

    @Test
    @DisplayName("Must return 422 when associate already exists.")
    void mustReturn422WhenAssociateAlreadyExists() throws Exception {
        Associate associate = EntityMockCreator.createAssociateMock();
        String request = createRequest(associate);
        when(associateService.save(any(Associate.class))).thenThrow(AssociateAlreadyExists.class);

        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isUnprocessableContent())
        ;
    }

    @Test
    @DisplayName("Must return 400 when name is shorter than 3.")
    void mustReturn400WhenNameIsShorterThan3() throws Exception {
        Associate associate = EntityMockCreator.createAssociateMock();
        associate.setName("a");
        String request = createRequest(associate);
        when(associateService.save(any(Associate.class))).thenThrow(AssociateAlreadyExists.class);

        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @DisplayName("Must return 400 when name is longer than 255.")
    void mustReturn400WhenNameIsLongerThan255() throws Exception {
        Associate associate = EntityMockCreator.createAssociateMock();
        associate.setName("a".repeat(256));
        String request = createRequest(associate);
        when(associateService.save(any(Associate.class))).thenThrow(AssociateAlreadyExists.class);

        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @DisplayName("Must return 400 when CPF is shorter than 11.")
    void mustReturn400WhenCpfIsShorterThan11() throws Exception {
        Associate associate = EntityMockCreator.createAssociateMock();
        associate.setCpf("1");
        String request = createRequest(associate);
        when(associateService.save(any(Associate.class))).thenThrow(AssociateAlreadyExists.class);

        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @DisplayName("Must return 400 when CPF is longer than 11.")
    void mustReturn400WhenCpfIsLongerThan11() throws Exception {
        Associate associate = EntityMockCreator.createAssociateMock();
        associate.setCpf("1".repeat(12));
        String request = createRequest(associate);
        when(associateService.save(any(Associate.class))).thenThrow(AssociateAlreadyExists.class);

        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @DisplayName("Must return 400 when CPF is not numeric.")
    void mustReturn400WhenCpfIsNotNumeric() throws Exception {
        Associate associate = EntityMockCreator.createAssociateMock();
        associate.setCpf("abcdefghijk");
        String request = createRequest(associate);
        when(associateService.save(any(Associate.class))).thenThrow(AssociateAlreadyExists.class);

        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
        ;
    }

    private String createRequest(Associate associate){
        Map<String, String> request = new HashMap<>();
        request.put("name", associate.getName());
        request.put("cpf", associate.getCpf());
        return objectMapper.writeValueAsString(request);
    }
}
