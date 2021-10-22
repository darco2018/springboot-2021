package com.ust;

import org.junit.jupiter.api.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;  // for @RunWith(SpringRunner.class)
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
/*SpringRunner is an alias for the SpringJUnit4ClassRunner. To use this class, simply annotate
a JUnit 4 based test class with: */
@RunWith(SpringRunner.class) // to use with org.junit. versions older than Jupiter
@SpringBootTest
class ApplicationTests {

	@Autowired
	private BookService bookService;

	@Test
	void contextLoads() {
	}

	@Test
	public void whenAppStarts_thenHibernateCreatesInitialRecords(){
		List<Book> books = bookService.getBooks();
		org.junit.Assert.assertEquals(3, books.size());
	}
}
