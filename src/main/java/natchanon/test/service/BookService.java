package natchanon.test.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import natchanon.test.dto.BookResponse;
import natchanon.test.entity.Book;
import natchanon.test.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class BookService {
    private final BookRepository bookRepository;

    public BookResponse findBookByAuthorName(String authorName) {
        BookResponse result = new BookResponse(bookRepository.findByAuthor(authorName));
        log.info(result.bookList.get(0));
        return result;
    }
}
