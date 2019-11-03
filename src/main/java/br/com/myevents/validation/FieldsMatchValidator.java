package br.com.myevents.validation;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * Valida a igualdade de dois atributos de um {@link Object}.
 */
public class FieldsMatchValidator implements ConstraintValidator<FieldsMatch, Object> {

    private String firstField;
    private String secondField;

    @Override
    public void initialize(FieldsMatch constraintAnnotation) {
        this.firstField = constraintAnnotation.firstField();
        this.secondField = constraintAnnotation.secondField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return Objects.equals(
                new BeanWrapperImpl(value).getPropertyValue(this.firstField),
                new BeanWrapperImpl(value).getPropertyValue(this.secondField));
    }

}
