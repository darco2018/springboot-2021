package cloud.javacoder.demo1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void shouldReturnAuthor() {
        Book book = new Book(12L, "title1", "John");

        String author = book.getAuthor();
        Assertions.assertEquals("John", author);

    }
}