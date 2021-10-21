package cloud.javacoder.springdatajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/* this app shows
 - Spring Data
 - lombok
 - dataFiller class as Service rather than CommandLinerRunner
 */

// Just add spring-boot-starter-data-rest to expose repositories as REST


@SpringBootApplication
@EnableTransactionManagement // needed for transactions outside repository, eg in DataFiller
public class SpringdatajpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringdatajpaApplication.class);
	}

}
