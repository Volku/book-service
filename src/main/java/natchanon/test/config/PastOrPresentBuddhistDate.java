package natchanon.test.config;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = BuddhistDateValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PastOrPresentBuddhistDate {

    String message() default "Date must be a valid Buddhist date (yyyy-MM-dd) and must not be in the future.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}