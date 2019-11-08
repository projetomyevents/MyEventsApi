package br.com.myevents.model;

import br.com.myevents.model.enums.PresenceStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Realiza a conversão de um status de presença de um convidado para a sua representação na base de dados e vice-versa.
 */
@Converter(autoApply = true)
public class GuestPresenceStatusConverter implements AttributeConverter<PresenceStatus, String> {

    @Override
    public String convertToDatabaseColumn(PresenceStatus presenceStatus) {
        return presenceStatus == null ? null : presenceStatus.getName();
    }

    @Override
    public PresenceStatus convertToEntityAttribute(String presenceStatus) {
        return presenceStatus == null ? null : PresenceStatus.of(presenceStatus);
    }

}
