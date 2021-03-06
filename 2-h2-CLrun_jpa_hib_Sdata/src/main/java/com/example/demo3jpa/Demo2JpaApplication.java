package com.example.demo3jpa;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class Demo2JpaApplication {

	// h2 at http://localhost:8080/h2-console
	// JDBC URL:  jdbc:h2:mem:testdb

	public static void main(String[] args) {

		
		/*   CommandLineRunner and ApplicationRunner, to run specific pieces of code when an application is fully started.
		   These interfaces get called just before run() once SpringApplication completes.
		 
		  CommandLineRunner - provides access to application arguments as string array.
		 
		  ApplicationRunner wraps the raw application arguments and exposes the ApplicationArguments interface,
		  which has many convinent methods to work with arguments,*/
		 
		  //You just need to register them as Beans in the application context. Then, Spring will automatically pick them up.
		

		SpringApplication.run(Demo2JpaApplication.class, args); // return a subclass of Application Context
	}

	// instead of using @Component on a class we can use @Bean for Spring to take care of the created instance
	// this method returns the implementation of the interface CommandLineRunner's run()

	// @Bean is also an annotation that Spring uses to gather beans at runtime, but it's not used at the class level.
	// Rather, we annotate methods with @Bean
	// test if bean created: assertNotNull(applicationContext.getBean(BeanExample.class));
	@Bean
	CommandLineRunner commandLineRunner() {
		return args ->
				System.out.println("------->> @Component CLRunners run earlier. This CLRunner from main() runs last with args:"
						+ Arrays.toString(args));
	}



	/* Spring Data: (SpringData JPA - specific implementation)
	* You create interfaces & Spring Data will create their implementation
	* */

}
