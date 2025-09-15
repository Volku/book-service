package natchanon.test.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.chrono.ThaiBuddhistDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

@Log4j2
@Configuration
public class ThaiBuddhistDateDeserializer extends JsonDeserializer<ThaiBuddhistDate> {

    // Define the formatter to match your specific JSON format
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public ThaiBuddhistDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String dateAsString = p.getText();
        try {
            TemporalAccessor temporalAccessor = FORMATTER.parse(dateAsString);
            return ThaiBuddhistDate.from(temporalAccessor);
        } catch (Exception e) {
            throw new IOException("Failed to deserialize date: " + dateAsString, e);
        }
    }
}