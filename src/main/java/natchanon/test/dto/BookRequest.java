package natchanon.test.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.*;
import lombok.*;
import natchanon.test.config.PastOrPresentBuddhistDate;
import org.springframework.util.ObjectUtils;

import java.time.chrono.ThaiBuddhistDate;

import static java.time.temporal.ChronoField.YEAR;

@Getter
@Setter
@NoArgsConstructor
public class BookRequest {
    @NotBlank(message = "author can not be blank/empty")
    private String author;
    @NotBlank(message = "title can not be blank/empty")
    private String title;
    @PastOrPresentBuddhistDate
    private String publishedDate;

    public BookRequest(String author, String title, String publishedDate) {
        this.author = author;
        this.title = title;
        this.publishedDate = publishedDate;
    }
}
