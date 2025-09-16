package natchanon.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import natchanon.test.dto.BookRequest;
import natchanon.test.entity.Book;
import natchanon.test.repository.BookRepository;
import natchanon.test.service.BookService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookServiceApplicationTests {
    @Autowired
    private MockMvc mockMvc;
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
    private BookRepository bookRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void startContainer() {
        mySQLContainer.start();
    }

    @AfterAll
    static void stopContainer() {
        mySQLContainer.stop();
    }

    @Test
    @Transactional
    void testFindBookByApi() throws Exception {
        Book book = new Book();
        book.setAuthor("Volk");
        book.setTitle("Test1");
        bookRepository.save(book);
        // Perform the mock HTTP GET request
        mockMvc.perform(get("/books?author=Volk"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("""
                        {
                          "bookList": [
                            {
                              "id": 1,
                              "title": "Test1",
                              "author": "Volk"
                            }
                          ]
                        }
                        """));
    }

    @Test
    @Transactional
    void testSaveBookByApi() throws Exception {
        BookRequest book = new BookRequest();
        book.setAuthor("Volk");
        book.setTitle("Test1");

        String bookRequestJson = objectMapper.writeValueAsString(book);
        // Perform the mock HTTP GET request
        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(bookRequestJson))
                .andExpect(status().isOk());

        assertThat(bookRepository.findByAuthor("Volk")).isNotNull();
    }

}
