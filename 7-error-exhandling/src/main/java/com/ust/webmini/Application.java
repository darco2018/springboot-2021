package com.ust.webmini;

import com.ust.webmini.help.SomeService;
import com.ust.webmini.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.validation.annotation.Validated;

@Validated
@SpringBootApplication
public class Application {

	private static Logger logger = LoggerFactory.getLogger(Application.class);

	//private static SomeService someService = new SomeService();
	/*
	@Autowired
	static SomeService someService; // java.lang.NullPointerException
*/
	// if Idea cant see spring dependencies -> deleting the auto-generated .iml file under classpath.
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

		SomeService someService = context.getBean(SomeService.class);

		// throws javax.validation.ConstraintViolationException on app's startup:
		//User user = new User("aa",null, "ADMIN", "whatever");

		User user = new User("aa@dupa.com","qwe", "ADMIN", "whatever");
		someService.doStuff(user);
		logger.info("-------------> Created user in main()");

		Item item = new Item("Life is wonderful.");
		someService.doStuff(item); // ConstraintViolationException
		logger.info("-------------> Created ITEM in main()");
	}

}
