package natchanon.test;

import natchanon.test.dto.BookRequest;
import natchanon.test.dto.BookResponse;
import natchanon.test.entity.Book;
import natchanon.test.repository.BookRepository;
import natchanon.test.service.BookService;
import org.instancio.Instancio;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.LocalDate;
import java.time.chrono.ThaiBuddhistDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.atIndex;
import static org.instancio.Select.field;

@Testcontainers
@SpringBootTest
public class ServiceTest {
    @Container
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.4.6"))
            .withDatabaseName("dev")
            .withEnv("MYSQL_ROOT_HOST", "%");

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

    @Autowired
    private BookService bookService;
    @Autowired
    private BookRepository bookRepository; // Your Spring Data JPA repository

    @BeforeAll
    static void startContainer() {
        mySQLContainer.start();
    }

    @AfterAll
    static void stopContainer() {
        mySQLContainer.stop();
    }

    @AfterEach
    void tearDown(){
        bookRepository.deleteAll();
    }

    @Test
    @DisplayName("Should be able to fetch a book from 10,000 books")
    void Given_10000_bookList_and_saved_When_findBookByAuthorName() {
        // Given
        int size = 10000;
        List<Book> book = Instancio.ofList(Book.class).size(size)
                .ignore(field(Book::getId)).create();
        Book expected = book.get(rng(size));
        List<Book> saved = bookRepository.saveAllAndFlush(book);
        // When
        long startTime = System.nanoTime();
        BookResponse byAuthor = bookService.findBookByAuthorName(expected.getAuthor());
        long endTime = System.nanoTime();
        long durationInMilis = (endTime - startTime)/ size;
        System.out.println(durationInMilis);
        // Then
        assertThat(saved).isNotNull();
        assertThat(saved.size()).isEqualTo(size);
        assertThat(byAuthor.bookList).satisfies(bookList -> {
                    Book actualBook = bookList.get(0);
                    assertThat(actualBook.getAuthor()).isEqualTo(expected.getAuthor());
                    assertThat(actualBook.getTitle()).isEqualTo(expected.getTitle());
                    assertThat(actualBook.getPublishedDate()).isEqualTo(expected.getPublishedDate());
                }
        );
    }

    @Test
    @DisplayName("Should be able to save 10,000 books from request")
    void Given_10000_bookRequest_and_saved_When_findBookByAuthorName() {
        // Given
        int size = 10000;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<BookRequest> bookRequestList = Instancio.ofList(BookRequest.class).size(size)
                .set(field(BookRequest::getPublishedDate), ThaiBuddhistDate.from(LocalDate.now()).format(dtf))
                .create();
        // When
        long startTime = System.nanoTime();
        for(BookRequest bookRequest: bookRequestList) {
            bookService.save(bookRequest);
        }
        long endTime = System.nanoTime();
        long durationInMilis = (endTime - startTime)/ size;
        System.out.println(durationInMilis);
        // Then
        List<Book> allData = bookRepository.findAll();
        assertThat(allData).isNotNull();
        assertThat(allData.size()).isEqualTo(size);
        assertThat(allData).satisfies( (book) ->{
            assertThat(book.getPublishedDate().toLocalDate().getYear()).isEqualTo(2568);
        }, atIndex(0) );
    }

    public int rng(int maxLength){
        Random random = new Random();
        int min = 0;
        int max = maxLength-1;
        return random.nextInt(max - min + 1) + min;
    }


}
