package br.com.myevents.validation;

import br.com.myevents.validation.FieldsMatch.List;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * O {@link Object} com esta anotação deverá conter os dois atributos especificados iguais.
 */
@Target({TYPE})
@Retention(RUNTIME)
@Repeatable(List.class)
@Documented
@Constraint(validatedBy = FieldsMatchValidator.class)
public @interface FieldsMatch {

    String message() default "Fields don't match!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String firstField();

    String secondField();

    @Target({TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        FieldsMatch[] value();
    }

}
