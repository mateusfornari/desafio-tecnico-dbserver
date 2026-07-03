package br.dev.fornarilabs.voting_system.domain;

import lombok.Getter;

@Getter
public enum VoteChoice {
    YES("Y"),
    NO("N");

    private final String value;

    VoteChoice(String value) {
        this.value = value;
    }

    public static VoteChoice fromValue(String value){
        for(VoteChoice choice: VoteChoice.values()){
            if(choice.getValue().equals(value)){
                return choice;
            }
        }
        throw new IllegalArgumentException("Invalid VoteChoice value.");
    }
}
