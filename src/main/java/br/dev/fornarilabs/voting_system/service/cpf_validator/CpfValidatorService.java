package br.dev.fornarilabs.voting_system.service.cpf_validator;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CpfValidatorService {

    public boolean isValid(String cpf){
        Random random = new Random();
        int randomNumber = random.nextInt();
        // Decide se o CPF é válido ou não aleatoriamente.
        return randomNumber % 5 != 0;
    }

}
