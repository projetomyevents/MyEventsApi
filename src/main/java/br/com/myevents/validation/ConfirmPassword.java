package br.com.myevents.validation;

import br.com.myevents.model.dto.NewUserDTO;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * O {@link NewUserDTO} com esta anotação deverá conter suas duas senhas
 * ({@link NewUserDTO#getPassword()}, {@link NewUserDTO#getConfirmedPassword()}) iguais.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Constraint(validatedBy = ConfirmPasswordValidator.class)
@Documented
public @interface ConfirmPassword {

    String message() default "As senhas são diferentes.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
