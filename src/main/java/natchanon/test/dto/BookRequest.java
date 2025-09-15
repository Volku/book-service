package natchanon.test.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.util.ObjectUtils;

import java.sql.Date;
import java.util.Calendar;

@Getter
@Setter
@NoArgsConstructor
public class BookRequest {
    @NotBlank(message = "author can not be blank/empty")
    private String author;
    @NotBlank(message = "title can not be blank/empty")
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "Date can not be in future")
    private Date publishedDate;

    public BookRequest(String author, String title, Date publishedDate) {
        this.author = author;
        this.title = title;
        this.publishedDate = publishedDate;
    }

    @JsonIgnore
    @AssertTrue(message = "Invalid Publish Date")
    public boolean isAllowedDate(){
        boolean isEmpty = ObjectUtils.isEmpty(publishedDate);
        return isEmpty || publishedDate.getYear() +1900 >= 1000;
    }

}
