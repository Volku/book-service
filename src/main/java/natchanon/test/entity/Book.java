package natchanon.test.entity;

import jakarta.persistence.*;
import lombok.*;
import natchanon.test.dto.BookRequest;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.chrono.ThaiBuddhistDate;

@Entity
@Table(name = "book")
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;
    @Column(name = "published_date")
    private ThaiBuddhistDate publishedDate;

    public Book(BookRequest bookRequest) {
        this.title = bookRequest.getTitle();
        this.author = bookRequest.getAuthor();
        this.publishedDate = bookRequest.getPublishedDate();
    }
}
