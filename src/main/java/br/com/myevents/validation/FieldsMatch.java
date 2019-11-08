package br.com.myevents.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * O {@link Object} com esta anotação deverá conter os dois atributos especificados iguais.
 */
@Constraint(validatedBy = FieldsMatchValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldsMatch {

    String message() default "Fields don't match!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String firstField();
    String secondField();

    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        FieldsMatch[] value();
    }

}
