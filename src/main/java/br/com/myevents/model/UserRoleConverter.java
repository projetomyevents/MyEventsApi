package br.com.myevents.model;

import br.com.myevents.security.enums.Role;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Optional;

/**
 * Converte um cargo de um usuário para a sua representação na base de dados e vice-versa.
 */
@Converter(autoApply = true)
public class UserRoleConverter implements AttributeConverter<Role, String> {

    @Override
    public String convertToDatabaseColumn(Role role) {
        return Optional.ofNullable(role).map(Role::getName).orElse(null);
    }

    @Override
    public Role convertToEntityAttribute(String role) {
        return Optional.ofNullable(role).map(Role::of).orElse(null);
    }

}
