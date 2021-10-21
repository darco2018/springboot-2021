package cloud.javacoder.springdatajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/* this app shows
 - Spring Data REST (spring-boot-starter-data-rest) to expose repositories as REST
 - lombok
 - dataFiller class as Service rather than CommandLinerRunner

 spring-boot-starter-data-rest makes automatically endpoints for Teacher & Course.
 In browser we can make GET requests but with HAL Explorer or Postman we can make POST, DELETE, PUT
  probably
 */

@SpringBootApplication
@EnableTransactionManagement // needed for transactions outside repository, eg in DataFiller
public class SDataRestLmbk {

	public static void main(String[] args) {
		SpringApplication.run(SDataRestLmbk.class);
	}

}
