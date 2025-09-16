package natchanon.test.config;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.time.chrono.ThaiBuddhistChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;

@Log4j2
public class BuddhistDateValidator implements ConstraintValidator<PastOrPresentBuddhistDate, String> {


    private static final DateTimeFormatter BUDDHIST_DATE_FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("uuuu-MM-dd")
            .toFormatter()
            .withChronology(ThaiBuddhistChronology.INSTANCE)
            .withResolverStyle(ResolverStyle.STRICT);

    @Override
    public boolean isValid(String dateStr, ConstraintValidatorContext context) {
        log.info("Validator is working");
        log.info(dateStr);
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return true;
        }
        try {
            LocalDate parsedDate = LocalDate.parse(dateStr, BUDDHIST_DATE_FORMATTER);
            LocalDate today = LocalDate.now();
            return !parsedDate.isAfter(today) && parsedDate.getYear() + 543 >= 1000;
        } catch (Exception e) {
            log.warn("Fail Date Validation formatter: ", e);
            return false;
        }
    }
}