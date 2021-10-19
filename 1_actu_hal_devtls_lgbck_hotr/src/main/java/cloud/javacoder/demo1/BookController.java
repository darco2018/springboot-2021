package cloud.javacoder.demo1;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class BookController {

    private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(BookController.class);

    @Value("${spring.message}")
    private String message;

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        System.out.println("<<<<<<<<<< Book controller called >>>>>>>>>>>>>>>>");
        System.out.println("ooooooooooooooooooooooooooo BBKKKKKK Book controller called >>>>>>>>>>>>>>>>");
        logger.trace("ooooooooooooooooooooooooooo BBKKKKKK Book controller  trace log message");
        logger.debug("ooooooooooooooooooooooooooo BBKKKKKK Book controller  Debug log message");
        logger.info("ooooooooooooooooooooooooooo BBKKKKKK Book controller  Info log message");
        logger.warn("ooooooooooooooooooooooooooo BBKKKKKK Book controller  Warn log message");
        logger.error("ooooooooooooooooooooooooooo BBKKKKKK Book controller  Error log message");
        return Arrays.asList(
                new Book(1l, "Mastering Jas Spring 777", "John Browny")
        );
    }

    @RequestMapping("/hello")
    public String hello(){
        return this.message;
    }
}

