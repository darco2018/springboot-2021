package com.ust;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/* This app :
* 1) loads data from resources data.sql (but uses h2-console)
* 2) uses @RunWith(SpringRunner.class) with @SpringBootTest - why exactly?
* */


@SpringBootApplication
public class App4SdataJpaData {

	public static void main(String[] args) {

		SpringApplication.run(App4SdataJpaData.class, args);

	// PROBLEM: can't use Book Service here to print books from H2 because BookRepository is not injected into Book Service.
		// and if I try to use here BookRepository I can't inject into main as @Autowired can't be used on static fields
		// and BookReposotory has to be static as it os used in static context
		// SOLUTION: look how BookService is used in Tests here?!

		Book book1 = new Book();
		book1.setName("Jungle");
		System.out.println(book1.hashCode()); // 1902216702 >> 961
		System.out.println(book1);

		Book book2 = new Book();
		book2.setName("Different Jungle");
		System.out.println(book2.hashCode()); // 1653309853 >> 961
		System.out.println(book2);
	}

}
