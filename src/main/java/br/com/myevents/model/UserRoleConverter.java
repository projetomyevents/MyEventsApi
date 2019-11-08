package br.com.myevents.model;

import br.com.myevents.security.enums.Role;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Realiza a conversão de um cargo de um usuário para a sua representação na base de dados e vice-versa.
 */
@Converter(autoApply = true)
public class UserRoleConverter implements AttributeConverter<Role, String> {

    @Override
    public String convertToDatabaseColumn(Role role) {
        return role == null ? null : role.getName();
    }

    @Override
    public Role convertToEntityAttribute(String role) {
        return role == null ? null : Role.of(role);
    }

}
