package br.com.myevents.validation;

import br.com.myevents.annotation.ConfirmPassword;
import br.com.myevents.model.dto.NewUserDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

/**
 * Valida as duas senhas ({@link NewUserDTO#getPassword()}, {@link NewUserDTO#getConfirmedPassword()})
 * de um {@link NewUserDTO}.
 */
public class ConfirmPasswordValidator implements ConstraintValidator<ConfirmPassword, NewUserDTO> {

    @Override
    public void initialize(ConfirmPassword constraintAnnotation) { }

    @Override
    public boolean isValid(NewUserDTO value, ConstraintValidatorContext context) {
        return Optional.ofNullable(value.getPassword()).orElse("").equals(value.getConfirmedPassword());
    }

}
