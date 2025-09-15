package natchanon.test.dto;

import natchanon.test.entity.Book;

import java.util.List;

public class BookResponse {
    public List<Book> bookList;

    public BookResponse(List<Book> bookList) {
        this.bookList = bookList;
    }

    public BookResponse() {
    }
}
