package natchanon.test.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.*;
import lombok.*;
import natchanon.test.config.ThaiBuddhistDateDeserializer;
import org.springframework.util.ObjectUtils;

import java.sql.Date;
import java.time.chrono.ThaiBuddhistDate;
import java.util.Calendar;

import static java.time.temporal.ChronoField.YEAR;

@Getter
@Setter
@NoArgsConstructor
public class BookRequest {
    @NotBlank(message = "author can not be blank/empty")
    private String author;
    @NotBlank(message = "title can not be blank/empty")
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonDeserialize(using = ThaiBuddhistDateDeserializer.class)
    @PastOrPresent
    private ThaiBuddhistDate publishedDate;

    public BookRequest(String author, String title, ThaiBuddhistDate publishedDate) {
        this.author = author;
        this.title = title;
        this.publishedDate = publishedDate;
    }

    @JsonIgnore
    @AssertTrue(message = "Invalid Publish Date")
    public boolean isAllowedDate(){
        boolean isEmpty = ObjectUtils.isEmpty(publishedDate);
        return isEmpty || publishedDate.getLong(YEAR) >= 1000;
    }

}
