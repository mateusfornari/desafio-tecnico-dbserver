package br.dev.fornarilabs.voting_system.repository.converter;

import br.dev.fornarilabs.voting_system.domain.VoteChoice;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class VoteChoiceConverter implements AttributeConverter<VoteChoice, String> {

    @Override
    public String convertToDatabaseColumn(VoteChoice attribute) {
        if(attribute == null){
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public VoteChoice convertToEntityAttribute(String dbData) {
        if(dbData == null){
            return null;
        }
        return VoteChoice.fromValue(dbData);
    }
}
