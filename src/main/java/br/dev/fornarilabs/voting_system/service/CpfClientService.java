package br.dev.fornarilabs.voting_system.service;

import br.dev.fornarilabs.voting_system.dto.cpf_validator.ValidateCpfDTO;
import br.dev.fornarilabs.voting_system.dto.cpf_validator.ValidateCpfResponseDTO;
import br.dev.fornarilabs.voting_system.service.exceptions.CpfApiError;
import br.dev.fornarilabs.voting_system.service.exceptions.InvalidCpf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
public class CpfClientService {

    private final RestClient client;

    public CpfClientService(@Qualifier("cpfValidatorClient") RestClient client) {
        this.client = client;
    }

    public boolean isCpfValid(String cpf){
        try {
            ValidateCpfDTO request = new ValidateCpfDTO(cpf);
            ValidateCpfResponseDTO response = client.post()
                    .uri("/cpf-validator/v1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .onStatus(status -> status.value() == 404, (req, res) -> {
                        log.info("CPF not found response.");
                        throw new InvalidCpf("CPF is invalid.");
                    })
                    .body(ValidateCpfResponseDTO.class);
            if (response != null) {
                log.info("CPF response status: {}", response.status());
                return "ABLE_TO_VOTE".equals(response.status());
            }
            return false;
        }catch (InvalidCpf e){
            log.info("CPF is invalid.");
            return false;
        }catch (HttpClientErrorException e){
            log.error("Error HTTP when accessing CPF API: {}", e.getResponseBodyAsString());
            throw new CpfApiError("Error HTTP when accessing CPF API.");
        }catch (Exception e){
            log.error("Unknown error when accessing CPF API: {}", e.getMessage());
            throw new CpfApiError("Unknown error when accessing CPF API.");
        }
    }
}
