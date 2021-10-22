package com.ust;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/* This app :
* 1) loads data from resources data.sql (but uses h2-console)
* 2) uses @RunWith(SpringRunner.class) with @SpringBootTest - for JUnit4
* */

/*you can check data.sql books are loaded to H2 as the tests pass
* or uncoment starter-web in pom to make Tomcat prevent app from shutting down*/


@SpringBootApplication
public class App4SdataJpaData {

	public static void main(String[] args) {

		SpringApplication.run(App4SdataJpaData.class, args);

	// PROBLEM: can't use Book Service here to print books from H2 because BookRepository is not injected into Book Service.
		// and if I try to use here BookRepository I can't inject into main as @Autowired can't be used on static fields
		// and BookRepository has to be static as it is used in static context
		// SOLUTION: look how BookService is used in Tests here?!

		Book book1 = new Book();
		book1.setName("Jungle");
		System.out.println(book1.hashCode()); // 1902216702 >> 961
		System.out.println("toString is not defined so we get fully-qualified-name@unsigned " +
				"hexadecimal representation of the hash code of the object: \n" +  book1);
		// Integer.toHexString(hashCode())

		Book book2 = new Book();
		book2.setName("Different Jungle");
		System.out.println(book2.hashCode()); // 1653309853 >> 961
		System.out.println(book2);
	}

}
