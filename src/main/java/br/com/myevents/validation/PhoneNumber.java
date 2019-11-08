package br.com.myevents.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * O atributo com esta anotação deverá ser um número de celular ou telefone brasileiro válido.
 */
@Pattern(regexp = "[1-9][0-9]9?[2-9][0-9]{7}")
@ReportAsSingleViolation
@Retention(RetentionPolicy.RUNTIME)
@Target({
        ElementType.METHOD,
        ElementType.FIELD,
        ElementType.ANNOTATION_TYPE,
        ElementType.CONSTRUCTOR,
        ElementType.PARAMETER,
        ElementType.TYPE_USE
})
@Constraint(validatedBy = { })
@Documented
public @interface PhoneNumber {

    String message() default "O número de telefone é inválido.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
