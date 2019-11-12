package br.com.myevents.model;

import br.com.myevents.model.enums.PresenceStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Optional;

/**
 * Converte um status de presença de um convidado para a sua representação na base de dados e vice-versa.
 */
@Converter(autoApply = true)
public class GuestPresenceStatusConverter implements AttributeConverter<PresenceStatus, String> {

    @Override
    public String convertToDatabaseColumn(PresenceStatus presenceStatus) {
        return Optional.ofNullable(presenceStatus).map(PresenceStatus::getName).orElseThrow(null);
    }

    @Override
    public PresenceStatus convertToEntityAttribute(String presenceStatus) {
        return Optional.ofNullable(presenceStatus).map(PresenceStatus::of).orElse(null);
    }

}
