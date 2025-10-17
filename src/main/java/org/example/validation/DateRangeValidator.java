package org.example.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;
import java.time.LocalDate;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateRangeValidator.DateRangeValidatorImpl.class)
@Documented
public @interface DateRangeValidator {
    String message() default "End date must be after or equal to start date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String startDateField();
    String endDateField();

    class DateRangeValidatorImpl implements ConstraintValidator<DateRangeValidator, Object> {
        private String startDateField;
        private String endDateField;

        @Override
        public void initialize(DateRangeValidator constraintAnnotation) {
            this.startDateField = constraintAnnotation.startDateField();
            this.endDateField = constraintAnnotation.endDateField();
        }

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext context) {
            try {
                var startDateGetter = value.getClass().getMethod("get" + capitalize(startDateField));
                var endDateGetter = value.getClass().getMethod("get" + capitalize(endDateField));

                LocalDate startDate = (LocalDate) startDateGetter.invoke(value);
                LocalDate endDate = (LocalDate) endDateGetter.invoke(value);

                if (startDate == null || endDate == null) {
                    return true; // Let @NotNull handle null values
                }

                return !endDate.isBefore(startDate);
            } catch (Exception e) {
                return false;
            }
        }

        private String capitalize(String str) {
            if (str == null || str.isEmpty()) return str;
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        }
    }
}

