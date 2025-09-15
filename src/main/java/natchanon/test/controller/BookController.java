package natchanon.test.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import natchanon.test.dto.BookRequest;
import natchanon.test.dto.BookResponse;
import natchanon.test.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/books")
    public BookResponse getBook(@RequestParam("author") String authorName) {
        return bookService.findBookByAuthorName(authorName);
    }

    @PostMapping("/books")
    public void saveBook(@Valid @RequestBody BookRequest bookRequest) {
        bookService.save(bookRequest);
    }
}
